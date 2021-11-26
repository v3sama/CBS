package com.cbs.services;

import com.cbs.dto.TicketReportDTO;
import com.cbs.dto.TicketReviewDTO;
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

    public void addTicket(Ticket ticket){
        ticketRepository.save(ticket);
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

    public List<Ticket> findBySessionId(long id) { return ticketRepository.findTicketByMovieSession_Id(id); }

    public Ticket findBySeat_id(long id){
        return ticketRepository.findTicketBySeat_Id(id);
    }

    public boolean findTicketbySessionAndSeat(long sid, long smid){
        List<Ticket> ticket = ticketRepository.findTicketBySeat_IdAndMovieSession_Id(sid, smid);
        return ticket.isEmpty();
    }

    public List<Ticket> existTicketByUserAndMovie(long userId){
        return ticketRepository.findTicketByMember_Id(userId);
    }

    public TicketReviewDTO findTicketUAM(int userid, int movieid){
        return ticketRepository.findByUAM(userid, movieid);
    }

    public List<TicketReviewDTO> findListTicketByUAM(int userid, int movieid){
        return ticketRepository.findTicketsByUAM(userid, movieid);
    }
}
