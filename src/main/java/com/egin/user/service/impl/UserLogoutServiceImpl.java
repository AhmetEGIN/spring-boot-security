package com.egin.user.service.impl;

import com.egin.auth.model.dto.request.TokenInvalidateRequest;
import com.egin.auth.service.InvalidTokenService;
import com.egin.auth.service.TokenService;
import com.egin.user.service.UserLogoutService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserLogoutServiceImpl implements UserLogoutService {

    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;

    public UserLogoutServiceImpl(
            TokenService tokenService,
            InvalidTokenService invalidTokenService
    ) {
        this.tokenService = tokenService;
        this.invalidTokenService = invalidTokenService;
    }

    @Override
    public void logout(TokenInvalidateRequest request) {

        tokenService.verifyAndValidate(Set.of(
                request.getAccessToken(),
                request.getRefreshToken()
        ));

        final String accessTokenId = tokenService.getPayload(request.getAccessToken())
                .getId();
        invalidTokenService.checkForInvalidityOfToken(accessTokenId);

        final String refreshTokenId = tokenService.getPayload(request.getRefreshToken())
                .getId();
        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        invalidTokenService.invalidateTokens(Set.of(accessTokenId, refreshTokenId));

    }
}
