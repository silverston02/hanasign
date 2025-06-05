package com.hanasign.project.service;

import com.hanasign.project.dto.ContractCommentDto.ContractCommentRequestDto;
import com.hanasign.project.dto.ContractCommentDto.ContractCommentResponseDto;
import com.hanasign.project.exception.CustomException;

import java.util.List;

public interface ContractCommentService {
    ContractCommentResponseDto save(ContractCommentRequestDto dto) throws CustomException;
    List<ContractCommentResponseDto> getComments(Long contractId, Long supplierId, Long clientId) throws CustomException;
}