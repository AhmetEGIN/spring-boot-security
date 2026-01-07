package com.egin.admin.model.mapper;

import com.egin.admin.model.Admin;
import com.egin.admin.model.entity.AdminEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminToAdminEntityMapper {

    public AdminEntity toAdminEntity(Admin admin) {
        return AdminEntity.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .userStatus(admin.getUserStatus())
                .userType(admin.getUserType())
                .createdAt(admin.getCreatedAt())
                .build();
    }


}
