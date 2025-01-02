package com.mdd.back.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "L'email ou username ne peut pas être vide")
    private String identifier;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;
}
