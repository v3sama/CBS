package com.cbs.repository;

import com.cbs.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleSessionRepository extends JpaRepository<MovieSession, Long> {
}
