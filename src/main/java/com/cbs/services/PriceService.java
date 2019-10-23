package com.cbs.services;

import com.cbs.model.Movie;
import com.cbs.model.Price;
import com.cbs.repository.MovieRepository;
import com.cbs.repository.PriceRepository;
import com.cbs.services.PriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Service
public class PriceService {
	private PriceRepository priceRepository;

	@Autowired
	public PriceService(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	public void addPrices(@Valid List<Price> prices) {

		priceRepository.saveAll(prices);
	}

	public void addPrice(Price price) {

		priceRepository.save(price);
	}

	public List<Price> getPricesByMovie(Long movieId) {
		return priceRepository.findAllPriceByMovie(movieId);
	
	}

}
