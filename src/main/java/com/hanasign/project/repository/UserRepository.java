// 4. UserRepository
package com.hanasign.project.repository;

import com.hanasign.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByEmailAndPw(String email, String pw);
//
//    List<User> findByCompanyId(Long companyId);

//    // Company 의 사업자번호로 User 조회
//    @Query("SELECT u FROM User u JOIN u.company c WHERE c.businessNumber = :businessNumber")
//    List<User> findUsersByCompanyBusinessNumber(@Param("businessNumber") String businessNumber);
//
//    // 유저와 그 유저의 회사 정보 함께 조회
//    @Query("SELECT u FROM User u JOIN FETCH u.company WHERE u.id = :userId")
//    Optional<User> findUserWithCompanyById(@Param("userId") Long userId);

    List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}


