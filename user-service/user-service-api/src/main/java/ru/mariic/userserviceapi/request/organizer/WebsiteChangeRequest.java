package ru.mariic.userserviceapi.request.organizer;

import jakarta.validation.constraints.NotBlank;

public record WebsiteChangeRequest(@NotBlank String websiteUrl) {
}
