package ru.mariiic.api.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequest(@NotBlank String newEmail){
}
