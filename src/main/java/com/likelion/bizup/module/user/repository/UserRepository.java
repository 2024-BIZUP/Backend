package com.likelion.bizup.module.user.repository;

import com.likelion.bizup.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserid(String userId);
    Optional<User> findByUserid(String userId);
}
