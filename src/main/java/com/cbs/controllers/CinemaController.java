package com.cbs.controllers;

import com.cbs.dto.CinemaCreationDTO;
import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Screen;
import com.cbs.services.CinemaScreenService;
import com.cbs.services.CinemaService;
import com.cbs.services.DiscountService;
import com.cbs.services.ProvinceService;
import com.cbs.services.RowService;
import com.cbs.services.ScreenService;
import com.cbs.services.SeatServices;
import com.cbs.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Controller
public class CinemaController {
    private CinemaService cinemaService;
    private ScreenService screenService;
    private CinemaScreenService cinemaScreenService;
    private ProvinceService provinceService;
    private RowService rowService; 
    
    @Autowired
    public CinemaController(CinemaService cinemaService, ScreenService screenService,
    		CinemaScreenService cinemaScreenService,ProvinceService provinceService,RowService rowService) {
        this.cinemaService = cinemaService;
        this.screenService = screenService;
        this.cinemaScreenService = cinemaScreenService;
        this.provinceService = provinceService;
        this.rowService = rowService;
    }

    @RequestMapping(value = "/admin/cinema", method = RequestMethod.GET)
    public String allCinema(Model model) {
    	List<Cinema> list = cinemaService.getAllCinema();
        model.addAttribute("cinemas", list);
        return "/admin/cinema-list";
    }

    @RequestMapping(value = "/cinema", method = RequestMethod.GET)
    public String allCinemaUser(@RequestParam(defaultValue = "1", required = false) Integer page, Model model) {
        Page<Cinema> pages = cinemaService.getAllCinemaPage(page);
        model.addAttribute("allCinema", pages);
        return "/cinema";
    }

	/*
	 * @RequestMapping(value = "/admin/add/cinema", method = RequestMethod.GET)
	 * public String addCinema(Model model) { AddCinema cinema = new AddCinema();
	 * List<Screen> screens = screenService.getAllScreen();
	 * 
	 * Map<Long,Integer> screenRows = new HashMap<>(); for (Screen screen : screens)
	 * {
	 * 
	 * screenRows.put(screen.getId(), 0); } cinema.setScreenRows(screenRows);
	 * 
	 * model.addAttribute("cinema", new Cinema()); model.addAttribute("provinces",
	 * provinceService.getAllProvince()); model.addAttribute("screens",
	 * screenService.getAllScreen());
	 * 
	 * return "/admin/add/cinema"; }
	 */
    @RequestMapping(value = "/admin/add/cinema", method = RequestMethod.GET)
    public String addCinema(Model model) {
    	CinemaCreationDTO cinemaForm = new CinemaCreationDTO();
    	cinemaForm.setCinema(new Cinema());
    	List<Screen> screens =  screenService.getAllScreen();
    	List<CinemaScreen> list =  new ArrayList<CinemaScreen>();

    	for (Screen screen :screens) {
    		CinemaScreen cs = new CinemaScreen();
    		cs.setScreen(screen);
    		list.add(cs);
		}
    	cinemaForm.setCinemaScreens(list);
    	model.addAttribute("cinemaForm", cinemaForm);
        model.addAttribute("provinces", provinceService.getAllProvince());
        
        return "/admin/add/cinema";
    }

    @RequestMapping(value = "/admin/add/cinema", method = RequestMethod.POST)
    public String addCinema(@Valid CinemaCreationDTO cinemaForm, BindingResult bindingResult, Model model, String[] screens, String[] rows) {
        Set<CinemaScreen> list = new HashSet<CinemaScreen>();
        for (CinemaScreen cs  : cinemaForm.getCinemaScreens()) {
        	if(cs.getId() != null && (cs.getRows() != 0 || cs.getId() != 0)  && cs.getRows() <= rowService.getAllRow().size()) {
        			list.add(cs);
        	}
		}
        if(list.size() != 0) {
        	 Cinema cinema = cinemaForm.getCinema();
             cinemaService.addCinema(cinema);
             cinemaScreenService.addAll(list);
             return "redirect:/admin/cinema";
        }  
       model.addAttribute("error", "You have to choose at least one screen");
       return "redirect:/admin/add/cinema";
    }

	/*
	 * @RequestMapping(value = "/admin/add/cinema", method = RequestMethod.POST)
	 * public String addCinema(@Valid Cinema cinema, BindingResult bindingResult,
	 * Model model, String[] screens, String[] rows) {
	 * cinemaService.addCinema(cinema);
	 * 
	 * Map<Long,Integer> screenList = new HashMap<Long,Integer>();
	 * 
	 * for (int i = 0; i < screens.length; i++) { Long screenId =
	 * Long.parseLong(screens[i]); Integer row = Integer.parseInt(rows[i]);
	 * 
	 * screenList.put(screenId,row); }
	 * cinemaScreenService.addScreenToCinema(cinema,screenList);
	 * 
	 * return "redirect:/admin/cinema"; }
	 */

    @RequestMapping(value = "/admin/delete/cinema", method = RequestMethod.GET, params = {"id"})
    public String deleteCinema(@RequestParam Long id, Model model) {
        cinemaService.deleteCinemaByID(id);
        return "redirect:/admin/cinema";
    }

    @RequestMapping(value = "/admin/edit/cinema", method = RequestMethod.GET, params = {"id"})
    public String editCinema(@RequestParam Long id, Model model) {
    	CinemaCreationDTO  cinemaForm = new CinemaCreationDTO();
    	cinemaForm.setCinema(cinemaService.getCinemaByID(id));
    	
    	cinemaForm.setCinemaScreens(new ArrayList<>(cinemaService.getCinemaByID(id).getCinemaScreens()));
        model.addAttribute("cinemaForm", cinemaForm);
        model.addAttribute("provinces", provinceService.getAllProvince());
 
    
        return "/admin/add/cinema";
    }

}

