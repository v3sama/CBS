package com.cbs.repository;

import com.cbs.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
