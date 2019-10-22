package com.cbs.dto;

import java.util.ArrayList;
import java.util.List;

import com.cbs.model.Price;

public class PriceCreationDTO {
	private List<Price> prices = new ArrayList<>();

	// default and parameterized constructor

	public void add(Price price) {
		this.prices.add(price);
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

}
