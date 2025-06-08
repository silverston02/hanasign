package com.hanasign.project.service.team;

import com.hanasign.project.dto.team.RequestCreateTeamDto;
import com.hanasign.project.dto.team.RequestUpdateTeamDto;
import com.hanasign.project.entity.Team;
import com.hanasign.project.entity.User;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.UserRepository;
import com.hanasign.project.repository.team.TeamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    // 팀 생성
    public Team create(String email,RequestCreateTeamDto requestCreateTeamDto){
        User user = userRepository.findByEmail(email).get();


        Team team = Team.builder()
                .teamName(requestCreateTeamDto.getTeamName())
                .companyId(user.getCompanyId())
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
//        if ( team.isEmpty()){
//            throw Exceptions.TEAM_NOT_FOUND;
//        }
        Team existhingTeam = team.get();
        existhingTeam.setTeamName(requestUpdateTeamDto.getTeamName());
        return teamRepository.save(existhingTeam);
    }
    // 이름으로 조회
    public List<Team> search(String email, String keyword) {
        User  user = userRepository.findByEmail(email).get();
        return teamRepository
                .findByTeamNameContainingIgnoreCaseAndCompanyIdAndDeletedAtIsNull(keyword, user.getCompanyId());
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
