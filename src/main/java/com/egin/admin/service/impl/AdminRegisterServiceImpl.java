package com.egin.admin.service.impl;

import com.egin.admin.exception.AdminAlreadyExistsException;
import com.egin.admin.model.Admin;
import com.egin.admin.model.dto.request.AdminRegisterRequest;
import com.egin.admin.model.entity.AdminEntity;
import com.egin.admin.model.mapper.AdminEntityToAdminMapper;
import com.egin.admin.model.mapper.AdminRegisterRequestToAdminEntityMapper;
import com.egin.admin.repository.AdminRepository;
import com.egin.admin.service.AdminRegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminRegisterServiceImpl implements AdminRegisterService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminRegisterServiceImpl(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Admin registerAdmin(AdminRegisterRequest request) {

        if (adminRepository.existsAdminEntityByEmail(request.getEmail())) {
            throw new AdminAlreadyExistsException();
        }
        final AdminEntity adminEntityToSave = AdminRegisterRequestToAdminEntityMapper.toAdminEntity(request);
        adminEntityToSave.setPassword(passwordEncoder.encode(request.getPassword()));
        AdminEntity savedAdminEntity = adminRepository.save(adminEntityToSave);

        return AdminEntityToAdminMapper.toAdmin(savedAdminEntity);
    }
}
