package com.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cbs.model.Movie;
import com.cbs.model.Payment;
import com.cbs.model.Price;
import com.cbs.model.Province;
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
	
}
