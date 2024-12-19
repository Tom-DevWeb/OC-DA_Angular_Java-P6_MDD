package com.mdd.back.controllers;

import com.mdd.back.dto.responses.ThemeResponseDto;
import com.mdd.back.services.ThemeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("")
    public List<ThemeResponseDto> getThemes() {
        return themeService.getThemes();
    }

    @PostMapping("/{id}/users/{userId}")
    public void subscribeTheme(@PathVariable Long id, @PathVariable Long userId) {
        themeService.subscribeTheme(id, userId);
    }

    @DeleteMapping("/{id}/users/{userId}")
    public void unsubscribeTheme(@PathVariable Long id, @PathVariable Long userId) {
        themeService.unsubscribeTheme(id, userId);
    }

}
