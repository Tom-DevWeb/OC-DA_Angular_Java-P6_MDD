package com.mdd.back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "theme_subscription")
public class ThemeSubscription {

    @EmbeddedId
    private ThemeSubscriptionId id;

}
