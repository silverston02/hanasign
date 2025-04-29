package com.hanasign.project.controller.support;

import com.hanasign.project.entity.support.SupportTicket;
import com.hanasign.project.service.support.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    // 문의 등록
    @PostMapping
    public ResponseEntity<SupportTicket> createTicket(@RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(supportTicketService.createTicket(ticket));
    }

    // 단일 문의 조회
    @GetMapping("/{inquiryID}")
    public ResponseEntity<SupportTicket> getTicket(@PathVariable Long inquiryID) {
        return ResponseEntity.ok(supportTicketService.getTicket(inquiryID));
    }

    // 모든 문의 조회
    @GetMapping
    public ResponseEntity<List<SupportTicket>> getAllTickets() {
        return ResponseEntity.ok(supportTicketService.getAllTickets());
    }

    // 문의 수정
    @PutMapping("/{inquiryID}")
    public ResponseEntity<SupportTicket> updateTicket(@PathVariable Long inquiryID, @RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(supportTicketService.updateTicket(inquiryID, ticket));
    }

    // 문의 삭제
    @DeleteMapping("/{inquiryID}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long inquiryID) {
        supportTicketService.deleteTicket(inquiryID);
        return ResponseEntity.ok("삭제 완료");
    }

    // 문의에 답변 등록
    @PostMapping("/{inquiryID}/response")
    public ResponseEntity<SupportTicket> addResponse(@PathVariable Long inquiryID, @RequestBody String response) {
        return ResponseEntity.ok(supportTicketService.addResponse(inquiryID, response));
    }
}