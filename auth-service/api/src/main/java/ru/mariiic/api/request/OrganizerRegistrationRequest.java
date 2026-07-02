package ru.mariiic.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OrganizerRegistrationRequest(@NotBlank String organizationName,
                                           @NotBlank String password,
                                           @NotBlank String passwordRepeat,
                                           @Email String email,
                                           @NotBlank String contact_person,
                                           @NotBlank String description,
                                           String websiteUrl
                                           ) {
}
