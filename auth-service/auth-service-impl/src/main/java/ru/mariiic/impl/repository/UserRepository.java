package ru.mariiic.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mariiic.impl.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByUsername(String username);

    @Modifying
    @Query("update UserAccount u set u.email = :email where u.userId = :userId")
    void updateEmailById(@Param("userId") UUID userId, @Param("email") String email);
}
