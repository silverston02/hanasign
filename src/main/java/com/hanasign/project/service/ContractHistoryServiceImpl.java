package com.hanasign.project.service;

import com.hanasign.project.dto.ContractHistoryDto;
import com.hanasign.project.entity.ContractHistoryEntity;
import com.hanasign.project.enums.ContractStatus;
import com.hanasign.project.repository.ContractHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractHistoryServiceImpl implements ContractHistoryService {

    private final ContractHistoryRepository repository;

    public ContractHistoryServiceImpl(ContractHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContractHistoryDto save(ContractHistoryDto dto) {
        ContractHistoryEntity entity = new ContractHistoryEntity();
        entity.setAttachmentBefore(dto.getAttachmentBefore());
        entity.setAttachmentAfter(dto.getAttachmentAfter());
        entity.setStatusBefore(ContractStatus.valueOf(dto.getStatusBefore().toUpperCase()));
        entity.setStatusAfter(ContractStatus.valueOf(dto.getStatusAfter().toUpperCase()));
        entity.setContractId(dto.getContractId());

        ContractHistoryEntity saved = repository.save(entity);

        ContractHistoryDto result = new ContractHistoryDto();
        result.setAttachmentBefore(saved.getAttachmentBefore());
        result.setAttachmentAfter(saved.getAttachmentAfter());
        result.setStatusBefore(saved.getStatusBefore().name());
        result.setStatusAfter(saved.getStatusAfter().name());
        result.setContractId(saved.getContractId());
        result.setId(saved.getId());
        return result;
    }

    @Override
    public List<ContractHistoryDto> getByContractId(Long contractId) {
        return repository.findByContractId(contractId).stream().map(entity -> {
            ContractHistoryDto dto = new ContractHistoryDto();
            dto.setAttachmentBefore(entity.getAttachmentBefore());
            dto.setAttachmentAfter(entity.getAttachmentAfter());
            dto.setStatusBefore(entity.getStatusBefore().name());
            dto.setStatusAfter(entity.getStatusAfter().name());
            dto.setContractId(entity.getContractId());
            dto.setId(entity.getId());
            return dto;
        }).collect(Collectors.toList());
    }
}