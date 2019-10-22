package com.cbs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		if(movie.getPrices().isEmpty())
			model.addAttribute("prices", new ArrayList<Price>());
		else
			model.addAttribute("prices", movie.getPrices());
		return "/admin/add/price";
	}

	@RequestMapping(value = "/admin/set/price", method = RequestMethod.POST)
	public String addPrice(@Valid List<Price> prices, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "error";
		}
		priceService.addPrices(prices);
		return "redirect:/admin/set/price";
	}
}
