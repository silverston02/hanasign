package com.hanasign.project.service.support;

import com.hanasign.project.entity.support.SupportTicket;
import com.hanasign.project.repository.support.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;

    @Override
    public SupportTicket createTicket(SupportTicket ticket) {
        return supportTicketRepository.save(ticket);
    }

    @Override
    public SupportTicket getTicket(Long inquiryID) {
        return supportTicketRepository.findById(inquiryID)
                .orElseThrow(() -> new RuntimeException("해당 문의가 존재하지 않습니다."));
    }

    @Override
    public List<SupportTicket> getAllTickets() {
        return supportTicketRepository.findAll();
    }

    @Override
    public SupportTicket updateTicket(Long inquiryID, SupportTicket ticket) {
        SupportTicket existingTicket = getTicket(inquiryID);
        existingTicket.setInquiryTitle(ticket.getInquiryTitle());
        existingTicket.setDescription(ticket.getDescription());
        existingTicket.setStatus(ticket.getStatus());
        return supportTicketRepository.save(existingTicket);
    }

    @Override
    public void deleteTicket(Long inquiryID) {
        supportTicketRepository.deleteById(inquiryID);
    }

    @Override
    public SupportTicket addResponse(Long inquiryID, String response) {
        SupportTicket ticket = getTicket(inquiryID);
        ticket.setResponse(response);
        ticket.setResponseDate(LocalDateTime.now());
        ticket.setStatus("답변완료");
        return supportTicketRepository.save(ticket);
    }
}
