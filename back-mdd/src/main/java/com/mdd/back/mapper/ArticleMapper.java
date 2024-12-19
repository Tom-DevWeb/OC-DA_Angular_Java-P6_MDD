package com.mdd.back.mapper;

import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.entities.Article;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleResponseDto articleToArticleResponseDto(Article article);

    List<ArticleResponseDto> articleToArticleResponseDtoList(List<Article> articles);
}
