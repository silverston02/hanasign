package com.hanasign.project.controller;

import com.hanasign.project.dto.ContractCommentDto;
import com.hanasign.project.service.ContractCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractCommentController {

    private final ContractCommentService service;

    public ContractCommentController(ContractCommentService service) {
        this.service = service;
    }

    @PostMapping("/contract-comments")
    public ResponseEntity<Void> postComment(@RequestBody ContractCommentDto dto) {
        service.save(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contracts/{contractId}/comments")
    public ResponseEntity<List<ContractCommentDto>> getComments(@PathVariable Long contractId) {
        return ResponseEntity.ok(service.getByContractId(contractId));
    }
}