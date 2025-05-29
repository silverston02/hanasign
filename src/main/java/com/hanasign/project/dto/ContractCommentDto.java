package com.hanasign.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCommentDto {
    private Long id;
    private String comment;
    private Long contractId;
    private Long userId;
    private String userType;
}