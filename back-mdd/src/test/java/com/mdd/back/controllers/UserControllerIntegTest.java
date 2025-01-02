package com.mdd.back.controllers;

import com.mdd.back.TestcontainersConfiguration;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.ThemeSubscription;
import com.mdd.back.entities.User;
import com.mdd.back.repositories.ArticleRepository;
import com.mdd.back.repositories.ThemeRepository;
import com.mdd.back.repositories.ThemeSubscriptionRepository;
import com.mdd.back.repositories.UserRepository;
import com.mdd.back.security.repositories.JwtRepository;
import com.mdd.back.stubs.ThemeStub;
import com.mdd.back.stubs.UserStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public class UserControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
        themeRepository.deleteAll();
        themeSubscriptionRepository.deleteAll();
        jwtRepository.deleteAll();

        userRepository.deleteAll();

        String encodedPassword = passwordEncoder.encode(UserStub.getDefaultUser().getPassword());

        // Ajouter des données de test dans l'ordre correct
        User user = UserStub.getDefaultUser();
        user.setPassword(encodedPassword);
        userRepository.save(user);


    }

    @Test
    @WithMockUser
    public void testFindById() throws Exception {
        User user = userRepository.findAll().getFirst();

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @WithMockUser
    public void testGetThemesUser() throws Exception {
        // Récupérer l'utilisateur de test
        User user = userRepository.findAll().getFirst();

        List<Theme> themes = ThemeStub.getDefaultThemes();
        themeRepository.saveAll(themes);

        List<ThemeSubscription> subscriptions = ThemeStub.getThemeSubscriptionsForUser(user, themes);
        themeSubscriptionRepository.saveAll(subscriptions);

        // Simuler l'appel à l'API pour récupérer les thèmes de l'utilisateur
        mockMvc.perform(get("/users/{id}/themes", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].themeId").value(6))
                .andExpect(jsonPath("$[1].themeId").value(7))
                .andExpect(jsonPath("$[2].themeId").value(8));
    }

}
