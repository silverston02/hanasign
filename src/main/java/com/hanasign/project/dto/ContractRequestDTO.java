package com.hanasign.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContractRequestDTO {
    private String title;
    private String content;
    private String documentUrl;
    private List<String> signerIds;
} 