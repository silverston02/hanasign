package com.hanasign.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 ID

    @Column(name = "name", length = 50)
    private String name; // 회사명

    @Column(name = "email", length = 50)
    private String email; // 로그인 아이디

    @Column(name = "pw", length = 100)
    private String pw; // 비밀번호

    @Column(name = "phon_number", length = 50)
    private String phonNumber; // 회사 전화번호

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성일

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정일

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 삭제일 (null이면 미삭제)

    @NotNull
    @Column(name = "company_id", nullable = false)
    private Long companyId; // 회사 ID

    @NotNull
    @Column(name = "team_id", nullable = false)
    private Long teamId;

}
