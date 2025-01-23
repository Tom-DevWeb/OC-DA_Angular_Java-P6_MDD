package com.mdd.back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "theme_subscription")
public class ThemeSubscription {

    /**
     * Clé primaire composée, définie dans la classe {@link ThemeSubscriptionId}.
     * L'annotation {@code @EmbeddedId} indique que cette clé regroupe plusieurs colonnes.
     */
    @EmbeddedId
    private ThemeSubscriptionId id;

}
