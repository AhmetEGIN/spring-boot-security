package com.egin.auth.security;

import com.egin.common.model.CustomError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        final CustomError error = CustomError.builder()
                .header(CustomError.Header.AUTHENTICATION_ERROR.getName())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .isSuccess(false)
                .build();

        final String responseBody = mapper
                .writer(DateFormat.getDateInstance())
                .writeValueAsString(error);

        response.getOutputStream().write(responseBody.getBytes());

    }
}
