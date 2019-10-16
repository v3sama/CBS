package com.cbs.repository;

import com.cbs.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
