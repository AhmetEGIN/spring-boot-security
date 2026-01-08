package com.egin.user.model.mapper;

import com.egin.user.model.User;
import com.egin.user.model.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserEntityToUserMapper {

    public User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userStatus(userEntity.getUserStatus())
                .userType(userEntity.getUserType())
                .build();
    }


}
