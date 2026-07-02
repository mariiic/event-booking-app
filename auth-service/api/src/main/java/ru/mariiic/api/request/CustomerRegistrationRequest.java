package ru.mariiic.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

//todo аннотация на совпадение паролей
public record CustomerRegistrationRequest(@NotBlank String username,
                                          @NotBlank String password,
                                          @NotBlank String passwordRepeat,
                                          @Email @NotBlank String email,
                                          @Past @NotNull LocalDate birthDate) {
}
