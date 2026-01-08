package com.egin.user.service;

import com.egin.user.model.User;
import com.egin.user.model.dto.request.UserRegisterRequest;

public interface UserRegisterService {
    User registerUser(final UserRegisterRequest request);
}
