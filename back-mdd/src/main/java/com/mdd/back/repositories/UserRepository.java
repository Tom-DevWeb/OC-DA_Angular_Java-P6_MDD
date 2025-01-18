package com.mdd.back.repositories;

import com.mdd.back.entities.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotBlank String email);

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.realUsername = :username")
    Optional<User> findByEmailOrUsername(String email, String username);
}
