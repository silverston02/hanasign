package com.hanasign.project.dto.ContractCommentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCommentRequestDto {
    private String comment;
    private Long contractId;
    private Long userId;
    private String userType; // "SUPPLIER" or "CLIENT"
}
