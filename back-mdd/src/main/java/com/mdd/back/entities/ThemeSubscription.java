package com.mdd.back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "THEME_SUBSCRIPTION")
public class ThemeSubscription {

    @EmbeddedId
    private ThemeSubscriptionId id;

}
