package com.mdd.back.stubs;

import com.mdd.back.entities.Theme;
import com.mdd.back.entities.ThemeSubscription;
import com.mdd.back.entities.ThemeSubscriptionId;
import com.mdd.back.entities.User;

import java.util.List;

public class ThemeStub {

    public static Theme getDefaultTheme() {
        Theme theme = new Theme();
        theme.setTitle("Default Theme");
        theme.setDescription("This is the default theme description.");
        return theme;
    }

    // Ajout d'une méthode pour renvoyer une liste de thèmes
    public static List<Theme> getDefaultThemes() {
        return List.of(
                createTheme("Theme 1", "Description of Theme 1"),
                createTheme("Theme 2", "Description of Theme 2"),
                createTheme("Theme 3", "Description of Theme 3")
        );
    }

    // Méthode utilitaire pour créer des thèmes avec un titre et une description
    private static Theme createTheme(String title, String description) {
        Theme theme = new Theme();
        theme.setTitle(title);
        theme.setDescription(description);
        return theme;
    }

    public static List<ThemeSubscription> getThemeSubscriptionsForUser(User user, List<Theme> themes) {
        return themes.stream()
                .map(theme -> {
                    ThemeSubscription subscription = new ThemeSubscription();
                    subscription.setId(new ThemeSubscriptionId(theme.getId(), user.getId()));
                    return subscription;
                })
                .toList();
    }
}
