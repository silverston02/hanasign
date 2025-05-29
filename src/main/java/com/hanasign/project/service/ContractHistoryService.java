package com.hanasign.project.service;

import com.hanasign.project.dto.ContractHistoryDto;

import java.util.List;

public interface ContractHistoryService {
    ContractHistoryDto save(ContractHistoryDto dto);
    List<ContractHistoryDto> getByContractId(Long contractId);
}