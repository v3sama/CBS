package com.cbs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbs.model.Seat;
import com.cbs.repository.SeatRepository;
import com.cbs.services.SeatServices;
import com.cbs.services.UserService;

@Controller
public class LayoutController {
	private SeatServices seatServices;

	@Autowired
	public LayoutController(SeatServices seatServices) {
		this.seatServices = seatServices;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String index() {

		return "/admin/index";
	}

	@RequestMapping(value = "/admin/seat", method = RequestMethod.GET)
	public String seatSetting(Model model) {
		model.addAttribute("seat",new Seat());
		return "/admin/seat";
	}
}
