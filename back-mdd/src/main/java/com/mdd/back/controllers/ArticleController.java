package com.mdd.back.controllers;

import com.mdd.back.dto.requests.ArticleRequestDto;
import com.mdd.back.dto.requests.CommentRequestDto;
import com.mdd.back.dto.responses.CommentResponseDto;
import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ArticleResponseDto getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("")
    public void createArticle(@Valid @RequestBody ArticleRequestDto articleRequestDto) {
        articleService.createArticle(articleRequestDto);
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getArticleComments(@PathVariable Long id) {
        return articleService.getArticleComments(id);
    }

    @PostMapping("/{id}/comments")
    public void createArticleComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {
        articleService.createArticleComment(id, commentRequestDto);

    }

}
