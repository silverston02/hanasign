package com.hanasign.project.service;

import com.hanasign.project.dto.ContractRequestDTO;
import com.hanasign.project.dto.ContractResponseDTO;

import java.util.List;

public interface ContractService {
    ContractResponseDTO createContract(ContractRequestDTO request);
    ContractResponseDTO getContract(Long contractId);
    List<ContractResponseDTO> getAllContracts();
    ContractResponseDTO updateContract(Long contractId, ContractRequestDTO request);
    void deleteContract(Long contractId);
    ContractResponseDTO signContract(Long contractId, String signerId);
    byte[] getContractDocument(Long contractId);
    void cleanupDuplicateSignatures();
}
