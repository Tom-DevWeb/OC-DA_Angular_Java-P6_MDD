package com.mdd.back.dto.requests;

import com.mdd.back.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDto {

    @NotBlank(message = "Le contenu du commentaire ne peut pas être vide")
    private String content;

    @NotNull(message = "Le nom de l'auteur ne peut pas être vide")
    private User author;
}

