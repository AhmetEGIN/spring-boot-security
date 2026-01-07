package com.egin.admin.service;

import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.LoginRequest;

public interface AdminLoginService {
    Token login(final LoginRequest request);
}
