package com.egin.admin.model.mapper;

import com.egin.admin.model.dto.request.AdminRegisterRequest;
import com.egin.admin.model.entity.AdminEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminRegisterRequestToAdminEntityMapper {

    public AdminEntity toAdminEntity(AdminRegisterRequest request) {
        return AdminEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }


}
