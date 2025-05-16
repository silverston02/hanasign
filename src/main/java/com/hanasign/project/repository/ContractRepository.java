package com.hanasign.project.repository;

import com.hanasign.project.entity.Contract;
import com.hanasign.project.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findBySupplierId(Long supplierId);
    List<Contract> findByClientId(Long clientId);
    List<Contract> findBySupplierIdAndStatus(Long supplierId, ContractStatus status);
    List<Contract> findByClientIdAndStatus(Long clientId, ContractStatus status);
}
