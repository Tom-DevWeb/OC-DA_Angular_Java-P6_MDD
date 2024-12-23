package com.mdd.back.dto.requests;

import com.mdd.back.entities.Theme;
import com.mdd.back.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArticleRequestDto {

    @NotBlank(message = "Le titre de l'article ne peut pas être vide")
    private String title;

    @NotBlank(message = "Le contenu de l'article ne peut pas être vide")
    private String content;

    @NotNull(message = "Le thème de l'article ne peut pas être nul")
    private Theme theme;

    @NotNull(message = "Le nom de l'auteur ne peut pas être vide")
    private User author;
}
