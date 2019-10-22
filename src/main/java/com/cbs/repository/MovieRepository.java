package com.cbs.repository;

import com.cbs.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContaining(String title, Pageable pageable);

    List<Movie> findByStatus(int status);
}
