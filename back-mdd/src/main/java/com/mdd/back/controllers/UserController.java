package com.mdd.back.controllers;

import com.mdd.back.dto.UserDto;
import com.mdd.back.dto.requests.LoginRequestDto;
import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.dto.responses.ThemesUserResponseDto;
import com.mdd.back.security.dto.RefreshTokenDto;
import com.mdd.back.security.service.JWTService;
import com.mdd.back.services.ThemeService;
import com.mdd.back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ThemeService themeService;
    private final JWTService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, ThemeService themeService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.themeService = themeService;
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

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/refresh-token")
    public @ResponseBody Map<String, String> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return jwtService.createRefreshToken(refreshTokenDto);
    }

    @GetMapping("/me")
    public UserDto findMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userService.getUserByEmail(email);
    }

    @PutMapping("")
    public void modifyUser(@Valid @RequestBody ModifyUserRequestDto modifyUserRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        userService.modifyUser(email, modifyUserRequestDto);
    }

    @GetMapping("/{id}/themes")
    public List<ThemesUserResponseDto> getThemesUser(@PathVariable Long id) {
        return themeService.getThemesUser(id);
    }

    @PostMapping("/disconnect")
    public void disconnect() {
        jwtService.disconnect();
    }

}
