package com.mdd.back.stubs;

import com.mdd.back.entities.Article;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ArticleStub {

    public static Article getDefaultArticle() {
        Article article = new Article();
        //article.setId(1L);
        article.setAuthor(UserStub.getDefaultUser());
        article.setTheme(ThemeStub.getDefaultTheme());
        article.setTitle("Default Article");
        article.setContent("This is the default article content.");
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;
    }

    public static Article getArticleWithLongContent() {
        Article article = new Article();
        article.setId(2L);
        article.setAuthor(UserStub.getAdminUser());
        article.setTheme(ThemeStub.getDefaultTheme());
        article.setTitle("Article with Long Content");
        article.setContent("This is a very long content".repeat(50));
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;
    }

    public static Article getArticleWithNoTitle() {
        Article article = new Article();
        article.setId(3L);
        article.setAuthor(UserStub.getDefaultUser());
        article.setTheme(ThemeStub.getDefaultTheme());
        article.setTitle(null);
        article.setContent("Content without a title.");
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;
    }

    public static Article getArticleWithShortTitle() {
        Article article = new Article();
        article.setId(4L);
        article.setAuthor(UserStub.getUserWithNoUsername());
        article.setTheme(ThemeStub.getDefaultTheme());
        article.setTitle("A");
        article.setContent("This article has a very short title.");
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;
    }

    public static Article getArticleWithoutAuthor() {
        Article article = new Article();
        article.setId(5L);
        article.setAuthor(null);
        article.setTheme(ThemeStub.getDefaultTheme());
        article.setTitle("Article Without Author");
        article.setContent("This article has no author.");
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;
    }

    public static List<Article> getArticleList() {
        return Arrays.asList(
                getDefaultArticle(),
                getArticleWithLongContent(),
                getArticleWithNoTitle(),
                getArticleWithShortTitle(),
                getArticleWithoutAuthor()
        );
    }
}
