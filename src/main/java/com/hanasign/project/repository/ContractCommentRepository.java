package com.hanasign.project.repository;

import com.hanasign.project.entity.ContractCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractCommentRepository extends JpaRepository<ContractCommentEntity, Long> {
    List<ContractCommentEntity> findByContractId(Long contractId);
}
