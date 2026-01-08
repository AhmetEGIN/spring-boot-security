package com.egin.user.model.mapper;

import com.egin.user.model.User;
import com.egin.user.model.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserToUserEntityMapper {

    public UserEntity toUserEntity(final User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .userStatus(user.getUserStatus())
                .build();
    }


}
