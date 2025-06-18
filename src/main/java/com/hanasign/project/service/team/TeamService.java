package com.hanasign.project.service.team;
import com.hanasign.project.dto.team.RequestCreateTeamDto;
import com.hanasign.project.dto.team.RequestUpdateTeamDto;
import com.hanasign.project.entity.Team;

import java.util.List;

public interface TeamService {
    Team create(String email,RequestCreateTeamDto requestCreateTeamDto);
    Team findById(Long id);
    Team update(String email, Long id, RequestUpdateTeamDto requestUpdateTeamDto);
    List<Team> search(String email, String keyword);
    void delete(Long id,String email);
}
