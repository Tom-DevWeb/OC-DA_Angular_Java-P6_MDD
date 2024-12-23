package com.mdd.back.security.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDto(@NotBlank String refreshToken) {}