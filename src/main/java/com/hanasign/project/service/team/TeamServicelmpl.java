package com.hanasign.project.service.team;

import com.hanasign.project.dto.team.RequestCreateTeamDto;
import com.hanasign.project.dto.team.RequestUpdateTeamDto;
import com.hanasign.project.entity.Team;
import com.hanasign.project.entity.User;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.UserRepository;
import com.hanasign.project.repository.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServicelmpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    // 팀 생성
    public Team create(String email, RequestCreateTeamDto requestCreateTeamDto){
        User user = userRepository.findByEmail(email).get();
        Team team = Team.builder() // Team 엔티티 빌더를 사용하여 팀 객체 생성
                .teamName(requestCreateTeamDto.getTeamName())
                .companyId(user.getCompanyId())
                .build();
        return teamRepository.save(team);

    }

    // 팀 정보 조회
    public Team findById(Long id) {
        Optional<Team> team = teamRepository.findByIdAndDeletedAtIsNull(id);
        if (team.isEmpty()) {
            throw Exceptions.TEAM_NOT_FOUND;
        }
        return team.get();
    }

    // 팀 정보 업데이트
    public Team update(String email, Long id, RequestUpdateTeamDto requestUpdateTeamDto){
        Optional<Team> team = teamRepository.findByIdAndDeletedAtIsNull(id); // ✅ requestUpdateTeamDto.getId() → id

        if (team.isEmpty()) {
            throw Exceptions.TEAM_NOT_FOUND;
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);

        // 유저의 회사 ID와 팀의 회사 ID 비교
        if (!team.get().getCompanyId().equals(user.getCompanyId())) {
            throw Exceptions.TEAM_NOT_FOUND;
        }

        Team existingTeam = team.get();
        existingTeam.setTeamName(requestUpdateTeamDto.getTeamName());
        return teamRepository.save(existingTeam);
    }

    // 이름으로 조회
    public List<Team> search(String email, String keyword) {
        User  user = userRepository.findByEmail(email).get();
        return teamRepository
                .findByTeamNameContainingIgnoreCaseAndCompanyIdAndDeletedAtIsNull(keyword, user.getCompanyId());
    }

    public void delete(Long id,String email) {
        User user = userRepository.findByEmail(email).get();
        Optional<Team> team = teamRepository.findByIdAndDeletedAtIsNull(id);
        // 유저 ID = 팀 ID 비교
        if (team.isEmpty() || !team.get().getCompanyId().equals(user.getCompanyId())) {
            throw Exceptions.TEAM_NOT_FOUND;
        }
        // soft delete
        Team existingTeam = team.get();
        existingTeam.setDeletedAt(java.time.Instant.now());
        teamRepository.save(existingTeam);
    }
}
