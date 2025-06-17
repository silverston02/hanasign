package com.hanasign.project.repository.company;

import com.hanasign.project.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 회사의 ID로 조회, 삭제되지 않은 회사만 반환
    Optional<Company> findByIdAndDeletedAtIsNull(Long id);
    // 회사의 email 조회, 삭제되지 않은 회사만 반환
    List<Company> findByNameContainingAndDeletedAtIsNull(String name);

}


