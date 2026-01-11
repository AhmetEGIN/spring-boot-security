package com.egin.admin.model.entity;

import com.egin.auth.model.enums.TokenClaims;
import com.egin.auth.model.enums.UserStatus;
import com.egin.auth.model.enums.UserType;
import com.egin.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
public class AdminEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.ADMIN;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    public Map<String, Object> getClaims() {

        final Map<String, Object> claims = new HashMap<>();

        claims.put(TokenClaims.USER_ID.getValue(), id);
        claims.put(TokenClaims.USER_TYPE.getValue(), userType.name());
        claims.put(TokenClaims.USER_STATUS.getValue(), userStatus.name());
        claims.put(TokenClaims.USER_EMAIL.getValue(), email);
        claims.put(TokenClaims.USER_FIRST_NAME.getValue(), firstName);
        claims.put(TokenClaims.USER_LAST_NAME.getValue(), lastName);

        return claims;
    }


}
