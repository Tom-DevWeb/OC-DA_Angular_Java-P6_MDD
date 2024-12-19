package com.mdd.back.services;

import com.mdd.back.dto.responses.ThemeResponseDto;
import com.mdd.back.dto.responses.ThemesUserResponseDto;
import com.mdd.back.entities.Theme;
import com.mdd.back.entities.ThemeSubscription;
import com.mdd.back.entities.ThemeSubscriptionId;
import com.mdd.back.mapper.ThemeMapper;
import com.mdd.back.repositories.ThemeRepository;
import com.mdd.back.repositories.ThemeSubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ThemeService {

    private final ThemeMapper themeMapper;
    private final ThemeRepository themeRepository;
    private final ThemeSubscriptionRepository themeSubscriptionRepository;

    public ThemeService(
            ThemeMapper themeMapper,
            ThemeRepository themeRepository,
            ThemeSubscriptionRepository themeSubscriptionRepository
    ) {
        this.themeMapper = themeMapper;
        this.themeRepository = themeRepository;
        this.themeSubscriptionRepository = themeSubscriptionRepository;
    }

    public List<ThemeResponseDto> getThemes() {
        List<Theme> theme = themeRepository.findAll();

        return themeMapper.themesToThemeResponseDto(theme);
    }

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

    public void unsubscribeTheme(Long themeId, Long userId) {

        ThemeSubscriptionId subscriptionId = new ThemeSubscriptionId();
        subscriptionId.setTheme(themeId);
        subscriptionId.setUser(userId);

        ThemeSubscription themeSubscription = themeSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Theme subscription not found"));

        themeSubscriptionRepository.delete(themeSubscription);
    }

    public List<ThemesUserResponseDto> getThemesUser(Long userId) {
        List<ThemeSubscription> subscriptions = themeSubscriptionRepository.findById_User(userId);

        return themeMapper.themesToThemesUserResponseDto(subscriptions);
    }
}
