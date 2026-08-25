package ru.mariiic.userserviceimpl.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends AppException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public ServiceException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
