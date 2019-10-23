package com.cbs.repository;

import com.cbs.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findCinemaByProvince_Id(long id);
}
