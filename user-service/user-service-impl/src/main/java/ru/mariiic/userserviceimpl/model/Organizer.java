package ru.mariiic.userserviceimpl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Organizer {
    @Id
    private UUID id;

    @Column(unique = true)
    private String organizationName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contactPerson;

    @Column
    private String description;

    @Column
    private String websiteUrl;

    public void addWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Organizer that)) {
            return false;
        }
        return this.id != null && id.equals(that.getId());
    }

    public static Organizer createNew(UUID id, String organizationName, String email, String contactPerson, String description, String websiteUrl) {
        return new Organizer(id, organizationName, email, contactPerson, description, websiteUrl);
    }
}
