package com.egin.auth.exception;

import java.io.Serial;

public class TokenAlreadyInvalidatedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 2323232323L;
    private static final String DEFAULT_MESSAGE = "Token is already invalidated";


    public TokenAlreadyInvalidatedException() {
        super(DEFAULT_MESSAGE);
    }

    public TokenAlreadyInvalidatedException(final String tokenId) {
        super(DEFAULT_MESSAGE + " Token id: " + tokenId);
    }
}
