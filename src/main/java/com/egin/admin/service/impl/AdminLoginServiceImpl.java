package com.egin.admin.service.impl;

import com.egin.admin.exception.AdminNotFoundException;
import com.egin.admin.model.entity.AdminEntity;
import com.egin.admin.repository.AdminRepository;
import com.egin.admin.service.AdminLoginService;
import com.egin.auth.exception.PasswordNotValidException;
import com.egin.auth.model.Token;
import com.egin.auth.model.dto.request.LoginRequest;
import com.egin.auth.service.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AdminLoginServiceImpl(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public Token login(LoginRequest loginRequest) {
        final AdminEntity adminEntity = adminRepository.findAdminEntityByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new AdminNotFoundException("Admin not found with email: " + loginRequest.getEmail())
                );

        if (Boolean.FALSE.equals(passwordEncoder.matches(
                loginRequest.getPassword(),
                adminEntity.getPassword()
        ))) {
            throw new PasswordNotValidException("Invalid password for email: " + loginRequest.getEmail());
        }

        return tokenService.generateToken(adminEntity.getClaims());
    }


}
