package com.hanasign.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanasign.project.dto.contractdto.request.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.request.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.request.ContractResendRequest;
import com.hanasign.project.dto.contractdto.request.ContractUserRequest;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.entity.ContractCommentEntity;
import com.hanasign.project.enums.ContractStatus;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.repository.ContractCommentRepository;
import com.hanasign.project.repository.ContractRepository;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hanasign.project.exception.Exceptions;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractServiceImpl implements ContractService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ContractRepository contractRepository;
    private final ContractCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 추가

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createContract(ContractCreateRequest request) throws CustomException {
        // 공급자 검증
        Long supplierId;
        try {
            supplierId = Long.parseLong(request.getSupplierId());
            if (!userRepository.findById(supplierId).isPresent()) {  //옵셔널 사용할때 널값으로 예외 처리 가능
                throw Exceptions.SUPPLIER_NOT_FOUND;
            }
        } catch (NumberFormatException e) {
            this.logger.error("supplier를 찾지 못하였습니다: {}", request.getSupplierId());
            throw Exceptions.SUPPLIER_NOT_FOUND;
        }

        // 고객 검증
        Long clientId;
        try {
            clientId = Long.parseLong(request.getClientId());
            if (!userRepository.findById(clientId).isPresent()) {
                throw Exceptions.CLIENT_NOT_FOUND;
            }
        } catch (NumberFormatException e) {
            this.logger.error("client를 찾지 못하였습니다: {}", request.getClientId());          //로거 부분은 영어로 해야되나?
            throw Exceptions.CLIENT_NOT_FOUND;
        }

        // 계약 생성
        Contract contract = new Contract();
        contract.setTitle(request.getTitle());
        contract.setSupplierId(supplierId);
        contract.setClientId(clientId);
        contract.setStatus(ContractStatus.WAITING);
        contract = contractRepository.save(contract);

        try {
            contract.setAttachments(objectMapper.writeValueAsString(request.getAttachments()));
            contractRepository.save(contract); // 첨부파일 정보 저장
        } catch (JsonProcessingException e) {
            this.logger.error("첨부파일을 찾지 못하였습니다: {}", e.getMessage());
            throw Exceptions.FILE_TRANS_ERROR;
        }

        // 코멘트 추가
        if (request.getComment() != null && !request.getComment().isEmpty()) {
            ContractCommentEntity comment = new ContractCommentEntity();
            comment.setContractId(contract.getId());
            comment.setUserId(supplierId);
            comment.setComment(request.getComment());
            comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
            commentRepository.save(comment);
        }

        return contract.getId().toString();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void resendContract(Long contractId, ContractResendRequest request) {
        try {
            // 계약 검증
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);

            // 유정 검증
            Long userId;
            try {
                userId = Long.parseLong(request.getUserId());
                if (!userRepository.findById(userId).isPresent()) {
                    throw Exceptions.USER_NOT_FOUND;
                }
            } catch (NumberFormatException e) {
                this.logger.error("Invalid user ID format: {}", request.getUserId());  //유효하지 않는 사용자 아이디
                throw Exceptions.USER_NOT_FOUND;
            }

            try {
                contract.setAttachments(objectMapper.writeValueAsString(request.getAttachments()));
                contract.setStatus(ContractStatus.INPROGRESS);
                contractRepository.save(contract);
            } catch (JsonProcessingException e) {
                this.logger.error("Error converting attachment information: {}", e.getMessage());
                throw Exceptions.FILE_TRANS_ERROR;
            }

            ContractCommentEntity comment = new ContractCommentEntity();
            comment.setContractId(contract.getId());
            comment.setUserId(userId);
            comment.setComment(request.getComment());
            comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
            commentRepository.save(comment);
        } catch (CustomException e) {
            // 로그
            this.logger.error("Error in resendContract: {}", e.getErrorMessage());
            throw new RuntimeException(e.getErrorMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 30)
    public void completeContract(Long contractId, ContractUserRequest request) {
        try {
            // 계약 검증
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);

            // 사용자 검증
            Long userId = null;
            if (request.getUserId() != null) {
                try {
                    userId = Long.parseLong(request.getUserId());
                    if (!userRepository.findById(userId).isPresent()) {
                        throw Exceptions.USER_NOT_FOUND;
                    }
                } catch (NumberFormatException e) {
                    this.logger.error("Invalid user ID format: {}", request.getUserId());
                    throw Exceptions.USER_NOT_FOUND;
                }
            } else {
                throw Exceptions.USER_NOT_FOUND;
            }

            // 사용자가 클라이언트인지 공급자인지 확인
            boolean isClient = userId.equals(contract.getClientId());
            boolean isSupplier = userId.equals(contract.getSupplierId());

            if (!isClient && !isSupplier) {
                this.logger.error("User is neither client nor supplier for this contract: {}", userId);
                throw Exceptions.CONTRACT_USER_NOT_FOUND;
            }

            // 사용자 유형에 따라 수락 상태 업데이트
            if (isClient) {
                contract.setClientAccepted(true);
                this.logger.info("Client accepted contract: {}", contractId);
            } else if (isSupplier) {
                contract.setSupplierAccepted(true);
                this.logger.info("Supplier accepted contract: {}", contractId);
            }

            // 양쪽 모두 수락했는지 확인해보는 로지크
            if (contract.getClientAccepted() && contract.getSupplierAccepted()) {
                // 계약 완료 처리
                contract.setStatus(ContractStatus.COMPLETE);
                this.logger.info("Contract completed successfully (both parties accepted): {}", contractId);
            } else {
                // 한쪽만 수락한 경우 진행 중
                contract.setStatus(ContractStatus.INPROGRESS);
            }

            contractRepository.save(contract);
        } catch (CustomException e) {
            // 로그 기록
            this.logger.error("Error in completeContract: {}", e.getErrorMessage());
            // RuntimeException으로 래핑하여 상위로 전파
            throw new RuntimeException(e.getErrorMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelContract(Long contractId, ContractCancelRequest request) {
        try {
            // 계약 검증
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);

            // 사용자 검증
            Long userId;
            try {
                userId = Long.parseLong(request.getUserId());
                if (!userRepository.findById(userId).isPresent()) {
                    throw Exceptions.USER_NOT_FOUND;
                }
            } catch (NumberFormatException e) {
                this.logger.error("Invalid user ID format: {}", request.getUserId());
                throw Exceptions.USER_NOT_FOUND;
            }

            // 계약 취소 처리
            contract.setStatus(ContractStatus.CANCEL);
            contractRepository.save(contract);

            // 취소 사유 코멘트 추가
            ContractCommentEntity comment = new ContractCommentEntity();
            comment.setContractId(contract.getId());
            comment.setUserId(userId);
            comment.setComment("[취소사유] " + request.getReason());
            comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
            commentRepository.save(comment);

            this.logger.info("Contract cancelled successfully: {}, reason: {}", contractId, request.getReason());
        } catch (CustomException e) {
            // 로그 기록
            this.logger.error("Error in cancelContract: {}", e.getErrorMessage());
            // RuntimeException으로 래핑하여 상위로 전파
            throw new RuntimeException(e.getErrorMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> getContracts(String supplierId, String clientId, String status) {
        try {
            Long supplierIdLong = null;
            Long clientIdLong = null;
            ContractStatus contractStatus = null;

            // 공급자 ID 파싱
            if (supplierId != null) {
                try {
                    supplierIdLong = Long.parseLong(supplierId);
                } catch (NumberFormatException e) {
                    this.logger.error("Invalid supplier ID format: {}", supplierId);
                    throw new RuntimeException("유효하지 않은 공급자 ID 형식입니다.");
                }
            }

            // 고객 ID 파싱
            if (clientId != null) {
                try {
                    clientIdLong = Long.parseLong(clientId);
                } catch (NumberFormatException e) {
                    this.logger.error("Invalid client ID format: {}", clientId);
                    throw new RuntimeException("유효하지 않은 고객 ID 형식입니다.");
                }
            }

            // 상태 파싱
            if (status != null) {
                try {
                    contractStatus = ContractStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    this.logger.error("Invalid contract status: {}", status);
                    throw new RuntimeException("유효하지 않은 계약 상태입니다.");
                }
            }

            // 모든 조건
            if (supplierIdLong != null && clientIdLong != null && contractStatus != null) {
                return contractRepository.findBySupplierIdAndClientIdAndStatus(
                        supplierIdLong,
                        clientIdLong,
                        contractStatus
                );
            }
            // supplierId와 clientId
            else if (supplierIdLong != null && clientIdLong != null) {
                return contractRepository.findBySupplierIdAndClientId(
                        supplierIdLong,
                        clientIdLong
                );
            }
            // supplierId와 status만
            else if (supplierIdLong != null && contractStatus != null) {
                return contractRepository.findBySupplierIdAndStatus(
                        supplierIdLong,
                        contractStatus
                );
            }
            // clientId와 status만
            else if (clientIdLong != null && contractStatus != null) {
                return contractRepository.findByClientIdAndStatus(
                        clientIdLong,
                        contractStatus
                );
            }
            // supplierId만
            else if (supplierIdLong != null) {
                return contractRepository.findBySupplierId(supplierIdLong);
            }
            // clientId만
            else if (clientIdLong != null) {
                return contractRepository.findByClientId(clientIdLong);
            }
            // status만
            else if (contractStatus != null) {
                return contractRepository.findByStatus(contractStatus);
            }
            // 조건이 제공되지 않을때
            else {
                return contractRepository.findAll(); // 모든 계약 반환 근데 나랑 관련된거만 나오게 수정해야함
            }
        } catch (Exception e) {
            // CustomException이 아닌거
            if (!(e instanceof RuntimeException)) {
                this.logger.error("Unexpected error in getContracts: {}", e.getMessage());
            }
            throw e; // 예외 재전파
        }
    }

}
