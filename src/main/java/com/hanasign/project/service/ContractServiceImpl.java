package com.hanasign.project.service;

import com.hanasign.project.dto.contractdto.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.ContractResendRequest;
import com.hanasign.project.dto.contractdto.ContractUserRequest;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.entity.ContractCommentEntity;
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
    private final AttachmentService attachmentService;

    @Override
    public String createContract(ContractCreateRequest request) {
        Contract contract = new Contract();
        contract.setTitle(request.getTitle());
        contract.setSupplierId(Long.parseLong(request.getSupplierId()));
        contract.setClientId(Long.parseLong(request.getClientId()));
        contract.setStatus(ContractStatus.WAITING);
        contract.setAttachments(String.join(",", request.getAttachments())); // 파일 여러개 일 수 있어 ","로 구분
        contract = contractRepository.save(contract);



        if (request.getComment() != null && !request.getComment().isEmpty()) {
            ContractCommentEntity comment = new ContractCommentEntity();
            comment.setContractId(contract.getId());
            comment.setUserId(contract.getSupplierId());
            comment.setComment(request.getComment());
            comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
            commentRepository.save(comment);
        }

        return contract.getId().toString();
    }


    @Override
    public void resendContract(Long contractId, ContractResendRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));

        contract.setAttachments(String.join(",", request.getAttachments()));
        contract.setStatus(ContractStatus.INPROGRESS);
        contractRepository.save(contract);

        ContractCommentEntity comment = new ContractCommentEntity();
        comment.setContractId(contract.getId());
        comment.setUserId(Long.parseLong(request.getUserId()));
        comment.setComment(request.getComment());
        comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
        commentRepository.save(comment);
    }

    @Override
    public void completeContract(Long contractId, ContractUserRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));
        contract.setStatus(ContractStatus.COMPLETE);
        contractRepository.save(contract);
    }

    @Override
    public void cancelContract(Long contractId, ContractCancelRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));

        contract.setStatus(ContractStatus.CANCEL);
        contractRepository.save(contract);

        ContractCommentEntity comment = new ContractCommentEntity();
        comment.setContractId(contract.getId());
        comment.setUserId(Long.parseLong(request.getUserId()));
        comment.setComment("[취소사유] " + request.getReason());
        comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
        commentRepository.save(comment);
    }

    @Override
    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("계약을 찾을 수 없습니다."));
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
