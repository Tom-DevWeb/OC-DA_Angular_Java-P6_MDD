package com.mdd.back.security.repositories;

import com.mdd.back.security.entities.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {

    Optional<Jwt> findByBearer(String bearer);

    @Query("SELECT j FROM Jwt j WHERE j.user.id = :userId AND j.disable = :disable AND j.expired = :expired")
    Optional<Jwt> findByIdUserAndDisableAndExpired(Long userId, Boolean disable, Boolean expired);


    @Query("SELECT j FROM Jwt j WHERE j.user.id = :idUser")
    Stream<Jwt> findAllTokenForThisIdUser(@Param("idUser") Long idUser);

    void deleteAllByExpiredAndDisable(boolean expired, boolean disable);

    Optional<Jwt> findByRefreshTokenValue(String refreshTokenRequest);

}
