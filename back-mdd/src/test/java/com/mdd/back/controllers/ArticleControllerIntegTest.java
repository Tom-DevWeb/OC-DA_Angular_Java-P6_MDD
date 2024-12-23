package com.mdd.back.controllers;

import com.mdd.back.TestcontainersConfiguration;
import com.mdd.back.entities.Article;
import com.mdd.back.entities.Comment;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.User;
import com.mdd.back.repositories.*;
import com.mdd.back.stubs.ArticleStub;
import com.mdd.back.stubs.ThemeStub;
import com.mdd.back.stubs.UserStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public class ArticleControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ThemeSubscriptionRepository themeSubscriptionRepository;


    @BeforeEach
    public void setUp() {
        // Nettoyer la base de données avant chaque test
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        userRepository.deleteAll();
        themeRepository.deleteAll();
        themeSubscriptionRepository.deleteAll();

        // Ajouter des données de test dans l'ordre correct
        User user = UserStub.getDefaultUser();
        userRepository.save(user);

        Theme theme = ThemeStub.getDefaultTheme();
        themeRepository.save(theme);

        // Créer un article avec les entités User et Theme déjà persistées
        Article article = ArticleStub.getDefaultArticle();
        article.setAuthor(user);
        article.setTheme(theme);
        articleRepository.save(article);
    }

    @Test
    @WithMockUser
    public void testGetAllArticles() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Default Article"));
    }


    @Test
    @WithMockUser
    public void testGetSingleArticle() throws Exception {
        Long articleId = articleRepository.findAll().getFirst().getId();

        mockMvc.perform(get("/articles/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Default Article"))
                .andExpect(jsonPath("$.content").value("This is the default article content."));
    }

    @Test
    @WithMockUser
    public void testCreateArticle() throws Exception {
        Long userId = userRepository.findAll().getFirst().getId();
        Long themeId = themeRepository.findAll().getFirst().getId();

        String newArticleJson = String.format("""
        {
            "title": "New Article",
            "content": "This is the content of the new article.",
            "author": {
                "id": %d
            },
            "theme": {
                "id": %d
            }
        }
    """, userId, themeId);

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newArticleJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetArticleComments() throws Exception {
        Article article = articleRepository.findAll().stream().findFirst().orElseThrow();
        Long articleId = article.getId();

        mockMvc.perform(get("/articles/{id}/comments", articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser
    public void testCreateArticleComment() throws Exception {
        Article article = articleRepository.findAll().stream().findFirst().orElseThrow();
        Long articleId = article.getId();

        User user = userRepository.findAll().stream().findFirst().orElseThrow();
        Long userId = user.getId();

        String newCommentJson = String.format("""
        {
            "content": "This is a new comment.",
            "author": {
                "id": %d
            }
        }""", userId);

        mockMvc.perform(post("/articles/{id}/comments", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCommentJson))
                .andExpect(status().isOk());

        // Vérifier que le commentaire a été créé
        List<Comment> comments = commentRepository.findAll();
        assertEquals(1, comments.size());
        assertEquals("This is a new comment.", comments.getFirst().getContent());
    }



}
