package com.mdd.back.controllers;

import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour gérer les opérations relatives aux utilisateurs.
 * Permet notamment la modification des informations d'un utilisateur.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Modifie les informations de l'utilisateur actuellement authentifié.
     *
     * @param modifyUserRequestDto le DTO contenant les nouvelles informations de l'utilisateur
     */
    @PutMapping("")
    public void modifyUser(@Valid @RequestBody ModifyUserRequestDto modifyUserRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        userService.modifyUser(email, modifyUserRequestDto);
    }

}
