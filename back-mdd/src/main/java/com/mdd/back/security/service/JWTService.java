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

/**
 * Service pour gérer la génération, la validation et le rafraîchissement des tokens JWT (JSON Web Tokens).
 * -
 * Le service prend en charge la génération d'un token JWT pour un utilisateur, ainsi que la gestion d'un token de rafraîchissement
 * pour permettre la mise à jour de l'authentification sans se reconnecter.
 * -
 * Les tokens JWT ont une durée de vie limitée à 60 minutes, tandis que les tokens de rafraîchissement ont une durée de vie de 30 jours.
 * -
 * Le service offre aussi la possibilité de couper la connexion d'un utilisateur en invalidant ses tokens et de supprimer les tokens expirés
 * automatiquement à interval régulier.
 */
@Slf4j
@Service
public class JWTService {

    private static final int expirationTime = 60 * 60 * 1000; //60 minutes
    private static final long refreshTokenExpirationTime = 30L * 24L * 60L * 60L * 1000L; // 30 jours (en millisecondes)

    private final UserService userService;
    private final JwtRepository jwtRepository;

    @Value("${security.jwt.secret-key}")
    private String ENCRYPTION_KEY;

    public JWTService(UserService userService, JwtRepository jwtRepository) {
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }

    /**
     * Génère un nouveau JWT pour un utilisateur donné, en invalidant les anciens tokens.
     *
     * @param email L'email de l'utilisateur pour lequel le JWT est généré.
     * @return Un objet contenant le token JWT généré ainsi que le token de rafraîchissement.
     */
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
                        .expiration(now.plusMillis(refreshTokenExpirationTime))
                        .expired(false)
                        .build())
                .build();

        jwtRepository.save(jwt);
        return new BearerResponseDto(jwt.getBearer(), jwt.getRefreshToken().getValue());
    }

    /**
     * Crée un nouveau token JWT en utilisant un token de rafraîchissement valide.
     *
     * @param refreshTokenDto Un objet contenant le token de rafraîchissement.
     * @return Un objet contenant le nouveau token JWT généré ainsi que le nouveau token de rafraîchissement.
     */
    public BearerResponseDto createRefreshToken(RefreshTokenDto refreshTokenDto) {
        Jwt jwt = this.jwtRepository.findByRefreshTokenValue(refreshTokenDto.refreshToken()).orElseThrow(() -> new RuntimeException("Token invalide"));
        if (jwt.getRefreshToken().isExpired() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
            throw new RuntimeException("Token expiré");
        }

        User user = userService.loadUserByUsername(jwt.getUser().getEmail());
        disableExistingTokens(user.getId());
        return this.generate(user.getEmail());
    }

    /**
     * Désactive tous les anciens tokens associés à un utilisateur donné.
     *
     * @param idUser L'ID de l'utilisateur pour lequel les tokens doivent être désactivés.
     */
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

    /**
     * Génère un token JWT d'accès.
     *
     * @param user L'utilisateur pour lequel le token JWT est généré.
     * @return Un objet {@link BearerResponseDto} contenant le JWT généré.
     */
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

    /**
     * Lit le nom d'utilisateur (email) à partir du token JWT.
     *
     * @param token Le token JWT.
     * @return Le nom d'utilisateur extrait du token.
     */
    public String lireUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    /**
     * Vérifie si un token JWT est expiré.
     *
     * @param token Le token JWT.
     * @return true si le token est expiré, false sinon.
     */
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

    /**
     * Récupère un token JWT à partir de son "bearer".
     *
     * @param bearer Le "bearer" du token JWT.
     * @return Le token {@link Jwt} associé au bearer.
     */
    public Jwt getTokenByBearer(String bearer) {
        return this.jwtRepository.findByBearer(bearer).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    /**
     * Déconnecte l'utilisateur en désactivant son token JWT actif.
     */
    public void disconnect() {
        User utilisateur = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt tokenPresentInBDD = this.jwtRepository.findByIdUserAndDisableAndExpired(utilisateur.getId(), false, false)
                .orElseThrow(() -> new RuntimeException("Token non trouvé"));

        tokenPresentInBDD.setExpired(true);
        tokenPresentInBDD.setDisable(true);

        jwtRepository.save(tokenPresentInBDD);
    }

    /**
     * Planifie la suppression automatique des tokens expirés toutes les 2 minutes.
     * -
     * Cette méthode est annotée avec {@link Scheduled} pour être exécutée périodiquement selon un cron planifié
     * (ici toutes les 2 minutes). Elle utilise également l'annotation {@link Transactional} pour garantir que
     * la suppression des tokens expirés est effectuée dans une transaction. Si une erreur se produit pendant
     * la suppression des tokens, toutes les modifications de la base de données seront annulées (rollback) pour
     * assurer la cohérence des données.
     * -
     * La méthode supprime tous les tokens qui sont marqués comme expirés et désactivés dans la base de données.
     *
     * @see Scheduled
     * @see Transactional
     */
    @Scheduled(cron = "0 */2 * * * *")
    @Transactional
    public void pruneExpiredTokens() {
        log.info("Pruning expired tokens à {}", Instant.now());
        this.jwtRepository.deleteAllByExpiredAndDisable(true, true);
    }


}
