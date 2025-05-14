package com.hanasign.project.controller;

import com.hanasign.project.dto.ContractHistoryDto;
import com.hanasign.project.service.ContractHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractHistoryController {

    private final ContractHistoryService service;

    public ContractHistoryController(ContractHistoryService service) {
        this.service = service;
    }

    @PostMapping("/contract-histories")
    public ResponseEntity<Void> saveHistory(@RequestBody ContractHistoryDto dto) {
        service.save(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contracts/{contractId}/histories")
    public ResponseEntity<List<ContractHistoryDto>> getHistories(@PathVariable Long contractId) {
        return ResponseEntity.ok(service.getByContractId(contractId));
    }
}