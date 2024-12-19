package com.mdd.back.services;

import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.entities.Article;
import com.mdd.back.mapper.ArticleMapper;
import com.mdd.back.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public List<ArticleResponseDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        return articleMapper.articleToArticleResponseDtoList(articles);
    }

    public ArticleResponseDto getArticleById(Long id) {

        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Article not found")
        );


        return articleMapper.articleToArticleResponseDto(article);
    }
}
