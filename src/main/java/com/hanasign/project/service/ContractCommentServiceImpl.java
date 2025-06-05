package com.hanasign.project.service;

import com.hanasign.project.dto.ContractCommentDto.ContractCommentRequestDto;
import com.hanasign.project.dto.ContractCommentDto.ContractCommentResponseDto;
import com.hanasign.project.entity.ContractCommentEntity;
import com.hanasign.project.entity.ContractCommentEntity.UserType;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.ContractCommentRepository;
import com.hanasign.project.service.ContractCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractCommentServiceImpl implements ContractCommentService {

    private final ContractCommentRepository repository;

    @Override
    public ContractCommentResponseDto save(ContractCommentRequestDto dto) throws CustomException {
        if (dto.getComment() == null || dto.getComment().trim().isEmpty()) {
            throw Exceptions.COMMENT_CONTENT_REQUIRED;
        }
        if (dto.getContractId() == null) {
            throw Exceptions.COMMENT_CONTRACT_NOT_FOUND;
        }
        if (dto.getUserId() == null) {
            throw Exceptions.COMMENT_WRITER_REQUIRED;
        }
        if (dto.getUserType() == null ||
                (!dto.getUserType().equalsIgnoreCase("SUPPLIER") && !dto.getUserType().equalsIgnoreCase("CLIENT"))) {
            throw Exceptions.COMMENT_WRITER_REQUIRED;
        }

        ContractCommentEntity entity = ContractCommentEntity.builder()
                .comment(dto.getComment())
                .contractId(dto.getContractId())
                .userId(dto.getUserId())
                .userType(UserType.valueOf(dto.getUserType().toUpperCase()))
                .build();

        ContractCommentEntity saved = repository.save(entity);

        return new ContractCommentResponseDto(
                saved.getId(),
                saved.getComment(),
                saved.getContractId(),
                saved.getUserId(),
                saved.getUserType().toString(),
                saved.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @Override
    public List<ContractCommentResponseDto> getComments(Long contractId, Long supplierId, Long clientId) throws CustomException {
        List<ContractCommentEntity> entities = repository.findByContractId(contractId);

        if (entities.isEmpty()) {
            throw Exceptions.COMMENT_LIST_EMPTY;
        }

        return entities.stream()
                .map(e -> new ContractCommentResponseDto(
                        e.getId(),
                        e.getComment(),
                        e.getContractId(),
                        e.getUserId(),
                        e.getUserType().toString(),
                        e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .collect(Collectors.toList());
    }
}