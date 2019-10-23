package com.cbs.repository;

import com.cbs.dto.ScheduleIndexClientDTO;
import com.cbs.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

    public List<MovieSession> findMovieSessionByCinemaScreen_Id(long id);

//    @Query(value = "SELECT ms.id as session_id, ms.time, m.title as movie_title\n" +
//            "FROM movie_session ms inner join cinema_screen cs \n" +
//            "on ms.cinema_room_id = cs.id\n" +
//            "inner join movie m\n" +
//            "on m.id = ms.movie_id\n" +
//            "where cs.cinema_id = ?1 and m.id = ?2", nativeQuery = true)
//    public List<ScheduleIndexClientDTO> findMovieSessionByCinema(Long cinemaId, Long movieId);
}
