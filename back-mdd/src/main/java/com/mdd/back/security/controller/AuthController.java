package com.mdd.back.security.controller;

import com.mdd.back.dto.requests.LoginRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.dto.responses.UserResponseDto;
import com.mdd.back.security.dto.BearerResponseDto;
import com.mdd.back.security.dto.RefreshTokenDto;
import com.mdd.back.security.service.JWTService;
import com.mdd.back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur qui gère l'authentification des utilisateurs.
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Permet à un utilisateur de se connecter avec ses identifiants (email/username et mot de passe).
     *
     * @param loginRequestDto les informations de connexion de l'utilisateur
     * @return un {@link BearerResponseDto} contenant le token JWT si l'authentification réussie, ou sinon null
     */
    @PostMapping("/login")
    public BearerResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        UserResponseDto user = userService.getUserByEmailOrUsername(loginRequestDto.getIdentifier());

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), loginRequestDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generate(user.getEmail());
        }
        return null;
    }

    /**
     * Permet à un utilisateur de s'enregistrer en fournissant ses informations de base (email, mot de passe, username).
     *
     * @param registerRequestDto les informations d'inscription de l'utilisateur
     * @return un {@link BearerResponseDto} contenant le token JWT après l'enregistrement et l'authentification
     */
    @PostMapping("/register")
    public BearerResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        String savedPassword = registerRequestDto.getPassword();

        userService.register(registerRequestDto);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequestDto.getEmail(), savedPassword)
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generate(registerRequestDto.getEmail());
        }
        return null;
    }

    /**
     * Permet de renouveler le token d'accès en utilisant un refresh token.
     *
     * @param refreshTokenDto les informations du refresh token pour obtenir un nouveau token d'accès
     * @return un {@link BearerResponseDto} contenant le nouveau token d'accès
     */
    @PostMapping("/refresh-token")
    public @ResponseBody BearerResponseDto refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return jwtService.createRefreshToken(refreshTokenDto);
    }

    /**
     * Permet de récupérer les informations de l'utilisateur actuellement authentifié.
     * L'email de l'utilisateur est extrait du contexte de sécurité.
     *
     * @return un {@link UserResponseDto}
     */
    @GetMapping("/me")
    public UserResponseDto findMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userService.getUserByEmail(email);
    }

    /**
     * Permet de déconnecter l'utilisateur.
     */
    @PostMapping("/disconnect")
    public void disconnect() {
        jwtService.disconnect();
    }
}
