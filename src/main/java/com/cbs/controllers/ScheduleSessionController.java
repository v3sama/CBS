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
public class ScheduleSessionController {

    private final FilmSessionService filmSessionService;
    private final FilmService filmService;
    private final CinemaService cinemaService;
    private final ScreenService screenService;
    private final TicketService ticketService;


    @Autowired
    public ScheduleSessionController(FilmSessionService filmSessionService, FilmService filmService,
                             CinemaService cinemaService, ScreenService screenService,
                             TicketService ticketService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.cinemaService = cinemaService;
        this.screenService = screenService;
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/admin/add/session", method = RequestMethod.GET, params = {"cinemaId"})
    public String addSession(@RequestParam Long cinemaId, Model model) {
        MovieSession scheduleSession = new MovieSession();
        model.addAttribute("filmSessionId", scheduleSession.getId());
        model.addAttribute("scheduleSession", scheduleSession);
        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("allScreens", cinemaService.getCinemaByID(cinemaId).getCinemaScreens());
        model.addAttribute("allFilms", filmService.getAllFilms());
        return "/admin/add/session";
    }

    @RequestMapping(value = "/admin/add/session", method = RequestMethod.POST)
    public String addSession(@Valid MovieSession scheduleSession, @RequestParam("price") int price, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allFilms", filmService.getAllFilms());
            model.addAttribute("price", price);
            return "/admin/add/session";
        }
        scheduleSession = filmSessionService.addSession(scheduleSession);
        return "redirect:/admin/session/";
    }

    @RequestMapping(value = "/admin/edit/session", method = RequestMethod.GET)
    public String editSession(@RequestParam Long sessionId, Model model) {
        MovieSession scheduleSession = filmSessionService.getSessionById(sessionId);
        model.addAttribute("scheduleSession", scheduleSession);
        model.addAttribute("allScreens", scheduleSession.getCinemaScreen().getScreen());
        model.addAttribute("allFilms", filmService.getAllFilms());
        return "/admin/edit/session";
    }

    @RequestMapping(value = "/admin/delete/session", method = RequestMethod.GET)
    public String deleteSession(@RequestParam Long filmSessionId, Model model) {
        filmSessionService.deleteSessionById(filmSessionId);
        return "redirect:/admin/session";
    }


    @RequestMapping(value = "/admin/session", method = RequestMethod.GET, params = {"cinemaId"})
    public String allSessionByCinema(@RequestParam Long cinemaId, Model model) {
        model.addAttribute("cinemaId", cinemaId);
        return "/admin/session";
    }

    @RequestMapping(value = "/admin/session", method = RequestMethod.GET)
    public String allSession(Model model) {
        model.addAttribute("sessions", filmSessionService.getAllSession());
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

    @RequestMapping(value = "/session", method = RequestMethod.GET, params = {"filmId"})
    public String allSessionByFilmUser(@RequestParam Long filmId, Model model) {
        return "/session";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String allSessionUser(Model model) {
        model.addAttribute("sessions", filmSessionService.getAllSession());
        return "/session";
    }

    @RequestMapping(value = "/details/session", method = RequestMethod.GET)
    public String getSession(@RequestParam Long sessionId, Model model) {
        model.addAttribute("scheduleSession", filmSessionService.getSessionById(sessionId));
        return "/details/session";
    }
}
