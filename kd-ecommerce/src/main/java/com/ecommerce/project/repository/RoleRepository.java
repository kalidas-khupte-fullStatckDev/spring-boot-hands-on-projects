package com.ecommerce.project.repository;

import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);

    boolean existsByRoleName(AppRole appRole);
}
