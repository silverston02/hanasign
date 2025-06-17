package com.hanasign.project.dto.team;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreateTeamDto {
    private Long id;
    private String teamName;
}
