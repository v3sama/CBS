package com.cbs.repository;

import com.cbs.dto.SessionList2DTO;
import com.cbs.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

    public List<MovieSession> findMovieSessionByCinemaScreen_Id(long id);

    @Query(value = "SELECT ms.id as id, ms.time as time FROM movie_session ms left join cinema_screen cs on ms.cinema_room_id = cs.id left join movie m on m.id = ms.movie_id left join cinema c on c.id = cs.cinema_id left join province p on p.id = c.province_id where p.id = :province_id and c.id = :cinema_id and m.id = :movie_id", nativeQuery = true)
    List<SessionList2DTO> findSessionByCinemaAndMovie(@Param("province_id") Integer province_id, @Param("cinema_id") Integer cinema_id, @Param("movie_id") Integer movie_id);

    List<MovieSession> findMovieSessionByMovie_IdAndCinemaScreen_Id(long movieId, long csID);

}
