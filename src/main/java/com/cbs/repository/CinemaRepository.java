package com.cbs.repository;

import com.cbs.model.Cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

	List<Cinema> findCinemaByProvince_Id(long id);

	@Query(value = "SELECT count(*) FROM movie_session WHERE movie_id = ?1 AND DATE_FORMAT(time,'%Y-%m-%d') LIKE ?2", nativeQuery = true)
	int hasMovieSession(Long movieId, String date);
}
