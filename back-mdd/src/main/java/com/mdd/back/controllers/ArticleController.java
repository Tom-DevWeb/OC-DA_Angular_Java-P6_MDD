package com.mdd.back.controllers;

import com.mdd.back.dto.requests.ArticleRequestDto;
import com.mdd.back.dto.requests.CommentRequestDto;
import com.mdd.back.dto.responses.CommentResponseDto;
import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour gérer les articles et leurs commentaires associés.
 * Fournit des points d'accès pour créer, récupérer et gérer les articles et les commentaires.
 */
@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Récupère tous les articles.
     *
     * @return une liste de {@link ArticleResponseDto} représentant tous les articles
     */
    @GetMapping("")
    public List<ArticleResponseDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Récupère un article par son identifiant.
     *
     * @param id    l'identifiant de l'article à récupérer
     * @return      le {@link ArticleResponseDto} représentant l'article correspondant
     */
    @GetMapping("/{id}")
    public ArticleResponseDto getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    /**
     * Crée un nouvel article.
     *
     * @param articleRequestDto le DTO contenant les données de l'article à créer
     */
    @PostMapping("")
    public void createArticle(@Valid @RequestBody ArticleRequestDto articleRequestDto) {
        articleService.createArticle(articleRequestDto);
    }

    /**
     * Récupère tous les commentaires d'un article spécifique.
     *
     * @param id    l'identifiant de l'article
     * @return      une liste de {@link CommentResponseDto} représentant les commentaires de l'article
     */
    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getArticleComments(@PathVariable Long id) {
        return articleService.getArticleComments(id);
    }

    /**
     * Ajoute un commentaire à un article spécifique.
     *
     * @param id                l'identifiant de l'article
     * @param commentRequestDto le DTO contenant les données du commentaire à ajouter
     */
    @PostMapping("/{id}/comments")
    public void createArticleComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {
        articleService.createArticleComment(id, commentRequestDto);

    }

}
