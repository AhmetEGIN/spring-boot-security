package com.egin.user.service.impl;

import com.egin.auth.exception.UserStatusNotValidException;
import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.TokenRefreshRequest;
import com.egin.auth.model.enums.TokenClaims;
import com.egin.auth.model.enums.UserStatus;
import com.egin.auth.service.TokenService;
import com.egin.user.exception.UserNotFoundException;
import com.egin.user.model.entity.UserEntity;
import com.egin.user.repository.UserRepository;
import com.egin.user.service.UserRefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class UserRefreshTokenServiceImpl implements UserRefreshTokenService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserRefreshTokenServiceImpl(
            UserRepository userRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public Token refreshToken(TokenRefreshRequest request) {

        tokenService.verifyAndValidate(request.getRefreshToken());
        final String userId = tokenService
                .getPayload(request.getRefreshToken())
                .get(TokenClaims.USER_ID.getValue())
                .toString();

        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        this.validateUserStatus(userEntity);

        return tokenService.generateToken(
                userEntity.getClaims(),
                request.getRefreshToken()
        );

    }

    private void validateUserStatus(UserEntity userEntity) {
        if (!(UserStatus.ACTIVE.equals(userEntity.getUserStatus()))) {
            throw new UserStatusNotValidException();
        }
    }
}
