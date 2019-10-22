package com.cbs.services;

import com.cbs.model.Cinema;
import com.cbs.model.Price;
import com.cbs.repository.CinemaRepository;
import com.cbs.repository.PriceRepository;
import com.cbs.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.validation.Valid;

@Service
public class PriceService {
	private PriceRepository priceRepository;
	
	public void addPrices(@Valid List<Price> prices) {
		
		priceRepository.saveAll(prices);
	}

	
}
