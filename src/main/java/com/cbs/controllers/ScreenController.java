package com.cbs.controllers;

import com.cbs.model.Screen;
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
    private final ScreenService screenService;

    @Autowired
    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @RequestMapping(value = "/admin/screen", method = RequestMethod.GET)
    public String allScreen(Model model) {
        model.addAttribute("screens", screenService.getAllScreen());
        return "/admin/screen-list";
    }

    @RequestMapping(value = "/admin/screen", method = RequestMethod.GET, params = {"cinemaId"})
    public String allScreenUser(Model model, Long cinemaId) {
//    	List<Screen> screens =  cinemaService.getCinemaByID(cinemaId).getCinemaScreens().stream().map(cs -> cs.getScreen()).collect(Collectors.toList());
    	if(cinemaId != 0)
    		model.addAttribute("screens", screenService.getScreenByCinema(cinemaId));
    	else 
    		model.addAttribute("screens", screenService.getAllScreen());
        //model.addAttribute("screens", screenService.getAllScreen());
        return "/admin/screen-list";
    }

    @RequestMapping(value = "/admin/add/screen", method = RequestMethod.GET)
    public String addScreen(Model model) {
        model.addAttribute("screen", new Screen());
//        model.addAttribute("cinemas", cinemaService.getAllCinema());
        return "/admin/add/screen";
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
    public String editScreen(@RequestParam Long id, Model model) {
        model.addAttribute("screen", screenService.getScreenById(id));
//        model.addAttribute("cinemas", cinemaService.getAllCinema());
        return "/admin/add/screen";
    }

    @RequestMapping(value = "/admin/delete/screen", method = RequestMethod.GET, params = {"id"})
    public String deleteScreen(@RequestParam Long id, Model model) {
        screenService.deleteScreenByID(id);
        return "redirect:/admin/screen";
    }
}
