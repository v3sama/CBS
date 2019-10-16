package com.cbs.repository;

import com.cbs.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
}
