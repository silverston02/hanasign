package com.hanasign.project.service;

import com.hanasign.project.dto.*;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.entity.ContractComment;

import java.util.List;

public interface ContractService {
    String createContract(ContractCreateRequest request);
    void addComment(Long contractId, ContractCommentRequest request);
    void resendContract(Long contractId, ContractResendRequest request);
    void completeContract(Long contractId, ContractUserRequest request);
    void cancelContract(Long contractId, ContractCancelRequest request);
    Contract getContractById(Long contractId);
    List<ContractComment> getComments(Long contractId);
    List<Contract> getContracts(String supplierId, String clientId, String status);
}
