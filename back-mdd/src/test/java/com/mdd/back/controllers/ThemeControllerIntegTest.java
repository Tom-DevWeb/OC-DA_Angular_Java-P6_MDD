package com.mdd.back.controllers;

import com.mdd.back.TestcontainersConfiguration;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.ThemeSubscription;
import com.mdd.back.entities.ThemeSubscriptionId;
import com.mdd.back.entities.User;
import com.mdd.back.repositories.ArticleRepository;
import com.mdd.back.repositories.ThemeRepository;
import com.mdd.back.repositories.ThemeSubscriptionRepository;
import com.mdd.back.repositories.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public class ThemeControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ThemeSubscriptionRepository themeSubscriptionRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    public void setUp() {
        // Nettoyer la base de données avant chaque test
        articleRepository.deleteAll();
        themeSubscriptionRepository.deleteAll();
        themeRepository.deleteAll();
        userRepository.deleteAll();


        // Ajouter des données de test dans l'ordre correct
        User user = UserStub.getDefaultUser();
        userRepository.save(user);

        Theme theme = ThemeStub.getDefaultTheme();
        themeRepository.save(theme);

    }

    @Test
    @WithMockUser
    public void testGetThemes() throws Exception {
        mockMvc.perform(get("/themes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Default Theme"));
    }

    @Test
    @WithMockUser
    public void testSubscribeTheme() throws Exception {
        User user = userRepository.findAll().getFirst();
        Theme theme = themeRepository.findAll().getFirst();

        mockMvc.perform(post("/themes/{id}/users/{userId}", theme.getId(), user.getId()))
                .andExpect(status().isOk());

        // Vérifier que l'abonnement a été ajouté
        ThemeSubscriptionId expectedId = new ThemeSubscriptionId();
        expectedId.setTheme(theme.getId());
        expectedId.setUser(user.getId());

        boolean exists = themeSubscriptionRepository.existsById(expectedId);
        assertTrue(exists);
    }

    @Test
    @WithMockUser
    public void testUnsubscribeTheme() throws Exception {
        User user = userRepository.findAll().getFirst();
        Theme theme = themeRepository.findAll().getFirst();

        // Ajouter un abonnement avant de tester le désabonnement
        ThemeSubscriptionId subscriptionId = new ThemeSubscriptionId();
        subscriptionId.setTheme(theme.getId());
        subscriptionId.setUser(user.getId());

        ThemeSubscription subscription = new ThemeSubscription();
        subscription.setId(subscriptionId);
        themeSubscriptionRepository.save(subscription);

        mockMvc.perform(delete("/themes/{id}/users/{userId}", theme.getId(), user.getId()))
                .andExpect(status().isOk());

        // Vérifier que l'abonnement a été supprimé
        boolean exists = themeSubscriptionRepository.existsById(subscriptionId);
        assertFalse(exists);
    }
}
