package com.hanasign.project.service;

import com.hanasign.project.dto.ContractHistoryDto;
import com.hanasign.project.entity.ContractHistoryEntity;
import com.hanasign.project.entity.ContractHistoryEntity.ContractStatus;
import com.hanasign.project.repository.ContractHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractHistoryService {

    private final ContractHistoryRepository repository;

    public ContractHistoryService(ContractHistoryRepository repository) {
        this.repository = repository;
    }

    public void save(ContractHistoryDto dto) {
        ContractHistoryEntity entity = new ContractHistoryEntity();
        entity.setAttachmentBefore(dto.getAttachmentBefore());
        entity.setAttachmentAfter(dto.getAttachmentAfter());
        entity.setStatusBefore(ContractStatus.valueOf(dto.getStatusBefore()));
        entity.setStatusAfter(ContractStatus.valueOf(dto.getStatusAfter()));
        entity.setContractId(dto.getContractId());
        repository.save(entity);
    }

    public List<ContractHistoryDto> getByContractId(Long contractId) {
        return repository.findByContractId(contractId).stream().map(entity -> {
            ContractHistoryDto dto = new ContractHistoryDto();
            dto.setAttachmentBefore(entity.getAttachmentBefore());
            dto.setAttachmentAfter(entity.getAttachmentAfter());
            dto.setStatusBefore(entity.getStatusBefore().name());
            dto.setStatusAfter(entity.getStatusAfter().name());
            dto.setContractId(entity.getContractId());
            return dto;
        }).collect(Collectors.toList());
    }
}