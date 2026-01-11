package com.egin.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Product already exist";

    public ProductAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
