package com.cbs.controllers;

import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Movie;
import com.cbs.model.MovieSession;
import com.cbs.model.Screen;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

@Controller
public class MovieSessionController {

	public final MovieSessionService movieSessionService;
	private final MovieService movieService;
	private final CinemaService cinemaService;
	private final ScreenService screenService;
	private final TicketService ticketService;
	private final ProvinceService provinceService;
	public static List<MovieSession> movieSessions = new ArrayList<MovieSession>();

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
		model.addAttribute("movies", movieService.getAllActiveMovies());
		return "/admin/add/session";
	}

	@RequestMapping(value = "/admin/add/session", method = RequestMethod.POST, params = { "province", "cinemas",
			"movies", "date" })
	public String addSession(Model model, @RequestParam("province") String value,
			@RequestParam("cinemas") Long cinemaValue, @RequestParam("movies") String movieValue,
			@RequestParam("date") String date) {

		List<Movie> movies = movieService.getAllActiveMovies();

		Movie movie = movieService.getMovieByID(Long.parseLong(movieValue));
		Set<CinemaScreen> cinemaScreens = cinemaService.getCinemaByID(cinemaValue).getCinemaScreens();

		model.addAttribute("provinceDetails", value);
		model.addAttribute("cinemaDetails", cinemaValue);
		model.addAttribute("movieDetails", movieValue);
		model.addAttribute("dateDetails", date);
		model.addAttribute("movieSessions", movieSessions);
		model.addAttribute("cinemaScreens", cinemaScreens);

		if (cinemaService.hasSession(Long.parseLong(movieValue), date) > 0)
			return "redirect:/admin/details/session-details";

		int noOfMovies = movies.size();
		// int noOfRooms = cinemaScreens.size();

		int last = 23 * 60;
		int first = 8 * 60;
		int breakTime = 15;
		int delay = 30;

		Schedule.LAST = last;
		int f = 0, i = 0;
		// for (int i = 0; i < noOfRooms; i++) {
		for (CinemaScreen cinemaScreen : cinemaScreens) {
			List<Movie> tmp = new ArrayList<>();
			for (int j = f; j < noOfMovies + f; j++) {
				tmp.add(movies.get(j % noOfMovies));
			}

			if (i > noOfMovies - 1) {
				first += delay;
			}

			System.out.println(tmp);

			newSchedule(first, breakTime, tmp, cinemaScreen, date);
			i++;
			f++;

			first = 8 * 60;
		}
		movieSessionService.addAll(movieSessions);

		return "redirect:/#";

	}

	static class Schedule {
		public List<Integer> values = new ArrayList<>();
		public List<Long> movieIds = new ArrayList<>();
		public List<Long> csIds = new ArrayList<>();
		// public static List<MovieSession> movieSessions;
//		private static MovieSessionService movieSessionService;
		private int time;
		public static int LAST;

		public Schedule(int first) {
			this.time = first;
		}

		public boolean canApply() {
			return time < LAST;
		}

		public void apply(Movie movie, int breakTime, CinemaScreen cinemaScreen, String dateString) {
			values.add(time);
			movieIds.add(movie.getId());
			csIds.add(cinemaScreen.getId());

			String localDateStting = dateString + " " + toTime(time);
			System.out.println(time);
			System.out.println(localDateStting);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
			LocalDateTime dateTime = LocalDateTime.parse(localDateStting, formatter);
			MovieSession ms = new MovieSession(movie, cinemaScreen, dateTime);
			movieSessions.add(ms);
			time += movie.getDuration() + breakTime;

		}

		private String toTime(int value) {
			if (value % 60 < 10) {
				return value / 60 + ":0" + value % 60;
			}
			return value / 60 + ":" + value % 60;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < values.size(); i++) {
				// sb.append(toTime(values.get(i))).append("\t").append(titles.get(i)).append("\n");
			}
			return sb.toString();
		}
	}

	static void newSchedule(int first, int breakTime, List<Movie> movies, CinemaScreen cinemaScreen, String date) {
		Queue<Movie> queue = new ConcurrentLinkedQueue<>(movies);
		Schedule newSchedule = new Schedule(first);
		while (newSchedule.canApply()) {
			Movie tmp = queue.poll();
			newSchedule.apply(tmp, breakTime, cinemaScreen, date);
			queue.add(tmp);

		}

		System.out.println(newSchedule);
	}

	@RequestMapping(value = "/api/admin/getCinemaByProvince", method = RequestMethod.GET, params = {
			"provinceId" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<Long, String> getCinemaByProvince(@RequestParam("provinceId") Long provinceId) {
		Set<Cinema> cinemas = provinceService.getProvinceByID(provinceId).getCinemas();

		Map<Long, String> cinemasMap = new HashMap<Long, String>();
		for (Cinema cinema : cinemas) {
			cinemasMap.put(cinema.getId(), cinema.getTitle());
		}

		return cinemasMap;
	}

//	@RequestMapping(value = "/admin/details/session-details", method = RequestMethod.GET)
//	public String viewSessionDetails(@RequestParam Long sessionId, Model model) {
//		return "/admin/details/session-details";
//	}

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
