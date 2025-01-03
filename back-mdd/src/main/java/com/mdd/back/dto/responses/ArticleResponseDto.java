package com.mdd.back.dto.responses;

import com.mdd.back.entities.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;
    private Theme theme;
    private Date createdAt;
    private String author;
}
