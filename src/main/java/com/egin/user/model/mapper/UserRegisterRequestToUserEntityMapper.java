package com.egin.user.model.mapper;

import com.egin.user.model.dto.request.UserRegisterRequest;
import com.egin.user.model.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRegisterRequestToUserEntityMapper {

    public UserEntity toUserEntity(final UserRegisterRequest userRegisterRequest) {

        return UserEntity.builder()
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .build();


    }


}
