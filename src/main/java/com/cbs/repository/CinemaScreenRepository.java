package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbs.model.CinemaScreen;

import java.util.List;

@Repository
public interface CinemaScreenRepository extends JpaRepository<CinemaScreen, Long>  {

    public List<CinemaScreen> findCinemaScreenByCinema_Id(long id);
}

