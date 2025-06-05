package com.hanasign.project.service.team;

import com.hanasign.project.dto.team.RequestCreateTeamDto;
import com.hanasign.project.dto.team.RequestUpdateTeamDto;
import com.hanasign.project.entity.Team;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.team.TeamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    // 팀 생성
    public Team create(RequestCreateTeamDto requestCreateTeamDto){
        Team team = Team.builder()
                .teamName(requestCreateTeamDto.getTeamName())
                .build();
        return teamRepository.save(team);

    }

    // 팀 정보 업데이트
    public Team findById(Long id) {
        Optional<Team> team = teamRepository.findByIdAndDeletedAtIsNull(id);
        if (team.isEmpty()) {
            throw Exceptions.TEAM_NOT_FOUND;
        }
        return team.get();
    }

    // 팀 정보 조회
    public Team update(Long id, RequestUpdateTeamDto requestUpdateTeamDto){
        Optional<Team> team = teamRepository.findByIdAndDeletedAtIsNull(id);
        if ( team.isEmpty()){
            throw Exceptions.TEAM_NOT_FOUND;
        }
        Team existhingTeam = team.get();
        existhingTeam.setTeamName(requestUpdateTeamDto.getTeamName());
        return teamRepository.save(existhingTeam);
    }

    public void delete(Long id){
        Optional<Team> team = teamRepository.findByIdAndDeletedAtIsNull(id);
        if (team.isEmpty()) {
            throw Exceptions.TEAM_NOT_FOUND;
        }
        // soft delete
        Team existingTeam = team.get();
        existingTeam.setDeletedAt(java.time.Instant.now());
        teamRepository.save(existingTeam);
    }
}
