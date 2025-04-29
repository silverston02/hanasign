package com.hanasign.project.service.support;

import com.hanasign.project.entity.support.SupportTicket;

import java.util.List;

public interface SupportTicketService {

    // 문의 등록
    SupportTicket createTicket(SupportTicket ticket);

    // 문의 조회 (단건)
    SupportTicket getTicket(Long inquiryID);

    // 모든 문의 조회 (리스트)
    List<SupportTicket> getAllTickets();

    // 문의 수정
    SupportTicket updateTicket(Long inquiryID, SupportTicket ticket);

    // 문의 삭제
    void deleteTicket(Long inquiryID);

    // 답변 등록
    SupportTicket addResponse(Long inquiryID, String response);
}
