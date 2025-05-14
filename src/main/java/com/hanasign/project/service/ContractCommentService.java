package com.hanasign.project.service;

import com.hanasign.project.dto.ContractCommentDto;
import com.hanasign.project.entity.ContractCommentEntity;
import com.hanasign.project.repository.ContractCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractCommentService {

    private final ContractCommentRepository repository;

    public ContractCommentService(ContractCommentRepository repository) {
        this.repository = repository;
    }

    public void save(ContractCommentDto dto) {
        ContractCommentEntity entity = new ContractCommentEntity();
        entity.setComment(dto.getComment());
        entity.setContractId(dto.getContractId());
        entity.setUserId(dto.getUserId());
        entity.setUserType(ContractCommentEntity.UserType.valueOf(dto.getUserType()));
        repository.save(entity);
    }

    public List<ContractCommentDto> getByContractId(Long contractId) {
        return repository.findByContractId(contractId).stream().map(entity -> {
            ContractCommentDto dto = new ContractCommentDto();
            dto.setComment(entity.getComment());
            dto.setContractId(entity.getContractId());
            dto.setUserId(entity.getUserId());
            dto.setUserType(entity.getUserType().name());
            return dto;
        }).collect(Collectors.toList());
    }
}