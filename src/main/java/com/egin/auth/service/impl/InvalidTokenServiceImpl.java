package com.egin.auth.service.impl;

import com.egin.auth.exception.TokenAlreadyInvalidatedException;
import com.egin.auth.model.entity.InvalidTokenEntity;
import com.egin.auth.repository.InvalidTokenRepository;
import com.egin.auth.service.InvalidTokenService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class InvalidTokenServiceImpl implements InvalidTokenService {

    private final InvalidTokenRepository invalidTokenRepository;

    public InvalidTokenServiceImpl(InvalidTokenRepository invalidTokenRepository) {
        this.invalidTokenRepository = invalidTokenRepository;
    }

    @Override
    public void invalidateTokens(Set<String> tokenIds) {
            final Set<InvalidTokenEntity> invalidTokenEntities = tokenIds.stream()
                    .map(token -> InvalidTokenEntity.builder()
                            .tokenId(token)
                            .build()
                    )
                    .collect(Collectors.toSet());

            invalidTokenRepository.saveAll(invalidTokenEntities);
    }

    @Override
    public void checkForInvalidityOfToken(String tokenId) {
        final boolean isTokenInvalid = invalidTokenRepository.findByTokenId(tokenId).isPresent();
        if (isTokenInvalid) {
            throw new TokenAlreadyInvalidatedException(tokenId);
        }
    }
}
