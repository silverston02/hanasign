package com.hanasign.project.repository.company;

import com.hanasign.project.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByIdAndDeletedAtIsNull(Long id);// soft delete를 고려한 조회
    List<Company> findByNameContainingAndDeletedAtIsNull(String name);

}
