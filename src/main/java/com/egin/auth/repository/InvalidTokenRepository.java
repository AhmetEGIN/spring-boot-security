package com.egin.auth.repository;

import com.egin.auth.model.entity.InvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvalidTokenRepository extends JpaRepository<InvalidTokenEntity, String> {
    Optional<InvalidTokenEntity> findByTokenId(String tokenId);
}
