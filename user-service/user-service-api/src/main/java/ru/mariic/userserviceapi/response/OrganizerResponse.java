package ru.mariic.userserviceapi.response;

import java.util.UUID;

public record OrganizerResponse(UUID userId,
                                String organizationName,
                                String email,
                                String contactPerson,
                                String description,
                                String websiteUrl) {
}
