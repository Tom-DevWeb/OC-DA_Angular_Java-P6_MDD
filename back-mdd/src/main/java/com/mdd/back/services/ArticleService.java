package com.mdd.back.services;

import com.mdd.back.dto.requests.ArticleRequestDto;
import com.mdd.back.dto.requests.CommentRequestDto;
import com.mdd.back.dto.responses.ArticleResponseDto;
import com.mdd.back.dto.responses.CommentResponseDto;
import com.mdd.back.entities.Article;
import com.mdd.back.entities.Comment;
import com.mdd.back.mapper.ArticleMapper;
import com.mdd.back.mapper.CommentMapper;
import com.mdd.back.repositories.ArticleRepository;
import com.mdd.back.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des articles et des commentaires associés.
 */
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public ArticleService(
            ArticleRepository articleRepository,
            ArticleMapper articleMapper,
            CommentRepository commentRepository,
            CommentMapper commentMapper
    ) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Récupère tous les articles disponibles.
     *
     * @return une liste de DTOs {@link ArticleResponseDto} représentant les articles
     */
    public List<ArticleResponseDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        return articleMapper.articleToArticleResponseDtoList(articles);
    }

    /**
     * Récupère un article spécifique par son identifiant.
     *
     * @param id l'identifiant de l'article
     * @return un DTO {@link ArticleResponseDto} représentant l'article
     * @throws IllegalArgumentException si l'article n'existe pas
     */
    public ArticleResponseDto getArticleById(Long id) {

        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Article not found")
        );

        return articleMapper.articleToArticleResponseDto(article);
    }

    /**
     * Crée un nouvel article à partir des données fournies.
     *
     * @param articleRequestDto les données de l'article sous forme de DTO
     */
    public void createArticle(ArticleRequestDto articleRequestDto) {
        Article article = articleMapper.articleRequestDtoToArticle(articleRequestDto);

        articleRepository.save(article);
    }

    /**
     * Récupère tous les commentaires d'un article spécifique.
     *
     * @param id l'identifiant de l'article
     * @return une liste de DTOs {@link CommentResponseDto} représentant les commentaires
     */
    public List<CommentResponseDto> getArticleComments(Long id) {
        List<Comment> comments = commentRepository.findAllCommentsByArticleId(id);

        return commentMapper.commentsToCommentsResponseDto(comments);
    }

    /**
     * Ajoute un commentaire à un article spécifique.
     *
     * @param articleId         l'identifiant de l'article
     * @param commentRequestDto les données du commentaire sous forme de DTO
     * @throws IllegalArgumentException si l'article n'existe pas
     */
    public void createArticleComment(Long articleId, CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.commentRequestDtoToComment(commentRequestDto);

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));

        comment.setArticle(article);

        commentRepository.save(comment);
    }
}
