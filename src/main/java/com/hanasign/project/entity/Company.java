package com.hanasign.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "phon_number", length = 50)
    private String phonNumber;

    @Size(max = 10)
    @NotNull
    @Column(name = "business_number", nullable = false, length = 10)
    private String businessNumber;

    @Size(max = 50)
    @Column(name = "fax_number", length = 50)
    private String faxNumber;

    @Lob
    @Column(name = "address")
    private String address;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Size(max=100)
    @Column(name="name", length = 100)
    private String name;

}