package com.egin.admin.model.mapper;

import com.egin.admin.model.Admin;
import com.egin.admin.model.entity.AdminEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminEntityToAdminMapper {

    public Admin toAdmin(AdminEntity adminEntity) {
        return Admin.builder()
                .id(adminEntity.getId())
                .email(adminEntity.getEmail())
                .password(adminEntity.getPassword())
                .firstName(adminEntity.getFirstName())
                .lastName(adminEntity.getLastName())
                .userType(adminEntity.getUserType())
                .userStatus(adminEntity.getUserStatus())
                .createdAt(adminEntity.getCreatedAt())
                .updatedAt(adminEntity.getUpdatedAt())
                .build();
    }


}
