package com.egin.user.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "User not found";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(final String message) {
        super(message);
    }
}
