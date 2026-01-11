package com.egin.user.service.impl;

import com.egin.auth.exception.PasswordNotValidException;
import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.LoginRequest;
import com.egin.auth.service.TokenService;
import com.egin.user.exception.UserNotFoundException;
import com.egin.user.model.entity.UserEntity;
import com.egin.user.repository.UserRepository;
import com.egin.user.service.UserLoginService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserLoginServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public Token login(LoginRequest request) {
        final UserEntity userEntity = userRepository.findUserEntityByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user can't be found: " + request.getEmail()));

        if (Boolean.FALSE.equals(passwordEncoder.matches(request.getPassword(), userEntity.getPassword()))) {
            throw new PasswordNotValidException();
        }
        return tokenService.generateToken(userEntity.getClaims());
    }
}

