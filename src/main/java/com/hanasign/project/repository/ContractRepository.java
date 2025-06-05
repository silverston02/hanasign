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
    List<Contract> findBySupplierId(Long supplierId);
    List<Contract> findByClientId(Long clientId);
    List<Contract> findByStatus(ContractStatus status);
    List<Contract> findBySupplierIdAndStatus(Long supplierId, ContractStatus status);
    List<Contract> findByClientIdAndStatus(Long clientId, ContractStatus status);

    List<Contract> findBySupplierIdAndClientIdAndStatus(Long supplierId, Long clientId, ContractStatus status);
    List<Contract> findBySupplierIdAndClientId(Long supplierId, Long clientId);

    // 정렬 기능
    List<Contract> findBySupplierIdOrderByCreatedAtDesc(Long supplierId);
    List<Contract> findByClientIdOrderByCreatedAtDesc(Long clientId);
    List<Contract> findBySupplierIdAndStatusOrderByCreatedAtDesc(Long supplierId, ContractStatus status);
    List<Contract> findByClientIdAndStatusOrderByCreatedAtDesc(Long clientId, ContractStatus status);
    List<Contract> findBySupplierIdAndClientIdAndStatusOrderByCreatedAtDesc(Long supplierId, Long clientId, ContractStatus status);
    List<Contract> findBySupplierIdAndClientIdOrderByCreatedAtDesc(Long supplierId, Long clientId);
}
