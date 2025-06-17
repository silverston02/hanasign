package com.hanasign.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanasign.project.dto.contractdto.request.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.request.ContractCompletRequest;
import com.hanasign.project.dto.contractdto.request.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.request.ContractResendRequest;
import com.hanasign.project.entity.*;
import com.hanasign.project.enums.ContractStatus;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.repository.*;
import com.hanasign.project.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hanasign.project.exception.Exceptions;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractServiceImpl implements ContractService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ContractRepository contractRepository;
    private final ContractCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 추가
    private final CompanyRepository companyRepository;
    private final AttachmentRepository attachmentRepository;
    private final ContractHistoryRepository contractHistoryRepository;




    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createContract(ContractCreateRequest request, UserDetails userDetails) throws CustomException, JsonProcessingException {


        // supplier 검증
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Long companyId = user.get().getCompanyId();
        // 현재 LOGIN 되어있는 ID 가 Supplier 어야 한다
        // 현재 로그인 된 id의 company가 존재하는지
        // 현재 로그인된 id가 무조건 supplier로
        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> Exceptions.COMPANY_NOT_FOUND);



        //userDetails.getUsername()
        // 고객 검증
        Long clientId;
        clientId = Long.parseLong(request.getClientId());
        if (companyRepository.findById(clientId).isEmpty()) {
            throw Exceptions.CLIENT_NOT_FOUND;
        }


        // 계약 생성
        Contract contract = new Contract();
        contract.setTitle(request.getTitle());
        contract.setSupplierId(companyId);
        contract.setClientId(clientId);
        contract.setStatus(ContractStatus.WAITING);
        contract = contractRepository.save(contract);


        contract.setAttachments(objectMapper.writeValueAsString(request.getAttachments()));
        contractRepository.save(contract); // 첨부파일 정보 저장

    // 코멘트 추가
        if (request.getComment() != null && !request.getComment().isEmpty()) {
            ContractCommentEntity comment = new ContractCommentEntity();
            comment.setContractId(contract.getId());
            comment.setUserId(companyId);
            comment.setComment(request.getComment());
            comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
            commentRepository.save(comment);
        }

        return contract.getId().toString();
    }


    @Override
    public void acceptContract(Long contractId, UserDetails userDetails) throws CustomException{
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);

        // 사용자 검증
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Long companyId = user.get().getCompanyId();

        boolean isClient = contract.getClientId().equals(companyId);
        ContractStatus status = contract.getStatus();

        //계약 상태 검증
        if(status == ContractStatus.WAITING) {
            if (!isClient) {
                throw Exceptions.CONTRACT_USER_NOT_FOUND;
            } else {
                contract.setStatus(ContractStatus.INPROGRESS);
                contractRepository.save(contract);
                this.logger.info("Contract accepted successfully: {}", contractId);
            }
        }
        else{
            throw Exceptions.STATUS_NOT_WAITING;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateContract(Long contractId, ContractResendRequest request, UserDetails userDetails) throws CustomException, JsonProcessingException {
        // 계약 검증
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);

        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Long companyId = user.get().getCompanyId();

        boolean isSupplier = contract.getSupplierId().equals(companyId);

        // 기존 첨부파일
        String beforeAttachments = contract.getAttachments();
        // 새 첨부파일
        String newAttachments = objectMapper.writeValueAsString(request.getAttachments());

        // 변경 여부 확인
        if(isSupplier) {
            if (!newAttachments.equals(beforeAttachments)) {
                // 첨부파일과 상태 업데이트
                contract.setAttachments(newAttachments);
                contract.setStatus(ContractStatus.INPROGRESS);
                contractRepository.save(contract);

                // 변경 이력 저장
                ContractHistoryEntity historyEntity = new ContractHistoryEntity();
                historyEntity.setContractId(contract.getId());
                historyEntity.setAttachmentBefore(beforeAttachments);
                historyEntity.setAttachmentAfter(newAttachments);
                contractHistoryRepository.save(historyEntity);


                logger.info("Contract {} updated with new attachments.", contractId);
            } else {
                logger.info("No changes in attachments for contract {}. Skipping update.", contractId);
            }
        } else
        {
          throw Exceptions.SUPPLIER_NOT_FOUND;
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 30)
    public void completeContract(Long contractId, UserDetails userDetails) throws CustomException {

        // 계약 검증
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);

        // 사용자 검증
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Long companyId = user.get().getCompanyId();

            // 사용자가 클라이언트인지 공급자인지 확인
            boolean isSupplier = contract.getSupplierId().equals(companyId);
            boolean isClient = contract.getClientId().equals(companyId);

            if (!isClient && !isSupplier) {
                throw Exceptions.CONTRACT_USER_NOT_FOUND;
            }

        // 계약 상태 검증
        ContractStatus status = contract.getStatus();

        if(status == ContractStatus.WAITING){
            throw Exceptions.STATUS_NOT_INPROGRESS;
        }

            // 사용자 유형에 따라 수락 상태 업데이트
            if (isClient) {
                contract.setClientAccepted(true);
            }
            if (isSupplier) {
                contract.setSupplierAccepted(true);
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
            //throw new RuntimeException();

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelContract(Long contractId, ContractCancelRequest request, UserDetails userDetails) throws CustomException{

            // 계약 검증
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> Exceptions.CONTRACT_NOT_FOUND);


            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            Long companyId = user.get().getCompanyId();

            boolean isSupplier = contract.getSupplierId().equals(companyId);
            boolean isClient = contract.getClientId().equals(companyId);

            ContractStatus status = contract.getStatus();

            if(status == ContractStatus.WAITING){
                throw Exceptions.STATUS_NOT_INPROGRESS;
            }

            // 계약 취소 처리
            if (!isClient && !isSupplier) {
                contract.setStatus(ContractStatus.CANCEL);
                contractRepository.save(contract);
            }
            else{
                throw Exceptions.CONTRACT_USER_NOT_FOUND;
            }


            // 취소 사유 코멘트 추가
            ContractCommentEntity comment = new ContractCommentEntity();
            comment.setContractId(contract.getId());
            comment.setUserId(companyId);
            comment.setComment("[취소사유] " + request.getReason());
            comment.setUserType(ContractCommentEntity.UserType.SUPPLIER);
            commentRepository.save(comment);

            this.logger.info("Contract cancelled successfully: {}, reason: {}", contractId, request.getReason());
        }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> getContracts(String supplierId, String clientId, String status, UserDetails userDetails) throws CustomException{

            Long supplierIdLong = null;
            Long clientIdLong = null;
            ContractStatus contractStatus = null;

            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            Long companyId = user.get().getCompanyId();


            // 공급자 ID 파싱
            if (supplierId != null) {

                    supplierIdLong = Long.parseLong(supplierId);
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
                    contractStatus = ContractStatus.valueOf(status);
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

    }

}
