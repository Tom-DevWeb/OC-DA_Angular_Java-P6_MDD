package com.mdd.back.controllers;

import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.services.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public List<ArticleResponseDto> getAllArticles() {
        return articleService.getAllArticles();
    }
}
