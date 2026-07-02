package ru.mariiic.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mariiic.impl.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByUsername(String username);
}
