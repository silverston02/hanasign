package com.hanasign.project.support.service;

import com.hanasign.project.support.domain.SupportTicket;
import com.hanasign.project.support.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;

    public SupportTicket create(SupportTicket ticket) {
        ticket.setCreatedDate(LocalDateTime.now());
        ticket.setStatus("등록됨");
        return supportTicketRepository.save(ticket);
    }

    public List<SupportTicket> getAll() {
        return supportTicketRepository.findAll();
    }

    public SupportTicket update(Long id, SupportTicket updateData) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID 없음"));

        ticket.setInquiryTitle(updateData.getInquiryTitle());
        ticket.setDescription(updateData.getDescription());
        ticket.setStatus(updateData.getStatus());
        return supportTicketRepository.save(ticket);
    }

    public void delete(Long id) {
        supportTicketRepository.deleteById(id);
    }

    public SupportTicket addResponse(Long id, String response) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID 없음"));

        ticket.setResponse(response);
        ticket.setResponseDate(LocalDateTime.now());
        ticket.setStatus("답변완료");
        return supportTicketRepository.save(ticket);
    }
}
