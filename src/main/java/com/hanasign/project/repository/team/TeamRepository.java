package com.hanasign.project.repository.team;

import com.hanasign.project.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByIdAndDeletedAtIsNull(Long id);// soft delete를 고려한 조회
    List<Team> findByTeamNameContainingIgnoreCaseAndDeletedAtIsNull(String keyword); //이름으로 조회
    List<Team> findByTeamNameContainingIgnoreCaseAndCompanyIdAndDeletedAtIsNull(String keyword, Long companyId);


}
