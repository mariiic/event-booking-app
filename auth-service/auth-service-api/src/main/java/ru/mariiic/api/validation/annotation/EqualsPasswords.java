package ru.mariiic.api.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.mariiic.api.validation.validator.EqualsPasswordsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EqualsPasswordsValidator.class})
public @interface EqualsPasswords {
    String message() default "Введенные пароли должны совпадать ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
