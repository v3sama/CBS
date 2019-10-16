package com.cbs.services;

import com.cbs.model.Ticket;
import com.cbs.repository.TicketRepository;
import com.cbs.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

	@Value("${spring.data.rest.default-page-size}")
    private Integer pageSize;

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Page<Ticket> findAll(Integer pageNumber) {
        PageRequest request =
        		PageRequest.of(pageNumber - 1, pageSize);
        return ticketRepository.findAll(request);
    }

    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepository.getOne(id);
    }

    public void update(Ticket ticket) {
        ticketRepository.save(ticket);
    }

}
