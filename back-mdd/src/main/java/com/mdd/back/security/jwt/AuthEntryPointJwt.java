package com.mdd.back.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Classe responsable de la gestion des erreurs d'authentification.
 *
 * Cette classe implémente l'interface {@link AuthenticationEntryPoint}
 * et définit la méthode {@link #commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
 * pour personnaliser la réponse HTTP renvoyée lorsqu'une requête nécessite une authentification, mais que l'utilisateur
 * n'est pas authentifié.
 *
 * Lorsqu'un utilisateur tente d'accéder à une ressource protégée sans être authentifié, cette classe envoie une réponse
 * HTTP 401 (Unauthorized) avec un message d'erreur au format JSON, contenant la description de l'exception.
 */
@Service
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + exception.getMessage() + "\"}");
    }

}
