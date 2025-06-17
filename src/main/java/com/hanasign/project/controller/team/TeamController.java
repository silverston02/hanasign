package com.hanasign.project.controller.team;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.team.RequestCreateTeamDto;
import com.hanasign.project.dto.team.RequestUpdateTeamDto;
import com.hanasign.project.dto.team.ResponseCreateTeamDto;
import com.hanasign.project.entity.Team;
import com.hanasign.project.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController extends BaseController {
    private final TeamService teamService;

    // 팀 부서 단일 정보 조회 (ID로)
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Team team = teamService.findById(id);
        return createResponseEntity(HttpStatus.OK, "부서 정보 조회 성공", team);
    }

    // 이름으로 검색
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@AuthenticationPrincipal UserDetails userDetails,@RequestParam String keyword){
        List<Team> list = teamService.search(userDetails.getUsername(), keyword);
        return createResponseEntity(HttpStatus.OK, "부서 검색 결과", list);
    }

    // 팀 부서 정보 생성
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RequestCreateTeamDto requestCreateTeamDto) {
        Team team = teamService.create(userDetails.getUsername(), requestCreateTeamDto);
        ResponseCreateTeamDto responseCreateTeamDto = ResponseCreateTeamDto.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .build();
        return createResponseEntity(HttpStatus.CREATED, "부서 정보 생성 성공", responseCreateTeamDto);
    }

    // 팀 부서 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RequestUpdateTeamDto requestUpdateTeamDto) {
        Team team = teamService.update(userDetails.getUsername(), requestUpdateTeamDto);
        return createResponseEntity(HttpStatus.OK, "부서 정보 업데이트 성공", team);
    }

    // 팀 부서 정보 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        teamService.delete(id,userDetails.getUsername());
        return createResponseEntity(HttpStatus.OK, "부서 정보 삭제 성공", null);
    }
}
