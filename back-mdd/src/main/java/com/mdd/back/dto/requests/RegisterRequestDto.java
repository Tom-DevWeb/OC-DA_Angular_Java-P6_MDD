package com.mdd.back.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    private String username;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email ne peut pas être vide")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$",
            message = "Votre mot de passe doit contenir minimum 8 caractères, avec au moins : un chiffre, une lettre minuscule, une lettre majuscule, et un caractère spécial.")
    private String password;
}

