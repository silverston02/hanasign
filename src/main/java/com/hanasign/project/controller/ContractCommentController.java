package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.ContractCommentDto;
import com.hanasign.project.service.ContractCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contract-comment")
public class ContractCommentController extends BaseController {

    private final ContractCommentService service;

    public ContractCommentController(ContractCommentService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody ContractCommentDto dto) {
        ContractCommentDto saved = service.save(dto);
        return createResponseEntity(HttpStatus.OK, "코멘트 저장 성공", saved);
    }

    @GetMapping("/find/{contractId}")
    public ResponseEntity<Map<String, Object>> findByContract(@PathVariable Long contractId) {
        List<ContractCommentDto> list = service.getByContractId(contractId);
        return createResponseEntity(HttpStatus.OK, "코멘트 조회 성공", list);
    }
}