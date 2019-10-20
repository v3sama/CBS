package com.cbs.services;

import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Screen;
import com.cbs.repository.CinemaRepository;
import com.cbs.repository.CinemaScreenRepository;
import com.cbs.repository.ScreenRepository;
import com.cbs.services.CinemaService;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CinemaService {

	@Value("${spring.data.rest.default-page-size}")
	private Integer pageSize;


	private final CinemaRepository cinemaRepository;

	@Autowired
	public CinemaService(CinemaRepository cinemaRepository, ScreenRepository screenRepository , 
			CinemaScreenRepository cinemaScreenRepository){
		this.cinemaRepository = cinemaRepository;


	}

	public Page<Cinema> getAllCinemaPage(Integer pageNumber) {
		PageRequest request = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.ASC, "title");
		return cinemaRepository.findAll(request);
	}

	public List<Cinema> getAllCinema() {
		return cinemaRepository.findAll();
	}

	private boolean existCinema(Cinema cinema) {
		return cinemaRepository.existsById(cinema.getId());
	}

	public void addCinema(Cinema cinema) {
		cinemaRepository.saveAndFlush(cinema);
	}

	public void deleteCinemaByID(Long id) {
		if (existCinema(getCinemaByID(id))) {
			cinemaRepository.deleteById(id);
		}
	}

	public Cinema getCinemaByID(Long id) {
		return cinemaRepository.getOne(id);
	}

	
}
