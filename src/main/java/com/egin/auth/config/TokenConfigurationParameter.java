package com.egin.auth.config;

import com.egin.auth.model.enums.ConfigurationParameters;
import com.egin.auth.utils.KeyConverter;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Configuration
public class TokenConfigurationParameter {

    private final String issuer;
    private final int accessTokenExpirationMinutes;
    private final int refreshTokenExpirationDays;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public TokenConfigurationParameter() {

        this.issuer = ConfigurationParameters.ISSUER.getValue();
        this.accessTokenExpirationMinutes = Integer.parseInt(ConfigurationParameters.AUTH_ACCESS_TOKEN_EXPIRE_MINUTE.getValue());
        this.refreshTokenExpirationDays = Integer.parseInt(ConfigurationParameters.AUTH_REFRESH_TOKEN_EXPIRE_DAY.getValue());
        this.publicKey = KeyConverter.convertPublicKey(ConfigurationParameters.AUTH_PUBLIC_KEY.getValue());
        this.privateKey = KeyConverter.convertPrivateKey(ConfigurationParameters.AUTH_PRIVATE_KEY.getValue());

    }
}
