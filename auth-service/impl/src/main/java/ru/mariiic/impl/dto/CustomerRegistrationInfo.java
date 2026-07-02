package ru.mariiic.impl.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerRegistrationInfo implements UserRegistrationInfo{
    private String username;
    private String email;
    private String passwordHash;
    private LocalDate birthDate;
    private String emailVerificationCode;
}
