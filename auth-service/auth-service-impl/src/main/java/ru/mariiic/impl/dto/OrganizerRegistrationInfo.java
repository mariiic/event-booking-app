package ru.mariiic.impl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizerRegistrationInfo implements UserRegistrationInfo {
    private String organizationName;
    private String passwordHash;
    private String email;
    private String contactPerson;
    private String description;
    private String websiteUrl;
    private String emailVerificationCode;
}
