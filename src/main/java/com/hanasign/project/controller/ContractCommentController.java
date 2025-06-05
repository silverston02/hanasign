package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.ContractCommentDto.ContractCommentRequestDto;
import com.hanasign.project.dto.ContractCommentDto.ContractCommentResponseDto;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.ContractCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class ContractCommentController extends BaseController {

    private final ContractCommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveComment(@RequestBody ContractCommentRequestDto dto) throws CustomException {
        ContractCommentResponseDto saved = commentService.save(dto);
        return createResponseEntity(HttpStatus.OK, "댓글 저장 성공", saved);
    }

    @GetMapping("/find")
    public ResponseEntity<Map<String, Object>> getComments(
            @RequestParam Long contractId,
            @RequestParam Long supplierId,
            @RequestParam Long clientId) throws CustomException {

        List<ContractCommentResponseDto> comments = commentService.getComments(contractId, supplierId, clientId);
        return createResponseEntity(HttpStatus.OK, "댓글 조회 성공", comments);
    }
}