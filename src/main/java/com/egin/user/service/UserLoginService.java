package com.egin.user.service;

import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.LoginRequest;
import jakarta.validation.Valid;

public interface UserLoginService {
    Token login(final LoginRequest request);
}
