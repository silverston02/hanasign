package com.hanasign.project.repository;

import com.hanasign.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByRegistrationNumber(String registrationNumber);
}
