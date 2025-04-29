package com.hanasign.project.repository.support;

import com.hanasign.project.entity.support.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    // 기본 CRUD 제공 (save, findById, findAll, deleteById 등)

    // 필요하면 추가 커스텀 메소드도 여기
}