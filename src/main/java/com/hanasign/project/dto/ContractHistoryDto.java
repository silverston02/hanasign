package com.hanasign.project.dto;

import lombok.Data;

@Data
public class ContractHistoryDto {
    private String attachmentBefore;  // 변경 전 첨부파일
    private String attachmentAfter;   // 변경 후 첨부파일
    private String statusBefore;      // 이전 상태 (Waiting, InProgress, Complete, Cancel)
    private String statusAfter;       // 이후 상태
    private Long contractId;          // 어떤 계약의 변경인지
    private String action;
}