package com.mdd.back.controllers;

import com.mdd.back.dto.requests.LoginRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.security.dto.RefreshTokenDto;
import com.mdd.back.security.service.JWTService;
import com.mdd.back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generate(loginRequestDto.getEmail());
        }
        return null;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        userService.register(registerRequestDto);
    }

    //TODO: A faire
    @GetMapping("/{id}")
    public void findById(@PathVariable Long id) {
        userService.findById(id);
    }

    @PostMapping("/refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return jwtService.createRefreshToken(refreshTokenDto);
    }

    //TODO: A faire
    @PostMapping("/disconnect")
    public void disconnect() {
        jwtService.disconnect();
    }

}
