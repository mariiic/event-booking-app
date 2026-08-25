package ru.mariiic.userserviceimpl.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {
    public NotFoundException(String message) {
        super(message, "not found", HttpStatus.NOT_FOUND);
    }
}
