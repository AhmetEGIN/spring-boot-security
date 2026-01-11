package com.egin.admin.service.impl;

import com.egin.admin.service.AdminLogoutService;
import com.egin.auth.model.dto.request.TokenInvalidateRequest;
import com.egin.auth.service.InvalidTokenService;
import com.egin.auth.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminLogoutServiceImpl implements AdminLogoutService {

    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;

    public AdminLogoutServiceImpl(
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


