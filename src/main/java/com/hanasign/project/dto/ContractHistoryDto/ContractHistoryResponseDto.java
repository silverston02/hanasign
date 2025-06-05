package com.hanasign.project.dto.ContractHistoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractHistoryResponseDto {
    private Long id;
    private String attachmentBefore;
    private String attachmentAfter;
    private String statusBefore;
    private String statusAfter;
    private Long contractId;
    private String createdAt;
}
