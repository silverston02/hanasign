package com.hanasign.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.contractdto.request.ContractCancelRequest;
import com.hanasign.project.dto.contractdto.request.ContractCompletRequest;
import com.hanasign.project.dto.contractdto.request.ContractCreateRequest;
import com.hanasign.project.dto.contractdto.request.ContractResendRequest;
import com.hanasign.project.entity.Contract;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController extends BaseController {
    private final ContractService contractService;


    @PostMapping("create")
    public ResponseEntity<String> createContract(@RequestBody ContractCreateRequest request ,@AuthenticationPrincipal UserDetails userDetails) throws CustomException, JsonProcessingException {
            String contractId = contractService.createContract(request, userDetails);
        return ResponseEntity.ok(contractId);
    }

    @PatchMapping("/{contractId}/accept")
    public ResponseEntity<Void> acceptContract(@PathVariable String contractId, @AuthenticationPrincipal UserDetails userDetails) throws CustomException {
        contractService.acceptContract(Long.valueOf(contractId), userDetails);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/update/{contractId}")
    public ResponseEntity<Void> resendContract(@PathVariable String contractId, @RequestBody ContractResendRequest request, @AuthenticationPrincipal UserDetails userDetails) throws CustomException, JsonProcessingException {
        contractService.updateContract(Long.valueOf(contractId), request, userDetails);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{contractId}/complete")
    public ResponseEntity<Void> completeContract(@PathVariable String contractId, @AuthenticationPrincipal UserDetails userDetails) throws CustomException {
        contractService.completeContract(Long.valueOf(contractId), userDetails);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cancel/{contractId}")
    public ResponseEntity<Void> cancelContract(@PathVariable String contractId, @RequestBody ContractCancelRequest request, @AuthenticationPrincipal UserDetails userDetails) throws CustomException {
         contractService.cancelContract(Long.valueOf(contractId), request, userDetails);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/find-all")
    public ResponseEntity<List<Contract>> getContracts(@RequestParam(required = false) String supplierId,
                                                       @RequestParam(required = false) String clientId,
                                                       @RequestParam(required = false) String status,
                                                       @AuthenticationPrincipal UserDetails userDetails) throws CustomException {
        return ResponseEntity.ok(contractService.getContracts(supplierId, clientId, status, userDetails));
    }
}
