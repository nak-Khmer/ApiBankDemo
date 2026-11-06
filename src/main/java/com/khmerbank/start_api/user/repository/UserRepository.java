package com.khmerbank.start_api.user.repository;

import com.khmerbank.start_api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username); // បន្ថែមបន្ទាត់នេះ
}