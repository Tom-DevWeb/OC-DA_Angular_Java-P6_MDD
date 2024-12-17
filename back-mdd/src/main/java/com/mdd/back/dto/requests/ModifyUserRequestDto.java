package com.mdd.back.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyUserRequestDto {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    private String username;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email ne peut pas être vide")
    private String email;

}

