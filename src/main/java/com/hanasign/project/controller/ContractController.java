package com.hanasign.project.controller;

import com.hanasign.project.dto.contractdto.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.ContractResendRequest;
import com.hanasign.project.dto.contractdto.ContractUserRequest;
import com.hanasign.project.entity.Contract;
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

    @PostMapping("contract-create")
    public ResponseEntity<String> createContract(@RequestBody ContractCreateRequest request) {
        String contractId = contractService.createContract(request);
        return ResponseEntity.ok(contractId);
    }


    @PatchMapping("/contract-resend")
    public ResponseEntity<Void> resendContract(@PathVariable String contractId, @RequestBody ContractResendRequest request) {
        contractService.resendContract(Long.valueOf(contractId), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/contract-complete")
    public ResponseEntity<Void> completeContract(@PathVariable String contractId, @RequestBody ContractUserRequest request) {
        contractService.completeContract(Long.valueOf(contractId), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/contract-cancel")
    public ResponseEntity<Void> cancelContract(@PathVariable String contractId, @RequestBody ContractCancelRequest request) {
         contractService.cancelContract(Long.valueOf(contractId), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contract-find/{contractId}")
    public ResponseEntity<Contract> getContract(@PathVariable String contractId) {
        return ResponseEntity.ok(contractService.getContractById(Long.valueOf(contractId)));
    }


    @GetMapping
    public ResponseEntity<List<Contract>> getContracts(@RequestParam(required = false) String supplierId,
                                                       @RequestParam(required = false) String clientId,
                                                       @RequestParam(required = false) String status) {
        return ResponseEntity.ok(contractService.getContracts(supplierId, clientId, status));
    }
}
