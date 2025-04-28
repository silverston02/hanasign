package com.hanasign.project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;

    // 기본 생성자 (Lombok의 @NoArgsConstructor로 대체)
    
    // 모든 필드를 초기화하는 생성자 (Lombok의 @AllArgsConstructor로 대체)
    
    // getter와 setter는 Lombok의 @Data로 자동 생성
}