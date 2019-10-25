package com.cbs.repository;

import com.cbs.model.Seat;
import com.cbs.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findTicketByMovieSession_Id(long id);

	Ticket findTicketBySeat_Id(long id);

	List<Ticket> findTicketBySeat_IdAndMovieSession_Id(long sid, long msid);
	
	@Query()

	List<Ticket> ticketsQuery(Long provinceId, Long cinemaId, Long screenId, Long movieId, Long memberId,
			LocalDate fromDate, LocalDate toDate);
}
