package com.hanasign.project.dto.ContractHistoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractHistoryRequestDto {
    private String attachmentBefore;
    private String attachmentAfter;
    private String statusBefore; // WAITING, IN_PROGRESS 등
    private String statusAfter;
    private Long contractId;
}