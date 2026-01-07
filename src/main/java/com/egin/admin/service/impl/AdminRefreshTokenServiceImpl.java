package com.egin.admin.service.impl;

import com.egin.admin.exception.AdminNotFoundException;
import com.egin.admin.model.entity.AdminEntity;
import com.egin.admin.repository.AdminRepository;
import com.egin.admin.service.AdminRefreshTokenService;
import com.egin.auth.exception.UserStatusNotValidException;
import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.TokenRefreshRequest;
import com.egin.auth.model.enums.TokenClaims;
import com.egin.auth.model.enums.UserStatus;
import com.egin.auth.service.TokenService;
import org.springframework.stereotype.Service;


@Service
public class AdminRefreshTokenServiceImpl implements AdminRefreshTokenService {

    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    public AdminRefreshTokenServiceImpl(
            AdminRepository adminRepository,
            TokenService tokenService
    ) {
        this.adminRepository = adminRepository;
        this.tokenService = tokenService;
    }

    @Override
    public Token refreshToken(TokenRefreshRequest request) {

        tokenService.verifyAndValidate(request.getRefreshToken());
        final String adminId = tokenService.getPayload(request.getRefreshToken())
                .get(TokenClaims.USER_ID.getValue())
                .toString();

        final AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(AdminNotFoundException::new);

        this.validateAdminStatus(adminEntity);

        return tokenService.generateToken(
                adminEntity.getClaims(),
                request.getRefreshToken()
        );

    }

    private void validateAdminStatus(AdminEntity adminEntity) {
        if (!(UserStatus.ACTIVE.equals(adminEntity.getUserStatus()))) {
            throw new UserStatusNotValidException("Admin status: " + adminEntity.getUserStatus());
        }
    }
}
