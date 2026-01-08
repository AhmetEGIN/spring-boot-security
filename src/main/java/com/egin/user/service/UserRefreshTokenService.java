package com.egin.user.service;

import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.TokenRefreshRequest;

public interface UserRefreshTokenService {
    Token refreshToken(final TokenRefreshRequest request);
}
