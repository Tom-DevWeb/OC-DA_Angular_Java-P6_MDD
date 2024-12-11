package com.mdd.back.security.entities;

import com.mdd.back.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "jwt")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean disable;

    private boolean expired;

    private String bearer;

    @Embedded
    private RefreshToken refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
