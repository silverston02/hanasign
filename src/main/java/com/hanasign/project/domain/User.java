package com.hanasign.project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user") // 테이블 명시
public class User {

    @Id
    @Column(name = "id") // 실제 DB 컬럼명
    private Long userID;

    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_permission")
    private String userPermission;
}
