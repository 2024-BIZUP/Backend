package com.likelion.bizup.module.user.repository;

import com.likelion.bizup.module.user.entity.ValidateUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidateUserRepository extends JpaRepository<ValidateUser, Long> {
    Boolean existsByBno(String bno);
    Boolean existsByCode(String Code);
    Optional<ValidateUser> findByCode(String Code);
}
