package ru.mariic.userserviceapi.response;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponse(UUID userId,
                               String username,
                               String email,
                               LocalDate birthDate) {
}
