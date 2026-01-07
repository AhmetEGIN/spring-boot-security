package com.egin.admin.service;

import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.TokenRefreshRequest;

public interface AdminRefreshTokenService {
    Token refreshToken(final TokenRefreshRequest request);
}
