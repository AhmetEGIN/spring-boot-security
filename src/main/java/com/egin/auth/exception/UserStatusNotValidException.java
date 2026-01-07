package com.egin.auth.exception;

import java.io.Serial;

public class UserStatusNotValidException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 343434334L;

    private static final String DEFAULT_MESSAGE = "User status is not valid for this operation.";

    public UserStatusNotValidException() {
        super(DEFAULT_MESSAGE);
    }
    public UserStatusNotValidException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
