package com.hanasign.project.service;

import com.hanasign.project.dto.ContractCommentDto;

import java.util.List;

public interface ContractCommentService {
    ContractCommentDto save(ContractCommentDto dto);
    List<ContractCommentDto> getByContractId(Long contractId);
}