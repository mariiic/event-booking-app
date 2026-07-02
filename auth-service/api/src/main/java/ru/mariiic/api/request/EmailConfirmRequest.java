package ru.mariiic.api.request;

import jakarta.validation.constraints.NotBlank;

public record EmailConfirmRequest(@NotBlank String email, @NotBlank String emailConfirm) {
}
