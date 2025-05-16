package com.hanasign.project.repository;

import com.hanasign.project.entity.ContractHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractHistoryRepository extends JpaRepository<ContractHistoryEntity, Long> {
    List<ContractHistoryEntity> findByContractId(Long contractId);
}