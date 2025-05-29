package com.hanasign.project.repository;

import com.hanasign.project.entity.Contract;
import com.hanasign.project.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findById(Long id);
    
    Optional<Contract> findBySupplierIdAndClientId(Long supplierId, Long clientId);
    
    Optional<List<Contract>> findBySupplierIdAndStatusOrderByCreatedAtDesc(Long supplierId, ContractStatus status);
    
    Optional<List<Contract>> findByClientIdAndStatusOrderByCreatedAtDesc(Long clientId, ContractStatus status);
    

    List<Contract> findBySupplierId(Long supplierId);
    List<Contract> findByClientId(Long clientId);
    List<Contract> findBySupplierIdAndStatus(Long supplierId, ContractStatus status);
    List<Contract> findByClientIdAndStatus(Long clientId, ContractStatus status);
}