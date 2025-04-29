package com.hanasign.project.controller;

import com.hanasign.project.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 웹 페이지 요청을 처리하는 컨트롤러
 * 실제 데이터 처리는 ContractService에서 수행
 */
@Controller
@RequiredArgsConstructor
public class WebController {
    
    // ContractService 의존성 주입
    private final ContractService contractService;

    /**
     * 계약 생성 페이지 요청 처리
     * @return contracts.html 템플릿
     */
    @GetMapping("/contracts")
    public String contracts() {
        return "contracts";
    }

    /**
     * 계약 목록 페이지 요청 처리
     * @return contracts-list.html 템플릿
     */
    @GetMapping("/contracts-list")
    public String contractsList() {
        return "contracts-list";
    }
} 