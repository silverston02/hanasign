// 4. UserRepository
package com.hanasign.project.repository;

import com.hanasign.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPw(String email, String pw);

    List<User> findByCompanyId(Long companyId);
}
