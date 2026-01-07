package com.egin.admin.exception;

public class AdminAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Admin already exist";

    public AdminAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
