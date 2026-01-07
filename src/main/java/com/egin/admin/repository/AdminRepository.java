package com.egin.admin.repository;

import com.egin.admin.model.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, String> {

    Optional<AdminEntity> findAdminEntityByEmail(final String email);
    boolean existsAdminEntityByEmail(final String email);
}
