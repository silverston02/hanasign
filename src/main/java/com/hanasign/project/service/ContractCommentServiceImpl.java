package com.hanasign.project.service;

import com.hanasign.project.dto.ContractCommentDto;
import com.hanasign.project.entity.ContractCommentEntity;
import com.hanasign.project.repository.ContractCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractCommentServiceImpl implements ContractCommentService {

    private final ContractCommentRepository repository;

    public ContractCommentServiceImpl(ContractCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContractCommentDto save(ContractCommentDto dto) {
        ContractCommentEntity entity = new ContractCommentEntity();
        entity.setComment(dto.getComment());
        entity.setContractId(dto.getContractId());
        entity.setUserId(dto.getUserId());
        entity.setUserType(ContractCommentEntity.UserType.valueOf(dto.getUserType().toUpperCase()));

        ContractCommentEntity saved = repository.save(entity);

        return new ContractCommentDto(
                saved.getId(),
                saved.getComment(),
                saved.getContractId(),
                saved.getUserId(),
                saved.getUserType().name()
        );
    }

    @Override
    public List<ContractCommentDto> getByContractId(Long contractId) {
        return repository.findByContractId(contractId).stream()
                .map(entity -> new ContractCommentDto(
                        entity.getId(),
                        entity.getComment(),
                        entity.getContractId(),
                        entity.getUserId(),
                        entity.getUserType().name()
                )).collect(Collectors.toList());
    }
}