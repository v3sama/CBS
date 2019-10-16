package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbs.model.CinemaScreen;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaScreen, Long>  {

}
