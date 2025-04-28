package com.hanasign.project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
t
    @Id
    private Long id;

    private String username;

    private String email;

    private String password;

    private String role;
}
