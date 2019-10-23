package com.cbs.repository;

import com.cbs.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContaining(String title, Pageable pageable);

    List<Movie> findByStatusTrue();
    List<Movie> findByStatusFalse();

//    List<Movie> findByDate_releaseBefore(LocalDate dDateate);

//    List<Movie> findByDate_releaseAfter(LocalDate date);

    @Query(value = "SELECT * FROM movie WHERE date_release > ?1", nativeQuery = true)
    List<Movie> findMovieSapChieu (String date);
}
