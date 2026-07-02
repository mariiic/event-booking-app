package ru.mariiic.impl.exception;

public class IncorrectEmailConfirmationException extends RuntimeException {
    public IncorrectEmailConfirmationException(String message) {
        super(message);
    }
}
