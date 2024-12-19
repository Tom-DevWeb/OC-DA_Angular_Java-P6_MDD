package com.mdd.back.repositories;

import com.mdd.back.entities.ThemeSubscription;
import com.mdd.back.entities.ThemeSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeSubscriptionRepository extends JpaRepository<ThemeSubscription, Long> {

    boolean existsById(ThemeSubscriptionId themeSubscriptionId);

    Optional<ThemeSubscription> findById(ThemeSubscriptionId themeSubscriptionId);
}
