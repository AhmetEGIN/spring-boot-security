package com.egin.admin.controller;

import com.egin.admin.model.dto.request.AdminRegisterRequest;
import com.egin.admin.service.AdminLoginService;
import com.egin.admin.service.AdminLogoutService;
import com.egin.admin.service.AdminRefreshTokenService;
import com.egin.admin.service.AdminRegisterService;
import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.LoginRequest;
import com.egin.auth.model.dto.request.TokenInvalidateRequest;
import com.egin.auth.model.dto.request.TokenRefreshRequest;
import com.egin.auth.model.dto.response.TokenResponse;
import com.egin.auth.model.mapper.TokenToTokenResponseMapper;
import com.egin.common.model.dto.response.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/admin")
public class AdminAuthController {

    private final AdminRegisterService adminRegisterService;
    private final AdminLoginService adminLoginService;
    private final AdminRefreshTokenService adminRefreshTokenService;
    private final AdminLogoutService adminLogoutService;
//    private final TokenToTokenResponseMapper tokenToTokenResponseMapper;


    public AdminAuthController(AdminRegisterService adminRegisterService,
                               AdminLoginService adminLoginService,
                               AdminRefreshTokenService adminRefreshTokenService,
                               AdminLogoutService adminLogoutService
//                               TokenToTokenResponseMapper tokenToTokenResponseMapper
    ) {
        this.adminRegisterService = adminRegisterService;
        this.adminLoginService = adminLoginService;
        this.adminRefreshTokenService = adminRefreshTokenService;
        this.adminLogoutService = adminLogoutService;
//        this.tokenToTokenResponseMapper = tokenToTokenResponseMapper;
    }


    @PostMapping("/register")
    public CustomResponse<Void> registerAdmin(
            @RequestBody @Valid final AdminRegisterRequest request
    ) {
        adminRegisterService.registerAdmin(request);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginAdmin(
            @RequestBody @Valid final LoginRequest request
            )
    {
        final Token token = adminLoginService.login(request);
        final TokenResponse tokenResponse = TokenToTokenResponseMapper.toTokenResponse(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshTokenAdmin(
            @RequestBody @Valid final TokenRefreshRequest request
    ) {
        final Token token = adminRefreshTokenService.refreshToken(request);
        final TokenResponse tokenResponse = TokenToTokenResponseMapper.toTokenResponse(token);
        return CustomResponse.successOf(tokenResponse);

    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> logout(
            @RequestBody @Valid final TokenInvalidateRequest request
    ) {
        adminLogoutService.logout(request);
        return CustomResponse.SUCCESS;
    }

}
