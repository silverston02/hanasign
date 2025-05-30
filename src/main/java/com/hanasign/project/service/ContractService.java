package com.hanasign.project.service;

import com.hanasign.project.dto.contractdto.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.ContractResendRequest;
import com.hanasign.project.dto.contractdto.ContractUserRequest;
import com.hanasign.project.entity.Contract;

import java.util.List;

public interface ContractService {
    String createContract(ContractCreateRequest request);
    void resendContract(Long contractId, ContractResendRequest request);
    void completeContract(Long contractId, ContractUserRequest request);
    void cancelContract(Long contractId, ContractCancelRequest request);
    Contract getContractById(Long contractId);
    List<Contract> getContracts(String supplierId, String clientId, String status);
}
