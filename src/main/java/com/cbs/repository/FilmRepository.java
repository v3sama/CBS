package com.cbs.repository;

import com.cbs.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContaining(String title, Pageable pageable);

}
