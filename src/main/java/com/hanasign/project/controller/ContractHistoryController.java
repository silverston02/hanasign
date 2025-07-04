package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.ContractHistoryDto.ContractHistoryRequestDto;
import com.hanasign.project.dto.ContractHistoryDto.ContractHistoryResponseDto;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.ContractHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contract-history")
public class ContractHistoryController extends BaseController {

    private final ContractHistoryService service;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveHistory(@RequestBody ContractHistoryRequestDto dto) throws CustomException {
        ContractHistoryResponseDto saved = service.save(dto);
        return createResponseEntity(HttpStatus.OK, "히스토리 저장 성공", saved);
    }

    @GetMapping("/find-all/{contractId}")
    public ResponseEntity<Map<String, Object>> getHistories(@PathVariable Long contractId) throws CustomException {
        List<ContractHistoryResponseDto> list = service.getByContractId(contractId);
        return createResponseEntity(HttpStatus.OK, "히스토리 조회 성공", list);
    }
}