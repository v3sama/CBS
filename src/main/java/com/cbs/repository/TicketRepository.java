package com.cbs.repository;

import com.cbs.dto.TicketReportDTO;
import com.cbs.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findTicketByMovieSession_Id(long id);

	Ticket findTicketBySeat_Id(long id);

	List<Ticket> findTicketBySeat_IdAndMovieSession_Id(long sid, long msid);
	
	@Query("SELECT o.id as orderId, t.member as memberId, o.orderTime as orderTime, t.amount as amount "
			+ "FROM Ticket t "
			+ " JOIN SOrder o on t.order = o.id "
			+ " JOIN MovieSession ms on t.movieSession = ms.id "
			+ " JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " JOIN Cinema c on c.id = cs.cinema "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?2 "
			+ "AND o.orderTime <= ?3 AND c.id = ?1")
	List<TicketReportDTO> findTicketByCinema(Long cinemaId, LocalDateTime fromDate, LocalDateTime toDate);
	
	
	  @Query( "SELECT o.id as orderId, t.member as memberId, o.orderTime, t.amount " +
		  "FROM Ticket t " + 
		  " JOIN SOrder o on t.order = o.id " +
		  " JOIN MovieSession ms on t.movieSession = ms.id " +
		  " JOIN CinemaScreen cs on ms.cinemaScreen = cs.id " +
		  " JOIN Cinema c on c.id = cs.cinema " +
		  " JOIN Province p on p.id = c.province " +
		  "WHERE o.status = 'Completed' AND o.orderTime >= :fromDate " +
		  "AND o.orderTime <= :toDate AND p.id = :provinceId  ")
	  List<TicketReportDTO>	  findTicketByProvince(Long provinceId, LocalDate fromDate, LocalDate toDate);
	 
}
