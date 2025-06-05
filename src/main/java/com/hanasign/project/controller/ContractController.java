package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.contractdto.request.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.request.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.request.ContractResendRequest;
import com.hanasign.project.dto.contractdto.request.ContractUserRequest;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController extends BaseController {
    private final ContractService contractService;

    @PostMapping("create")
    public ResponseEntity<String> createContract(@RequestBody ContractCreateRequest request) throws CustomException{
            String contractId = contractService.createContract(request);
        return ResponseEntity.ok(contractId);
    }


    @PatchMapping("/update/{contractId}")
    public ResponseEntity<Void> resendContract(@PathVariable String contractId, @RequestBody ContractResendRequest request) {
        contractService.resendContract(Long.valueOf(contractId), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/complete/{contractId}")
    public ResponseEntity<Void> completeContract(@PathVariable String contractId, @RequestBody ContractUserRequest request) {
        contractService.completeContract(Long.valueOf(contractId), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cancel/{contractId}")
    public ResponseEntity<Void> cancelContract(@PathVariable String contractId, @RequestBody ContractCancelRequest request) {
         contractService.cancelContract(Long.valueOf(contractId), request);
        return ResponseEntity.ok().build();
    }




    @GetMapping("/find-all")
    public ResponseEntity<List<Contract>> getContracts(@RequestParam(required = false) String supplierId,
                                                       @RequestParam(required = false) String clientId,
                                                       @RequestParam(required = false) String status) {
        return ResponseEntity.ok(contractService.getContracts(supplierId, clientId, status));
    }
}
