package com.egin.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomError {

    private HttpStatus httpStatus;

    private String header;

    @JsonInclude(JsonInclude.Include.NON_NULL) // JSON çıktısında null alanların gösterilmemesini sağlar
    private String message;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private final Boolean isSuccess;

    @Getter
    @Builder
    public static class CustomSubError {

        private String message;
        private String field;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object value;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String type;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Header {
        API_ERROR("API ERROR"),
        VALIDATION_ERROR("VALIDATION ERROR"),
        AUTHENTICATION_ERROR("AUTHENTICATION ERROR"),
        ALREADY_EXISTS("ALREADY EXISTS"),
        NOT_FOUND("NOT FOUND"),
        DATABASE_ERROR("DATABASE ERROR"),
        PROCESS_ERROR("PROCESS ERROR");

        private final String name;
    }

}
