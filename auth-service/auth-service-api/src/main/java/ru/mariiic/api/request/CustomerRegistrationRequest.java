package ru.mariiic.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import ru.mariiic.api.interfaces.PasswordsMatchable;
import ru.mariiic.api.validation.annotation.EqualsPasswords;

import java.time.LocalDate;

@EqualsPasswords
public record CustomerRegistrationRequest(@NotBlank String username,
                                          @NotBlank String password,
                                          @NotBlank String passwordRepeat,
                                          @Email @NotBlank String email,
                                          @Past @NotNull LocalDate birthDate) implements PasswordsMatchable {
}
