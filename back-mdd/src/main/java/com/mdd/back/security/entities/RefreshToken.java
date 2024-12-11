package com.mdd.back.security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RefreshToken {

    @Column(name = "refresh_token_expired")
    private boolean expired;

    @Column(name = "refresh_token_value")
    private String value;

    @Column(name = "refresh_token_creation")
    private Instant creation;

    @Column(name = "refresh_token_expiration")
    private Instant expiration;

}
