package ru.mariiic.userserviceimpl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    @Id
    private UUID id;

    @Column(nullable = false,  unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    LocalDate birthDate;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Customer that)) {
            return false;
        }
        return this.id != null && id.equals(that.getId());
    }

    public static Customer createNew(UUID id, String username, String email, LocalDate birthDate) {
        return new Customer(id, username, email, birthDate);
    }
}
