package com.egin.auth.service.impl;

import com.egin.auth.config.TokenConfigurationParameter;
import com.egin.auth.model.Token;
import com.egin.auth.model.enums.ConfigurationParameters;
import com.egin.auth.model.enums.TokenClaims;
import com.egin.auth.model.enums.TokenType;
import com.egin.auth.model.enums.UserType;
import com.egin.auth.service.InvalidTokenService;
import com.egin.auth.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenConfigurationParameter tokenConfigurationParameters;
    private final InvalidTokenService invalidTokenService;

    public TokenServiceImpl(TokenConfigurationParameter tokenConfigurationParameters, InvalidTokenService invalidTokenService) {
        this.tokenConfigurationParameters = tokenConfigurationParameters;
        this.invalidTokenService = invalidTokenService;
    }

    @Override
    public Token generateToken(Map<String, Object> claims) {

        final long currentTimeMillis = System.currentTimeMillis();
        final Date tokenIssuedAt = new Date(currentTimeMillis);
        final Date accessTokenExpiration = DateUtils.addMinutes(
            new Date(currentTimeMillis),
                tokenConfigurationParameters.getAccessTokenExpirationMinutes()
        );

        final String accessToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(ConfigurationParameters.ISSUER.getValue())
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiration)
                .signWith(tokenConfigurationParameters.getPrivateKey())
                .claims(claims)
                .compact();

        final Date refreshTokenExpiration = DateUtils.addDays(
                new Date(currentTimeMillis),
                tokenConfigurationParameters.getRefreshTokenExpirationDays()
        );

        final String refreshToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameters.getIssuer())
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiration)
                .signWith(tokenConfigurationParameters.getPrivateKey())
                .claim(TokenClaims.USER_ID.getValue(), claims.get(TokenClaims.USER_ID.getValue()))
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiration.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public Token generateToken(Map<String, Object> claims, String refreshToken) {
        final long currentTimeMillis = System.currentTimeMillis();
        final String refreshTokenId = this.getId(refreshToken);
        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);
        final Date accessTokenIssuedAt = new Date(currentTimeMillis);
        final Date accessTokenExpiration = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                tokenConfigurationParameters.getAccessTokenExpirationMinutes()
        );

        final String accessToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameters.getIssuer())
                .issuedAt(accessTokenIssuedAt)
                .expiration(accessTokenExpiration)
                .signWith(tokenConfigurationParameters.getPrivateKey())
                .claims(claims)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiration.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {

        final Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(tokenConfigurationParameters.getPublicKey())
                .build()
                .parseSignedClaims(jwtToken);

        final JwsHeader jwsHeader = claimsJws.getHeader();
        final Claims payload = claimsJws.getPayload();

        final Jwt jwt = new Jwt(
                jwtToken,
                payload.getIssuedAt().toInstant(),
                payload.getExpiration().toInstant(),
                Map.of(
                        TokenClaims.TYP.getValue(), jwsHeader.getType(),
                        TokenClaims.ALGORITHM.getValue(), jwsHeader.getAlgorithm()
                ),
                payload
        );

        final UserType userType = UserType.valueOf(
                payload.get(TokenClaims.USER_TYPE.getValue()).toString()
        );

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userType.name()));

        return UsernamePasswordAuthenticationToken
                .authenticated(jwt, null, authorities);

    }

    @Override
    public void verifyAndValidate(String jwtToken) {

        Jwts.parser()
                .verifyWith(tokenConfigurationParameters.getPublicKey())
                .build()
                .parseSignedClaims(jwtToken);

    }

    @Override
    public void verifyAndValidate(Set<String> jwtToken) {
        jwtToken.forEach(this::verifyAndValidate);
    }

    @Override
    public Jws<Claims> getClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameters.getPublicKey())
                .build()
                .parseSignedClaims(jwtToken);
    }

    @Override
    public Claims getPayload(String jwtToken) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameters.getPublicKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    @Override
    public String getId(String jwtToken) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameters.getPublicKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getId();
    }
}
