package com.egin.user.model.entity;

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
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    public Map<String, Object> getClaims() {

        final Map<String, Object> claims = new HashMap<>();

        claims.put(TokenClaims.USER_ID.getValue(), this.getId());
        claims.put(TokenClaims.USER_TYPE.getValue(), this.getUserType().name());
        claims.put(TokenClaims.USER_STATUS.getValue(), this.getUserStatus().name());
        claims.put(TokenClaims.USER_FIRST_NAME.getValue(), this.getFirstName());
        claims.put(TokenClaims.USER_LAST_NAME.getValue(), this.getLastName());
        claims.put(TokenClaims.USER_EMAIL.getValue(), this.getEmail());

        return claims;
    }


}
