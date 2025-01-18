package com.mdd.back.controllers;

import com.mdd.back.dto.responses.ThemeResponseDto;
import com.mdd.back.services.ThemeService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Contrôleur pour gérer les thèmes et les abonnements associés.
 * Fournit des points d'accès pour récupérer, s'abonner et se désabonner des thèmes.
 */
@RestController
@RequestMapping(value = "/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * Récupère tous les thèmes disponibles.
     *
     * @return une liste de {@link ThemeResponseDto} représentant tous les thèmes disponibles
     */
    @GetMapping("")
    public List<ThemeResponseDto> getThemes() {
        return themeService.getThemes();
    }

    /**
     * Récupère les thèmes auxquels l'utilisateur connecté est abonné.
     *
     * @param principal l'utilisateur actuellement authentifié
     * @return une liste de {@link ThemeResponseDto} représentant les thèmes abonnés par l'utilisateur
     */
    @GetMapping("/subscriptions")
    public List<ThemeResponseDto> getSubscribedThemes(Principal principal) {
        String email = principal.getName();
        return themeService.getSubscribedThemes(email);
    }

    /**
     * Permet à un utilisateur de s'abonner à un thème spécifique.
     *
     * @param id l'identifiant du thème
     * @param userId l'identifiant de l'utilisateur
     */
    @PostMapping("/{id}/users/{userId}")
    public void subscribeTheme(@PathVariable Long id, @PathVariable Long userId) {
        themeService.subscribeTheme(id, userId);
    }

    /**
     * Permet à un utilisateur de se désabonner d'un thème spécifique.
     *
     * @param id l'identifiant du thème
     * @param userId l'identifiant de l'utilisateur
     */
    @DeleteMapping("/{id}/users/{userId}")
    public void unsubscribeTheme(@PathVariable Long id, @PathVariable Long userId) {
        themeService.unsubscribeTheme(id, userId);
    }

}
