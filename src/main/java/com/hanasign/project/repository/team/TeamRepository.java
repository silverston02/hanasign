package com.hanasign.project.repository.team;

import com.hanasign.project.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByIdAndDeletedAtIsNull(Long id);// soft delete를 고려한 조회

}
