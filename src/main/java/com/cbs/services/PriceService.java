package com.cbs.services;

import com.cbs.model.Price;
import com.cbs.repository.PriceRepository;
import com.cbs.services.PriceService;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.validation.Valid;

@Service
public class PriceService {
	private PriceRepository priceRepository;
	
	public void addPrices(@Valid List<Price> prices) {
		
		priceRepository.saveAll(prices);
	}
	
	public void addPrice(Price price) {
		
		priceRepository.save(price);
	}

	
}
