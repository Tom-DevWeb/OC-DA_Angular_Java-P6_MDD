package com.mdd.back.mapper;

import com.mdd.back.dto.requests.ArticleRequestDto;
import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(source = "author.realUsername", target = "author")
    ArticleResponseDto articleToArticleResponseDto(Article article);

    List<ArticleResponseDto> articleToArticleResponseDtoList(List<Article> articles);

    Article articleRequestDtoToArticle(ArticleRequestDto articleRequestDto);
}
