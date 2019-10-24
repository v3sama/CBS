package com.cbs.controllers;

import com.cbs.model.Cinema;
import com.cbs.model.Movie;
import com.cbs.model.MovieSession;
import com.cbs.model.Province;
import com.cbs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import java.time.LocalDateTime;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

@Controller
public class MovieSessionController {

	private final MovieSessionService movieSessionService;
	private final MovieService movieService;
	private final CinemaService cinemaService;
	private final ScreenService screenService;
	private final TicketService ticketService;
	private final ProvinceService provinceService;

	@Autowired
	public MovieSessionController(MovieSessionService movieSessionService, MovieService movieService,
			CinemaService cinemaService, ScreenService screenService, TicketService ticketService,
			ProvinceService provinceService) {
		this.movieSessionService = movieSessionService;
		this.movieService = movieService;
		this.cinemaService = cinemaService;
		this.screenService = screenService;
		this.ticketService = ticketService;
		this.provinceService = provinceService;
	}

	@RequestMapping(value = "/admin/add/session", method = RequestMethod.GET/* , params = { "province" } */)
	public String addSession(
			/* @RequestParam Long movieId, */ Model model/* , @RequestParam("province") String value */) {
		// Movie movie = movieService.getMovieByID(movieId);
		// model.addAttribute("movie", movie);

		model.addAttribute("provinces", provinceService.getAllProvince());
		// tự động load cinemas thằng tỉnh đầu tiên
		model.addAttribute("cinemas", provinceService.getAllProvince().get(0).getCinemas());
		model.addAttribute("movies", movieService.getAllMovies());
		return "/admin/add/session";
	}

	@RequestMapping(value = "/admin/add/session", method = RequestMethod.POST, params = { "province" })
	public String addSession(Model model, @RequestParam("province") String value) {
		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("provinces", provinceService.getAllProvince());
		Province province = provinceService.getProvinceByID(Long.parseLong(value));
		Set<Cinema> cinemas = provinceService.getProvinceByID(Long.parseLong(value)).getCinemas();
		model.addAttribute("cinemas", cinemas);

		/*
		 * model.addAttribute("movieSessionId", scheduleSession.getId());
		 * model.addAttribute("scheduleSession", scheduleSession);
		 * model.addAttribute("cinemaId", cinemaService.getAllCinema());
		 * model.addAttribute("allScreens",
		 * cinemaService.getCinemaByID(cinemaId).getCinemaScreens());
		 * model.addAttribute("allMovies", movieService.getAllMovies());
		 */
		return "/admin/add/session";
	}

	// mình gọi vào địa chỉ bên dưới, truyền và provinceId
	//đặt debug xem nó có hgọi k
	@RequestMapping(value = "/api/admin/getCinemaByProvince", method = RequestMethod.GET, params = { "provinceId" }
	, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody  Map<Long, String> getCinemaByProvince(@RequestParam("provinceId") Long provinceId) {
		Set<Cinema> cinemas = provinceService.getProvinceByID(provinceId).getCinemas();
		
		Map<Long, String> cinemasMap = new HashMap<Long, String>(); 
		for (Cinema cinema : cinemas) {
			cinemasMap.put(cinema.getId(),cinema.getTitle());
		}
		
		
		
		return cinemasMap;
	}

	@RequestMapping(value = "/admin/edit/session", method = RequestMethod.GET)
	public String editSession(@RequestParam Long sessionId, Model model) {
		MovieSession scheduleSession = movieSessionService.getSessionById(sessionId);
		model.addAttribute("scheduleSession", scheduleSession);
		model.addAttribute("allScreens", scheduleSession.getCinemaScreen().getScreen());
		model.addAttribute("allMovies", movieService.getAllMovies());
		return "/admin/edit/session";
	}

	@RequestMapping(value = "/admin/delete/session", method = RequestMethod.GET)
	public String deleteSession(@RequestParam Long movieSessionId, Model model) {
		movieSessionService.deleteSessionById(movieSessionId);
		return "redirect:/admin/session";
	}

	@RequestMapping(value = "/admin/session", method = RequestMethod.GET, params = { "cinemaId" })
	public String allSessionByCinema(@RequestParam Long cinemaId, Model model) {
		model.addAttribute("cinemaId", cinemaId);
		return "/admin/session";
	}

	@RequestMapping(value = "/admin/session", method = RequestMethod.GET)
	public String allSession(Model model) {
		model.addAttribute("sessions", movieSessionService.getAllSession());
		return "/admin/session";
	}

	@RequestMapping(value = "/session", method = RequestMethod.GET, params = { "cinemaId" })
	public String allSessionByCinemaUser(@RequestParam Long cinemaId, Model model) {

		return "/session";
	}

	@RequestMapping(value = "/session", method = RequestMethod.GET, params = { "screenId" })
	public String allSessionByScreenUser(@RequestParam Long screenId, Model model) {
		return "/session";
	}

	@RequestMapping(value = "/session", method = RequestMethod.GET, params = { "movieId" })
	public String allSessionByMovieUser(@RequestParam Long movieId, Model model) {
		return "/session";
	}

	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public String allSessionUser(Model model) {
		model.addAttribute("sessions", movieSessionService.getAllSession());
		return "/session";
	}

	@RequestMapping(value = "/details/session", method = RequestMethod.GET)
	public String getSession(@RequestParam Long sessionId, Model model) {
		model.addAttribute("scheduleSession", movieSessionService.getSessionById(sessionId));
		return "/details/session";
	}

}
