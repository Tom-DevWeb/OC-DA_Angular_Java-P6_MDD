package com.mdd.back.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ThemeSubRequestDto {

    @NotNull(message = "Le thème ne peut pas être nul")
    private Long themeId;

    @NotNull(message = "L'utilisateur ne peut pas être nul")
    private Long userId;
}

