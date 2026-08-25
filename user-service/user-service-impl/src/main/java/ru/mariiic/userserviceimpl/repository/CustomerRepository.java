package ru.mariiic.userserviceimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mariiic.userserviceimpl.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Modifying
    @Query("update Customer c set c.email = :email where c.id = :userId")
    Customer updateEmail(@Param("userId") UUID userId, @Param("email") String email);
}
