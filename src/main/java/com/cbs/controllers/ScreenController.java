package com.cbs.controllers;

import com.cbs.model.Screen;
import com.cbs.services.CinemaService;
import com.cbs.services.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ScreenController {

    private final CinemaService cinemaService;

    private final ScreenService screenService;

    @Autowired
    public ScreenController(CinemaService cinemaService, ScreenService screenService) {
        this.cinemaService = cinemaService;
        this.screenService = screenService;
    }

    @RequestMapping(value = "/admin/screen", method = RequestMethod.GET)
    public String allScreen(Model model) {
        model.addAttribute("screens", screenService.getAllScreen());
        return "/admin/screens";
    }

    @RequestMapping(value = "/screen", method = RequestMethod.GET)
    public String allScreenUser(Model model) {
        model.addAttribute("screens", screenService.getAllScreen());
        return "/screen";
    }

    @RequestMapping(value = "/admin/add/screen", method = RequestMethod.GET)
    public String addScreen(Model model) {
        model.addAttribute("screen", new Screen());
        model.addAttribute("cinemas", cinemaService.getAllCinema());
        return "/admin/add/screens";
    }

    @RequestMapping(value = "/admin/add/screen", method = RequestMethod.POST)
    public String addScreen(@Valid Screen screen, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/screen";
        }
        screenService.addScreen(screen);
        return "redirect:/admin/screen";
    }


    @RequestMapping(value = "/admin/edit/screen", method = RequestMethod.GET)
    public String editScreen(@RequestParam Long screenId, Model model) {
        model.addAttribute("screen", screenService.getScreenById(screenId));
        model.addAttribute("cinemas", cinemaService.getAllCinema());
        return "/admin/edit/screen";
    }

    @RequestMapping(value = "/admin/delete/screen", method = RequestMethod.GET, params = {"ScreenId"})
    public String deleteScreen(@RequestParam Long screenId, Model model) {
        screenService.deleteScreenByID(screenId);
        return "redirect:/admin/screen";
    }
}
