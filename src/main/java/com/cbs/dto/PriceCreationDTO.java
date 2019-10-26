package com.cbs.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cbs.model.Movie;
import com.cbs.model.Price;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceCreationDTO {
	private List<Price> prices = new ArrayList<>();

	// default and parameterized constructor

	public void add(Price price) {
		this.prices.add(price);
	}
	

}
