package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbs.model.Seat;
@Repository
public interface SeatRepository extends JpaRepository<Seat,Long>{

}
