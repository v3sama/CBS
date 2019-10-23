package com.cbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbs.model.Seat;
import com.cbs.repository.*;

@Service
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

	public Seat addSeat(Seat seat) {
		return repository.saveAndFlush(seat);
	}

	public void addAllSeat(List<Seat> seats) {
		repository.saveAll(seats);
		
	}
}
