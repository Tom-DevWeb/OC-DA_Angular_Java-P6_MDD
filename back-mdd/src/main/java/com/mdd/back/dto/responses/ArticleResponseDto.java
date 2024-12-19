package com.mdd.back.dto.responses;

import com.mdd.back.entities.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleResponseDto {

    private String title;
    private String content;
    private Theme theme;
}
