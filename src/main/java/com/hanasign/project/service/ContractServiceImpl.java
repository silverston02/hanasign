package com.hanasign.project.service;

import com.hanasign.project.dto.*;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.entity.ContractComment;
import com.hanasign.project.enums.ContractStatus;
import com.hanasign.project.repository.ContractCommentRepository;
import com.hanasign.project.repository.ContractRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractCommentRepository commentRepository;

    @Override
    public String createContract(ContractCreateRequest request) {
        Contract contract = new Contract();
        contract.setTitle(request.getTitle());
        contract.setSupplierId(Long.parseLong(request.getSupplierId()));
        contract.setClientId(Long.parseLong(request.getClientId()));
        contract.setStatus(ContractStatus.Waiting);
        contract.setAttachments(String.join(",", request.getAttachments()));

        contract = contractRepository.save(contract);

        if (request.getComment() != null && !request.getComment().isEmpty()) {
            ContractComment comment = new ContractComment();
            comment.setContract(contract);
            comment.setUserId(contract.getSupplierId());
            comment.setComment(request.getComment());
            commentRepository.save(comment);
        }

        return contract.getId().toString();
    }

    @Override
    public void addComment(Long contractId, ContractCommentRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));

        ContractComment comment = new ContractComment();
        comment.setContract(contract);
        comment.setUserId(Long.parseLong(request.getUserId()));
        comment.setComment(request.getComment());
        commentRepository.save(comment);
    }

    @Override
    public void resendContract(Long contractId, ContractResendRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));

        contract.setAttachments(String.join(",", request.getAttachments()));
        contract.setStatus(ContractStatus.InProgress);
        contractRepository.save(contract);

        ContractComment comment = new ContractComment();
        comment.setContract(contract);
        comment.setUserId(Long.parseLong(request.getUserId()));
        comment.setComment(request.getComment());
        commentRepository.save(comment);
    }

    @Override
    public void completeContract(Long contractId, ContractUserRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));
        contract.setStatus(ContractStatus.Complete);
        contractRepository.save(contract);
    }

    @Override
    public void cancelContract(Long contractId, ContractCancelRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));
        contract.setStatus(ContractStatus.Cancel);
        contractRepository.save(contract);

        ContractComment comment = new ContractComment();
        comment.setContract(contract);
        comment.setUserId(Long.parseLong(request.getUserId()));
        comment.setComment("[취소사유] " + request.getReason());
        commentRepository.save(comment);
    }

    @Override
    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));
    }

    @Override
    public List<ContractComment> getComments(Long contractId) {
        return commentRepository.findByContract_Id(contractId);
    }

    @Override
    public List<Contract> getContracts(String supplierId, String clientId, String status) {
        if (supplierId != null && status != null) {
            return contractRepository.findBySupplierIdAndStatus(Long.parseLong(supplierId), ContractStatus.valueOf(status));
        } else if (clientId != null && status != null) {
            return contractRepository.findByClientIdAndStatus(Long.parseLong(clientId), ContractStatus.valueOf(status));
        } else if (supplierId != null) {
            return contractRepository.findBySupplierId(Long.parseLong(supplierId));
        } else if (clientId != null) {
            return contractRepository.findByClientId(Long.parseLong(clientId));
        } else {
            return contractRepository.findAll();
        }
    }
}
