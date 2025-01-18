package com.mdd.back.security.service;

import com.mdd.back.entities.User;
import com.mdd.back.security.dto.BearerResponseDto;
import com.mdd.back.security.dto.RefreshTokenDto;
import com.mdd.back.security.entities.Jwt;
import com.mdd.back.security.entities.RefreshToken;
import com.mdd.back.security.repositories.JwtRepository;
import com.mdd.back.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static io.jsonwebtoken.Claims.EXPIRATION;
import static io.jsonwebtoken.Claims.SUBJECT;

@Slf4j
@Service
public class JWTService {

    private static final int expirationTime = 60 * 60 * 1000; //60 minutes

    private final UserService userService;
    private final JwtRepository jwtRepository;

    @Value("${security.jwt.secret-key}")
    private String ENCRYPTION_KEY;

    public JWTService(UserService userService, JwtRepository jwtRepository) {
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }

    public BearerResponseDto generate(String email) {
        User user = userService.loadUserByUsername(email);
        this.disableExistingTokens(user.getId());
        BearerResponseDto jwtMap = this.generateJwt(user);

        Instant now = Instant.now();
        Jwt jwt = Jwt.builder()
                .bearer(jwtMap.bearer())
                .user(user)
                .expired(false)
                .disable(false)
                .refreshToken(RefreshToken.builder()
                        .value(UUID.randomUUID().toString())
                        .creation(now)
                        .expiration(now.plusMillis(expirationTime))
                        .expired(false)
                        .build())
                .build();

        jwtRepository.save(jwt);
        return new BearerResponseDto(jwt.getBearer(), jwt.getRefreshToken().getValue());
    }

    public BearerResponseDto createRefreshToken(RefreshTokenDto refreshTokenDto) {
        Jwt jwt = this.jwtRepository.findByRefreshTokenValue(refreshTokenDto.refreshToken()).orElseThrow(() -> new RuntimeException("Token invalide"));
        if (jwt.getRefreshToken().isExpired() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
            throw new RuntimeException("Token expiré");
        }

        User user = userService.loadUserByUsername(jwt.getUser().getEmail());
        disableExistingTokens(user.getId());
        return this.generate(user.getEmail());
    }

    private void disableExistingTokens(Long idUser) {
        final List<Jwt> jwtList = this.jwtRepository.findAllTokenForThisIdUser(idUser);
        jwtList.forEach(
                jwt -> {
                    jwt.setDisable(true);
                    jwt.setExpired(true);
                }
        );
        this.jwtRepository.saveAll(jwtList);
    }

    private BearerResponseDto generateJwt(User user) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long expirationTimeMillis = currentTimeMillis + expirationTime;

        Map<String, Object> claims = Map.of(
                "username", user.getRealUsername(),
                SUBJECT, user.getEmail(),
                EXPIRATION, new Date(expirationTimeMillis)
        );

        String bearer = Jwts.builder()
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(expirationTimeMillis))
                .subject(user.getEmail())
                .claims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return new BearerResponseDto(bearer, null);
    }

    private Key getKey() {
        byte[] decode = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String lireUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaimsFromToken(token);
        return function.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Jwt getTokenByBearer(String bearer) {
        return this.jwtRepository.findByBearer(bearer).orElseThrow(() -> new RuntimeException("Token not found"));
    }


    public void disconnect() {
        User utilisateur = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt tokenPresentInBDD = this.jwtRepository.findByIdUserAndDisableAndExpired(utilisateur.getId(), false, false)
                .orElseThrow(() -> new RuntimeException("Token non trouvé"));

        tokenPresentInBDD.setExpired(true);
        tokenPresentInBDD.setDisable(true);

        jwtRepository.save(tokenPresentInBDD);
    }

    @Scheduled(cron = "0 */2 * * * *")
    @Transactional
    public void pruneExpiredTokens() {
        log.info("Pruning expired tokens à {}", Instant.now());
        this.jwtRepository.deleteAllByExpiredAndDisable(true, true);
    }


}
