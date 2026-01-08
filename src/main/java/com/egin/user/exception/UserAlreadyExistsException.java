package com.egin.user.exception;

public class UserAlreadyExistsException extends  RuntimeException{

    private static final String DEFAULT_MESSAGE = "User already exists";

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
