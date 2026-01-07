package com.egin.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handeMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    ) {
        Map<String, String> map = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            map.put(fieldName, message);
        });
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> handlePathVariableException(
            final ConstraintViolationException exception
            ) {

        return null;


    }


}
