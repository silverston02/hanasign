package com.hanasign.project.support.controller;

import com.hanasign.project.support.domain.SupportTicket;
import com.hanasign.project.support.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    // 1. 등록
    @PostMapping
    public ResponseEntity<SupportTicket> create(@RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(supportTicketService.create(ticket));
    }

    // 2. 전체 조회
    @GetMapping
    public ResponseEntity<List<SupportTicket>> getAll() {
        return ResponseEntity.ok(supportTicketService.getAll());
    }

    // 3. 수정
    @PutMapping("/{id}")
    public ResponseEntity<SupportTicket> update(@PathVariable Long id, @RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(supportTicketService.update(id, ticket));
    }

    // 4. 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supportTicketService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 5. 답변 등록
    @PutMapping("/{id}/response")
    public ResponseEntity<SupportTicket> respond(@PathVariable Long id, @RequestParam String response) {
        return ResponseEntity.ok(supportTicketService.addResponse(id, response));
    }
}
