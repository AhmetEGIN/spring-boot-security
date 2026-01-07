package com.egin.auth.filter;

import com.egin.auth.model.Token;
import com.egin.auth.service.InvalidTokenService;
import com.egin.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomBearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;


    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final  FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(Token.isBearerToken(authHeader)) {
            final String jwtToken = Token.getJwt(authHeader);
            tokenService.verifyAndValidate(jwtToken);
            final String tokenId = tokenService.getId(jwtToken);
            invalidTokenService.checkForInvalidityOfToken(tokenId);
            final UsernamePasswordAuthenticationToken authentication = tokenService.getAuthentication(jwtToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }
}
