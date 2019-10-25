package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cbs.model.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long>{

    @Query(value = "select * from seat s where s.isVIP = true", nativeQuery = true)
    List<Seat> findByVIPTrue1();

    Seat findSeatById(long id);
}
