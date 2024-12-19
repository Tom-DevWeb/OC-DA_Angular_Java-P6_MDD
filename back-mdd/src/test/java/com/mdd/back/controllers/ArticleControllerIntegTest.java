package com.mdd.back.controllers;

import com.mdd.back.TestcontainersConfiguration;
import com.mdd.back.entities.Article;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.User;
import com.mdd.back.repositories.ArticleRepository;
import com.mdd.back.repositories.CommentRepository;
import com.mdd.back.repositories.ThemeRepository;
import com.mdd.back.repositories.UserRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class ArticleControllerIntegTest {
/*
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


    @BeforeEach
    public void setUp() {
        // Nettoyer la base de données avant chaque test
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        userRepository.deleteAll();
        themeRepository.deleteAll();

        // Ajouter des données de test dans l'ordre correct
        User user = UserStub.getDefaultUser();
        userRepository.save(user);

        Theme theme = ThemeStub.getDefaultTheme();
        themeRepository.save(theme);

        // Créer un article avec les entités User et Theme déjà persistées
        Article article = ArticleStub.getDefaultArticle();
        article.setAuthor(user);  // Associer l'utilisateur déjà persisté
        article.setTheme(theme);   // Associer le thème déjà persisté
        articleRepository.save(article);
    }

    @Autowired
    private DataSource dataSource;

    @Test
    void testDatabaseConnection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "The database connection should not be null.");
        }
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

     */
}
