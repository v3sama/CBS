package com.cbs.services;

import com.cbs.model.Price;
import com.cbs.repository.PriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

//	public Optional<Price> findByID(long id){
//		return priceRepository.findById(id);
//	}

	public Price findbyId2(long id){
		return priceRepository.getOne(id);
	}

	public List<Price> findPricebyMovie(long id, boolean isHoliday){
		return priceRepository.findPriceByMovieIdAndIsHoliday(id, isHoliday);
	}

	
}
