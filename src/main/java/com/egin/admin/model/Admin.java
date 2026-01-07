package com.egin.admin.model;

import com.egin.auth.model.enums.UserStatus;
import com.egin.auth.model.enums.UserType;
import com.egin.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Admin extends BaseModel {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Builder.Default
    private UserType userType = UserType.ADMIN;

    @Builder.Default
    private UserStatus userStatus = UserStatus.ACTIVE;




}
