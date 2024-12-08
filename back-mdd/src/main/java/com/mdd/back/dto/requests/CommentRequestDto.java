package com.mdd.back.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequestDto {

    @NotBlank(message = "Le contenu du commentaire ne peut pas être vide")
    private String content;
}

