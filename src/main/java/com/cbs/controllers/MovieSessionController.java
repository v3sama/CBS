package com.cbs.controllers;

import com.cbs.model.MovieSession;
import com.cbs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class MovieSessionController {

    private final MovieSessionService movieSessionService;
    private final MovieService movieService;
    private final CinemaService cinemaService;
    private final ScreenService screenService;
    private final TicketService ticketService;


    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService, MovieService movieService,
                             CinemaService cinemaService, ScreenService screenService,
                             TicketService ticketService) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.cinemaService = cinemaService;
        this.screenService = screenService;
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/admin/add/session", method = RequestMethod.GET, params = {"cinemaId"})
    public String addSession(@RequestParam Long cinemaId, Model model) {
        MovieSession scheduleSession = new MovieSession();
        model.addAttribute("movieSessionId", scheduleSession.getId());
        model.addAttribute("scheduleSession", scheduleSession);
        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("allScreens", cinemaService.getCinemaByID(cinemaId).getCinemaScreens());
        model.addAttribute("allMovies", movieService.getAllMovies());
        return "/admin/add/session";
    }

    @RequestMapping(value = "/admin/add/session", method = RequestMethod.POST)
    public String addSession(@Valid MovieSession scheduleSession, @RequestParam("price") int price, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allMovies", movieService.getAllMovies());
            model.addAttribute("price", price);
            return "/admin/add/session";
        }
        scheduleSession = movieSessionService.addSession(scheduleSession);
        return "redirect:/admin/session/";
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


    @RequestMapping(value = "/admin/session", method = RequestMethod.GET, params = {"cinemaId"})
    public String allSessionByCinema(@RequestParam Long cinemaId, Model model) {
        model.addAttribute("cinemaId", cinemaId);
        return "/admin/session";
    }

    @RequestMapping(value = "/admin/session", method = RequestMethod.GET)
    public String allSession(Model model) {
        model.addAttribute("sessions", movieSessionService.getAllSession());
        return "/admin/session";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET, params = {"cinemaId"})
    public String allSessionByCinemaUser(@RequestParam Long cinemaId, Model model) {

        return "/session";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET, params = {"screenId"})
    public String allSessionByScreenUser(@RequestParam Long screenId, Model model) {
        return "/session";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET, params = {"movieId"})
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
