package ru.mariiic.userserviceimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mariiic.userserviceimpl.model.Organizer;

import java.util.UUID;

public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {
    @Modifying
    @Query("update Organizer o set o.email = :email where o.id = :userId")
    Organizer updateEmail(@Param("userId") UUID userId, @Param("email") String email);
}
