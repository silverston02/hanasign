package com.hanasign.project.dto;

import lombok.Data;

@Data
public class ContractCommentDto {
    private String comment;
    private Long contractId;
    private Long userId;
    private String userType;
}