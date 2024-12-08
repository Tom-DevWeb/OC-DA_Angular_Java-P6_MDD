package com.mdd.back.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ThemeSubscriptionId implements Serializable {
    private Long theme;
    private Long user;
}