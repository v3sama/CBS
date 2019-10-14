package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbs.model.CinemaScreen;


public interface CinemaRoomRepository extends JpaRepository<CinemaScreen, Long>  {

}
