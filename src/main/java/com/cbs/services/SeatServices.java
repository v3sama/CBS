package com.cbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cbs.model.Seat;
import com.cbs.repository.*;

public class SeatServices {
	@Autowired
	private SeatRepository repository;

	public List<Seat> getAllSeat() {
		return repository.findAll();
	}

	public Seat getSeatById(Long id) {
		return repository.getOne(id);
	}

	public void deleteBySeatId(Long id) {
		if (getSeatById(id) != null) {
			repository.deleteById(id);
		}
	}

	public Seat addHall(Seat seat) {
		return repository.saveAndFlush(seat);
	}
}
