package com.hanasign.project.service;

import com.hanasign.project.dto.ContractHistoryDto.ContractHistoryRequestDto;
import com.hanasign.project.dto.ContractHistoryDto.ContractHistoryResponseDto;
import com.hanasign.project.exception.CustomException;

import java.util.List;

public interface ContractHistoryService {
    ContractHistoryResponseDto save(ContractHistoryRequestDto dto) throws CustomException;
    List<ContractHistoryResponseDto> getByContractId(Long contractId) throws CustomException;
}