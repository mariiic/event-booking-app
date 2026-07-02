package ru.mariiic.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mariiic.impl.model.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndIsActive(String token, Boolean isActive);

    @Query("select r from RefreshToken r where r.user.userId = :userId and r.isActive = true")
    List<RefreshToken> findAllActiveByUserId(@Param("userId") UUID userId);
}
