package com.ecommerce.project.repository;

import com.ecommerce.project.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(@NotBlank String userName);

    boolean existsByEmail(@NotBlank String email);
}
