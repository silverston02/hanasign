package com.hanasign.project.support.domain;

import com.hanasign.project.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "support_ticket")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inquiryTitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status; // "등록됨", "답변완료" 등

    @Column(columnDefinition = "TEXT")
    private String response;

    private LocalDateTime responseDate;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
