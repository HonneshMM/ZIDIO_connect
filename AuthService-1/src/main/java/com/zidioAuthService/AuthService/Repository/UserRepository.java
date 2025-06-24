package com.zidioAuthService.AuthService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zidioAuthService.AuthService.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}