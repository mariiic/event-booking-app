package ru.mariiic.api.request;

import jakarta.validation.constraints.NotBlank;

//todo аннотация для проверки паролей
public record ChangePasswordRequest(@NotBlank String oldPassword, @NotBlank String newPassword) {
}
