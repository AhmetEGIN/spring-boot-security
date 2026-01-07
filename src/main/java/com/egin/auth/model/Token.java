package com.egin.auth.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@Builder
public class Token {

    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresAt;

    private static final String TOKEN_PREFIX = "Bearer ";
    public static boolean isBearerToken(
            final String authHeader
    ) {
        return StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX);
    }

    public static String getJwt(
            final String authHeader
    ) {
        return authHeader.replace(TOKEN_PREFIX, "");
    }



}
