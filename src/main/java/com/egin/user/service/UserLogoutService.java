package com.egin.user.service;

import com.egin.auth.model.dto.request.TokenInvalidateRequest;

public interface UserLogoutService {
    void logout(final TokenInvalidateRequest request);
}
