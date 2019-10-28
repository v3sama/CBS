package com.cbs.repository;

import com.cbs.dto.TicketReportDTO;
import com.cbs.dto.TicketReviewDTO;
import com.cbs.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findTicketByMovieSession_Id(long id);

	Ticket findTicketBySeat_Id(long id);

	List<Ticket> findTicketBySeat_IdAndMovieSession_Id(long sid, long msid);

	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "AND c.id = ?3 ")
	List<TicketReportDTO> findTicketByCinema( LocalDateTime fromDate, LocalDateTime toDate,Long cinemaId);

	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "AND p.id = ?3 ")
	List<TicketReportDTO> findTicketByProvince( LocalDateTime fromDate, LocalDateTime toDate, Long provinceId);

	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "AND m.id = ?3 ")
	List<TicketReportDTO> findTicketByMovie( LocalDateTime fromDate, LocalDateTime toDate, Long movieId);

	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "AND p.id = ?3 AND m.id = ?4")
	List<TicketReportDTO> findTicketByMovieProvince( LocalDateTime fromDate,LocalDateTime toDate, Long provinceId, Long movieId);


	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "AND c.id = ?3 AND m.id = ?4")
	List<TicketReportDTO> findTicketByMovieCinema( LocalDateTime fromDate,	LocalDateTime toDate, Long cinemaId, Long movieId);

	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "AND u.id = ?3")
	List<TicketReportDTO> findTicketByCustomer( LocalDateTime fromDate, LocalDateTime toDate,Long customerId);

	@Query("SELECT u.id as memberId, o.id as orderId, o.orderTime as orderTime, p.name as province, c.title as cinema, "
			+ "m.title as movie,f.name as format, ms.time as sessionTime, s.isVIP as seatType, t.amount as price  "
			+ "FROM Ticket t " + " LEFT OUTER JOIN Seat s on s.id = t.seat "
			+ " LEFT OUTER JOIN SOrder o on t.order = o.id " + "LEFT OUTER JOIN User u on o.member = u.id "
			+ " LEFT OUTER JOIN MovieSession ms on t.movieSession = ms.id "
			+ " LEFT OUTER JOIN Movie m on ms.movie = m.id " + " LEFT OUTER JOIN FormatType f on f.id = m.formatType "
			+ " LEFT OUTER JOIN CinemaScreen cs on ms.cinemaScreen = cs.id "
			+ " LEFT OUTER JOIN Cinema c on c.id = cs.cinema " + "LEFT OUTER JOIN Province p on c.province = p.id "
			+ "WHERE o.status = 'Completed' AND o.orderTime >= ?1 " + "AND o.orderTime <= ?2 "
					+ "order by o.orderTime")
	List<TicketReportDTO> findTicket(LocalDateTime fromDate, LocalDateTime toDate);

	List<Ticket> findTicketByMember_Id(long id);

	@Query(value = "select (count(*) > 0) as id from ticket t " + "left join  movie_session "
			+ "on t.movie_session_id = movie_session.id " + "left join sorder " + "on t.order_id = sorder.id "
			+ "where t.member_id = :member and movie_session.movie_id = :movie and sorder.status= 'Completed'", nativeQuery = true)
	TicketReviewDTO findByUAM(@Param("member") Integer member, @Param("movie") Integer movie);

	@Query(value = "select sorder.id as id from ticket t " + "left join  movie_session "
			+ "on t.movie_session_id = movie_session.id " + "left join sorder " + "on t.order_id = sorder.id "
			+ "where t.member_id = :member and movie_session.movie_id = :movie and sorder.status= 'Completed'", nativeQuery = true)
	List<TicketReviewDTO> findTicketsByUAM(@Param("member") Integer member, @Param("movie") Integer movie);
}
