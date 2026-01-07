package com.egin.auth.model.mapper;

import com.egin.auth.model.Token;
import com.egin.auth.model.dto.response.TokenResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenToTokenResponseMapper {

    public TokenResponse toTokenResponse(
            final Token token
            )
    {
        return TokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiresAt(token.getAccessTokenExpiresAt())
                .build();
    }


}
