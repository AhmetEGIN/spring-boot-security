package com.egin.admin.service;

import com.egin.auth.model.dto.request.TokenInvalidateRequest;

public interface AdminLogoutService {
    void logout(final TokenInvalidateRequest request);
}
