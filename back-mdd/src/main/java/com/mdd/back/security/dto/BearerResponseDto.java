package com.mdd.back.security.dto;

import jakarta.validation.constraints.NotBlank;

public record BearerResponseDto(@NotBlank String bearer, @NotBlank String refresh) {
}
