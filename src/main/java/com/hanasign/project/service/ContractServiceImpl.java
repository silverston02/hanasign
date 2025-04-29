package com.hanasign.project.service;

import com.hanasign.project.dto.ContractRequestDTO;
import com.hanasign.project.dto.ContractResponseDTO;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.entity.ContractSignature;
import com.hanasign.project.enums.ContractStatus;
import com.hanasign.project.repository.ContractRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    @Override
    @Transactional
    public ContractResponseDTO createContract(ContractRequestDTO request) {
        Contract contract = new Contract();
        contract.setTitle(request.getTitle());
        contract.setContent(request.getContent());
        contract.setDocumentUrl(request.getDocumentUrl());
        
        Contract savedContract = contractRepository.save(contract);
        return convertToDTO(savedContract);
    }

    @Override
    public ContractResponseDTO getContract(Long contractId) {
        Contract contract = findContract(contractId);
        return convertToDTO(contract);
    }

    @Override
    public List<ContractResponseDTO> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContractResponseDTO updateContract(Long contractId, ContractRequestDTO request) {
        Contract contract = findContract(contractId);
        contract.setTitle(request.getTitle());
        contract.setContent(request.getContent());
        contract.setDocumentUrl(request.getDocumentUrl());
        
        return convertToDTO(contract);
    }

    @Override
    @Transactional
    public void deleteContract(Long contractId) {
        Contract contract = findContract(contractId);
        contractRepository.delete(contract);
    }

    @Override
    @Transactional
    public ContractResponseDTO signContract(Long contractId, String signerId) {
        Contract contract = findContract(contractId);
        
        ContractSignature signature = new ContractSignature();
        signature.setContract(contract);
        signature.setSignerId(signerId);
        
        contract.getSignatures().add(signature);
        
        if (contract.getSignatures().size() == 2) { // 양쪽 모두 서명 완료
            contract.setStatus(ContractStatus.COMPLETED);
        } else {
            contract.setStatus(ContractStatus.SIGNED);
        }
        
        return convertToDTO(contract);
    }

    @Override
    public byte[] getContractDocument(Long contractId) {
        Contract contract = findContract(contractId);
        // TODO: 실제 문서 파일 로직 구현
        return new byte[0];
    }

    private Contract findContract(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
    }

    private ContractResponseDTO convertToDTO(Contract contract) {
        ContractResponseDTO dto = new ContractResponseDTO();
        dto.setId(contract.getId());
        dto.setTitle(contract.getTitle());
        dto.setContent(contract.getContent());
        dto.setDocumentUrl(contract.getDocumentUrl());
        dto.setStatus(contract.getStatus());
        dto.setCreatedAt(contract.getCreatedAt());
        dto.setUpdatedAt(contract.getUpdatedAt());
        
        List<ContractResponseDTO.SignatureDTO> signatureDTOs = contract.getSignatures().stream()
                .map(this::convertToSignatureDTO)
                .collect(Collectors.toList());
        dto.setSignatures(signatureDTOs);
        
        return dto;
    }

    private ContractResponseDTO.SignatureDTO convertToSignatureDTO(ContractSignature signature) {
        ContractResponseDTO.SignatureDTO dto = new ContractResponseDTO.SignatureDTO();
        dto.setId(signature.getId());
        dto.setSignerId(signature.getSignerId());
        dto.setSignatureImageUrl(signature.getSignatureImageUrl());
        dto.setSignedAt(signature.getSignedAt());
        return dto;
    }
} 