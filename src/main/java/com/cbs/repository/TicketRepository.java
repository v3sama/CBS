package com.cbs.repository;

import com.cbs.model.Seat;
import com.cbs.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findTicketByMovieSession_Id(long id);

    Ticket findTicketBySeat_Id(long id);

}
