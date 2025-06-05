package com.hanasign.project.service;

import com.hanasign.project.dto.ContractHistoryDto.ContractHistoryRequestDto;
import com.hanasign.project.dto.ContractHistoryDto.ContractHistoryResponseDto;
import com.hanasign.project.entity.ContractHistoryEntity;
import com.hanasign.project.enums.ContractStatus;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.ContractHistoryRepository;
import com.hanasign.project.service.ContractHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractHistoryServiceImpl implements ContractHistoryService {

    private final ContractHistoryRepository repository;

    @Override
    public ContractHistoryResponseDto save(ContractHistoryRequestDto dto) throws CustomException {
        if (dto.getContractId() == null) {
            throw Exceptions.HISTORY_CONTRACT_NOT_FOUND;
        }
        if (dto.getStatusBefore() == null || dto.getStatusBefore().trim().isEmpty()) {
            throw Exceptions.HISTORY_STATUS_REQUIRED;
        }
        if (dto.getStatusAfter() == null || dto.getStatusAfter().trim().isEmpty()) {
            throw Exceptions.HISTORY_STATUS_REQUIRED;
        }
        if (dto.getAttachmentBefore() == null || dto.getAttachmentAfter() == null) {
            throw Exceptions.HISTORY_ATTACHMENT_REQUIRED;
        }

        ContractHistoryEntity entity = ContractHistoryEntity.builder()
                .contractId(dto.getContractId())
                .statusBefore(ContractStatus.valueOf(dto.getStatusBefore().toUpperCase()))
                .statusAfter(ContractStatus.valueOf(dto.getStatusAfter().toUpperCase()))
                .attachmentBefore(dto.getAttachmentBefore())
                .attachmentAfter(dto.getAttachmentAfter())
                .build();

        ContractHistoryEntity saved = repository.save(entity);

        return new ContractHistoryResponseDto(
                saved.getId(),
                saved.getAttachmentBefore(),
                saved.getAttachmentAfter(),
                saved.getStatusBefore().name(),
                saved.getStatusAfter().name(),
                saved.getContractId(),
                saved.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @Override
    public List<ContractHistoryResponseDto> getByContractId(Long contractId) throws CustomException {
        List<ContractHistoryEntity> list = repository.findByContractId(contractId);

        if (list.isEmpty()) {
            throw Exceptions.HISTORY_NO_CHANGES;
        }

        return list.stream()
                .map(e -> new ContractHistoryResponseDto(
                        e.getId(),
                        e.getAttachmentBefore(),
                        e.getAttachmentAfter(),
                        e.getStatusBefore().name(),
                        e.getStatusAfter().name(),
                        e.getContractId(),
                        e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .collect(Collectors.toList());
    }
}