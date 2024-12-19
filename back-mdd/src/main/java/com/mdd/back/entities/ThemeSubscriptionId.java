package com.mdd.back.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ThemeSubscriptionId implements Serializable {

    @Column(name = "theme_id")
    private Long theme;

    @Column(name = "user_id")
    private Long user;
}