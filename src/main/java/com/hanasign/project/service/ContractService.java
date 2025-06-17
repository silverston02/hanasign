package com.hanasign.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanasign.project.dto.contractdto.request.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.request.ContractCompletRequest;
import com.hanasign.project.dto.contractdto.request.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.request.ContractResendRequest;
import com.hanasign.project.dto.contractdto.request.ContractCompletRequest;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ContractService {
    String createContract(ContractCreateRequest request, UserDetails userDetails) throws CustomException, JsonProcessingException;
    void acceptContract(Long contractId, UserDetails userDetails) throws CustomException;
    void updateContract(Long contractId, ContractResendRequest request, UserDetails userDetails) throws CustomException, JsonProcessingException;
    void completeContract(Long contractId, UserDetails userDetails)throws CustomException;
    void cancelContract(Long contractId, ContractCancelRequest request, UserDetails userDetails) throws CustomException;
    List<Contract> getContracts(String supplierId, String clientId, String status, UserDetails userDetails) throws CustomException;
}
