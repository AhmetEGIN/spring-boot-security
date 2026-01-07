package com.egin.admin.exception;


public class AdminNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Admin not found";

    public AdminNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public AdminNotFoundException(final String message) {
        super(message);
    }
}
