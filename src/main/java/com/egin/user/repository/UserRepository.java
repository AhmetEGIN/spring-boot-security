package com.egin.user.repository;

import com.egin.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsUserEntityByEmail(String email);
    Optional<UserEntity> findUserEntityByEmail(String email);
}
