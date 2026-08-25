package ru.mariiic.api.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.mariiic.api.interfaces.PasswordsMatchable;
import ru.mariiic.api.validation.annotation.EqualsPasswords;

public class EqualsPasswordsValidator implements ConstraintValidator<EqualsPasswords, Object> {
    @Override
    public void initialize(EqualsPasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return true;
        }
        if (!(o instanceof PasswordsMatchable dto)) {
            return false;
        }
        return dto.password().equals(dto.passwordRepeat());
    }
}
