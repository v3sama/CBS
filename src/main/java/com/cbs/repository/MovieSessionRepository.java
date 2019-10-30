package com.cbs.repository;

import com.cbs.dto.SessionDateDTO;
import com.cbs.dto.SessionList2DTO;
import com.cbs.dto.test1;
import com.cbs.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

	public List<MovieSession> findMovieSessionByCinemaScreen_Id(long id);

	@Query(value = "SELECT ms.id as id, ms.time as time FROM movie_session ms left join cinema_screen cs on ms.cinema_room_id = cs.id left join movie m on m.id = ms.movie_id left join cinema c on c.id = cs.cinema_id left join province p on p.id = c.province_id where p.id = :province_id and c.id = :cinema_id and m.id = :movie_id and DATE(date_format(time, '%Y-%m-%d')) = curdate()", nativeQuery = true)
	List<SessionList2DTO> findSessionByCinemaAndMovie(@Param("province_id") Integer province_id,
			@Param("cinema_id") Integer cinema_id, @Param("movie_id") Integer movie_id);

	List<MovieSession> findMovieSessionByMovie_IdAndCinemaScreen_Id(long movieId, long csID);

	/*
	 * @Query(value = "SELECT ms.id as id, TIME(ms.time) as time " +
	 * "FROM movie_session ms " +
	 * "left join cinema_screen cs on ms.cinema_room_id = cs.id " +
	 * "left join movie m on m.id = ms.movie_id " +
	 * "left join cinema c on c.id = cs.cinema_id " +
	 * "left join province p on p.id = c.province_id " +
	 * "where p.id = :province_id and c.id = :cinema_id and m.id = :movie_id " +
	 * "and DATE_FORMAT(ms.time,'%Y-%m-%d') LIKE :time", nativeQuery = true)
	 */
	@Query("select ms.id as id,  DATE_FORMAT(ms.time, '%H:%i') as time, s.title as screenName  "
			+ "from MovieSession ms "
			+ " join CinemaScreen cs on cs.id =  ms.cinemaScreen "
			+ " join Screen s on cs.screen = s.id "
			+ " join Cinema c on cs.cinema = c.id "
			+ " join Movie m on m.id = ms.movie "
			+ "where c.id = :cinema_id and m.id = :movie_id "
			+ "and  DATE_FORMAT(ms.time,'%Y-%m-%d')  LIKE :time "
			+ "order by s.id")
	public List<test1> findSessionByCinemaAndMovieAndDate(
			@Param("cinema_id") Long cinema_id, @Param("movie_id") Long movie_id, @Param("time") String time);

	MovieSession findMovieSessionById(long id);

	@Query(value = "SELECT distinct date_format(ms.time, '%d%m') as id, date_format(ms.time, '%d-%m') as time FROM movie_session ms left join cinema_screen cs on ms.cinema_room_id = cs.id left join movie m on m.id = ms.movie_id left join cinema c on c.id = cs.cinema_id left join province p on p.id = c.province_id where p.id = :province_id and c.id = :cinema_id and m.id = :movie_id and DATE(ms.time) >= curdate()", nativeQuery = true)
	List<SessionList2DTO> findDateOfSessionByCinemaAndMovieAndDate(@Param("province_id") Integer province_id,
			@Param("cinema_id") Integer cinema_id, @Param("movie_id") Integer movie_id);

	@Query(value = "SELECT ms.id as id, date_format(ms.time, '%H:%i') as time FROM movie_session ms left join cinema_screen cs on ms.cinema_room_id = cs.id left join movie m on m.id = ms.movie_id left join cinema c on c.id = cs.cinema_id left join province p on p.id = c.province_id where p.id = :province_id and c.id = :cinema_id and m.id = :movie_id and date_format(ms.time, '%d%m') = :dateEqual", nativeQuery = true)
	List<SessionList2DTO> findTimeOfSessionByCinemaAndMovieAndDate(@Param("province_id") Integer province_id,
			@Param("cinema_id") Integer cinema_id, @Param("movie_id") Integer movie_id,
			@Param("dateEqual") String date);

}
