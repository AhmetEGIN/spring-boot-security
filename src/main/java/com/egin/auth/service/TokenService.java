package com.egin.auth.service;

import com.egin.auth.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Map;
import java.util.Set;

public interface TokenService {

    Token generateToken(final Map<String, Object> claims);
    Token generateToken(final Map<String, Object> claims, final String refreshToken);
    UsernamePasswordAuthenticationToken getAuthentication(final String jwtToken);
    void verifyAndValidate(final String jwtToken);
    void verifyAndValidate(final Set<String> jwtToken);
    Jws<Claims> getClaims(final String jwtToken);
    Claims getPayload(final String jwtToken);
    String getId(final String jwtToken);





}
