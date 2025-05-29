package com.hanasign.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractHistoryDto {

    private Long id;                    // 히스토리 ID
    private String attachmentBefore;    // 변경 전 첨부파일
    private String attachmentAfter;     // 변경 후 첨부파일
    private String statusBefore;        // 이전 상태 (WAITING, IN_PROGRESS, ...)
    private String statusAfter;         // 이후 상태
    private Long contractId;            // 계약 ID
}