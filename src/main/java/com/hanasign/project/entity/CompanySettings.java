package com.hanasign.project.entity;

import jakarta.persistence.*; // JPA 엔티티 어노테이션들
import lombok.*;             // Lombok 어노테이션들
import com.hanasign.project.domain.User; // User 엔티티가 위치한 경로 (이것도 실제 경로 맞춰줘)

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "company_settings")
public class CompanySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String address;

    private Long tellNumber;

    private Long faxNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private User admin;

    private String field;
    private String ceo;
    private Long eid;


    private String websiteUrl;
}
