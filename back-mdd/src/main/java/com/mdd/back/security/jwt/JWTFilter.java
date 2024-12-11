package com.mdd.back.security.jwt;

import com.mdd.back.security.entities.Jwt;
import com.mdd.back.security.service.JWTService;
import com.mdd.back.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JWTFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JWTService jwtService;

    public JWTFilter(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token;
        String username = null;
        Jwt tokenInBdd = null;
        boolean isTokenExpired = true;

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            tokenInBdd = this.jwtService.getTokenByBearer(token);
            isTokenExpired = jwtService.isTokenExpired(token);
            username = jwtService.lireUsername(token);
        }

        if (!isTokenExpired &&
                username != null &&
                isGetTokenMatchesThisUser(tokenInBdd, username) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }

    private boolean isGetTokenMatchesThisUser(Jwt token, String username) {
        return userService.loadUserByUsername(username).getId().equals(token.getUser().getId());
    }
}

