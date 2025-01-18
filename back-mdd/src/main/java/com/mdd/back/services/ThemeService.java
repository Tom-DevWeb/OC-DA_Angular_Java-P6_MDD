package com.mdd.back.services;

import com.mdd.back.dto.responses.ThemeResponseDto;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.ThemeSubscription;
import com.mdd.back.entities.ThemeSubscriptionId;
import com.mdd.back.entities.User;
import com.mdd.back.mapper.ThemeMapper;
import com.mdd.back.repositories.ThemeRepository;
import com.mdd.back.repositories.ThemeSubscriptionRepository;
import com.mdd.back.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des thèmes et des abonnements associés.
 */
@Service
public class ThemeService {

    private final UserRepository userRepository;
    private final ThemeMapper themeMapper;
    private final ThemeRepository themeRepository;
    private final ThemeSubscriptionRepository themeSubscriptionRepository;

    public ThemeService(
            UserRepository userRepository,
            ThemeMapper themeMapper,
            ThemeRepository themeRepository,
            ThemeSubscriptionRepository themeSubscriptionRepository
    ) {
        this.userRepository = userRepository;
        this.themeMapper = themeMapper;
        this.themeRepository = themeRepository;
        this.themeSubscriptionRepository = themeSubscriptionRepository;
    }

    /**
     * Récupère tous les thèmes disponibles.
     *
     * @return une liste de DTOs {@link ThemeResponseDto} représentant les thèmes
     */
    public List<ThemeResponseDto> getThemes() {
        List<Theme> theme = themeRepository.findAll();

        return themeMapper.themesToThemeResponseDto(theme);
    }

    /**
     * Récupère les thèmes auxquels un utilisateur est abonné.
     *
     * @param userEmail l'email de l'utilisateur
     * @return une liste de DTOs {@link ThemeResponseDto} représentant les thèmes abonnés
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public List<ThemeResponseDto> getSubscribedThemes(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ThemeSubscription> subscriptions = themeSubscriptionRepository.findById_User(user.getId());

        List<Theme> themes = subscriptions.stream()
                .map(subscription -> themeRepository.findById(subscription.getId().getTheme())
                        .orElseThrow(() -> new RuntimeException("Theme not found")))
                .toList();

        return themeMapper.themesToThemeResponseDto(themes);
    }

    /**
     * Abonne un utilisateur à un thème.
     *
     * @param themeId l'identifiant du thème
     * @param userId l'identifiant de l'utilisateur
     * @throws RuntimeException si le thème n'est pas trouvé ou si l'utilisateur est déjà abonné au thème
     */
    public void subscribeTheme(Long themeId, Long userId) {
        themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found"));

        ThemeSubscriptionId subscriptionId = new ThemeSubscriptionId();
        subscriptionId.setTheme(themeId);
        subscriptionId.setUser(userId);

        boolean isAlreadySubscribed = themeSubscriptionRepository.existsById(subscriptionId);
        if (isAlreadySubscribed) {
            throw new RuntimeException("Theme subscription already subscribed");
        }

        ThemeSubscription themeSubscription = new ThemeSubscription();
        themeSubscription.setId(subscriptionId);

        themeSubscriptionRepository.save(themeSubscription);
    }

    /**
     * Désabonne un utilisateur d'un thème.
     *
     * @param themeId l'identifiant du thème
     * @param userId l'identifiant de l'utilisateur
     * @throws RuntimeException si l'abonnement n'est pas trouvé
     */
    public void unsubscribeTheme(Long themeId, Long userId) {

        ThemeSubscriptionId subscriptionId = new ThemeSubscriptionId();
        subscriptionId.setTheme(themeId);
        subscriptionId.setUser(userId);

        ThemeSubscription themeSubscription = themeSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Theme subscription not found"));

        themeSubscriptionRepository.delete(themeSubscription);
    }

}
