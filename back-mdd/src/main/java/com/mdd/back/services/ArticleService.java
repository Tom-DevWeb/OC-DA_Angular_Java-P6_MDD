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

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
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

    public void createArticle(ArticleRequestDto articleRequestDto) {
        Article article = articleMapper.articleRequestDtoToArticle(articleRequestDto);

        articleRepository.save(article);
    }

    public List<CommentResponseDto> getArticleComments(Long id) {
        List<Comment> comments = commentRepository.findAllCommentsByArticleId(id);

        return commentMapper.commentsToCommentsResponseDto(comments);
    }

    public void createArticleComment(Long articleId, CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.commentRequestDtoToComment(commentRequestDto);

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));

        comment.setArticle(article);

        commentRepository.save(comment);
    }
}
