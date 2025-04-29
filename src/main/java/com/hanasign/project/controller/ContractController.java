package com.hanasign.project.controller;

import com.hanasign.project.dto.ContractRequestDTO;
import com.hanasign.project.dto.ContractResponseDTO;
import com.hanasign.project.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {
    
    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(@RequestBody ContractRequestDTO request) {
        return ResponseEntity.ok(contractService.createContract(request));
    }

    @GetMapping("/{contractId}")
    public ResponseEntity<ContractResponseDTO> getContract(@PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.getContract(contractId));
    }

    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<ContractResponseDTO> updateContract(
            @PathVariable Long contractId,
            @RequestBody ContractRequestDTO request) {
        return ResponseEntity.ok(contractService.updateContract(contractId, request));
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{contractId}/sign")
    public ResponseEntity<ContractResponseDTO> signContract(
            @PathVariable Long contractId,
            @RequestParam String signerId) {
        return ResponseEntity.ok(contractService.signContract(contractId, signerId));
    }

    @GetMapping("/{contractId}/document")
    public ResponseEntity<byte[]> getContractDocument(@PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.getContractDocument(contractId));
    }
}
