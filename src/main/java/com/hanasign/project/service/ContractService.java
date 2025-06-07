package com.hanasign.project.service;

import com.hanasign.project.dto.contractdto.request.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.request.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.request.ContractResendRequest;
import com.hanasign.project.dto.contractdto.request.ContractUserRequest;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.exception.CustomException;

import java.util.List;

public interface ContractService {
    String createContract(ContractCreateRequest request) throws CustomException;
    void resendContract(Long contractId, ContractResendRequest request);
    void completeContract(Long contractId, ContractUserRequest request);
    void cancelContract(Long contractId, ContractCancelRequest request);
    List<Contract> getContracts(String supplierId, String clientId, String status);
}
