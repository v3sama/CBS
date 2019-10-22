package com.cbs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cbs.dto.PriceCreationDTO;
import com.cbs.model.FormatType;
import com.cbs.model.Movie;
import com.cbs.model.Price;
import com.cbs.services.MovieService;
import com.cbs.services.PriceService;

@Controller
public class PriceController {
	private PriceService priceService;
	private MovieService movieService;

	@Autowired
	public PriceController(PriceService priceService,MovieService movieService) {
		this.priceService = priceService;
		this.movieService = movieService;
	}

	@RequestMapping(value = "/admin/set/price", method = RequestMethod.GET, params = { "movieId" })
	public String addPrice(@RequestParam Long movieId, Model model) {
		Movie movie = movieService.getMovieByID(movieId);
		model.addAttribute("movie", movie);
		if(movie.getPrices().isEmpty()) {
			FormatType formatType = movie.getFormatType();
			PriceCreationDTO pricesForm = new PriceCreationDTO();
			//WDVIP - ve ngay thuong ghe VIP
			Price price = new Price();
			price.setTitle("WDVIP"+formatType.getName());
			price.setIsHoliday(false);
			price.setIsVIP(true);
			price.setFormatType(formatType);
			price.setDescription("Ve VIP ngay thuong");
			pricesForm.add(price);
			//WDVIP - ve ngay thuong ghe thuong
			price = new Price();
			price.setTitle("WDNONVIP"+formatType.getName());
			price.setIsHoliday(false);
			price.setIsVIP(false);
			price.setFormatType(formatType);
			price.setDescription("Ve thuong ngay thuong");
			pricesForm.add(price);
			//WEVIP - ve ngay le, ghe vip
			price = new Price();
			price.setTitle("WEVIP"+formatType.getName());
			price.setIsHoliday(true);
			price.setIsVIP(true);
			price.setFormatType(formatType);
			price.setDescription("Ve VIP ngay le + cuoi tuan");
			pricesForm.add(price);
			//WEVIP - ve ngay le, ghe thuong
			price = new Price();
			price.setTitle("WENONVIP"+formatType.getName());
			price.setIsHoliday(true);
			price.setIsVIP(false);
			price.setFormatType(formatType);
			price.setDescription("Ve thuong ngay le + cuoi tuan");
			pricesForm.add(price);
			
			model.addAttribute("forms", pricesForm);
		} else
			model.addAttribute("forms", movie.getPrices());
		return "/admin/add/price";
	}

	@RequestMapping(value = "/admin/set/price", method = RequestMethod.POST)
	public String addPrice(@ModelAttribute PriceCreationDTO form, Model model) {
		List<Price> list = form.getPrices();
		//priceService.addPrices(form.getPrices());
		priceService.addPrice(list.get(1));
		return "redirect:/admin/set/price";
	}
}
