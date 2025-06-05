package com.hanasign.project.dto.ContractCommentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCommentResponseDto {
    private Long id;
    private String comment;
    private Long contractId;
    private Long userId;
    private String userType;
    private String createdAt; // optional: 시간 표기용
}