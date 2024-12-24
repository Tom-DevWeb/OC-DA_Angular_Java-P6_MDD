package com.mdd.back.controllers;

import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.dto.responses.ThemesUserResponseDto;
import com.mdd.back.dto.responses.UserResponseDto;
import com.mdd.back.services.ThemeService;
import com.mdd.back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final ThemeService themeService;

    public UserController(UserService userService, ThemeService themeService) {
        this.userService = userService;
        this.themeService = themeService;
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.getUserById(id);
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



}
