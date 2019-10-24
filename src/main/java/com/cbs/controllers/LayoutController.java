package com.cbs.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbs.dto.CustomUserDetail;
import com.cbs.model.Row;
import com.cbs.model.Seat;

import com.cbs.services.RowService;
import com.cbs.services.SeatServices;

@Controller
public class LayoutController {
	private SeatServices seatServices;
	private RowService rowService;

	@Autowired
	public LayoutController(SeatServices seatServices, RowService rowService) {
		this.seatServices = seatServices;
		this.rowService = rowService;
	}

	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String index(Model model) {

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		model.addAttribute("userInfo", username);

//		CustomUserDetail myUserDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		Long userId=myUserDetails.getUser().getId();
		return "/admin/index";
	}

	@RequestMapping(value = "/admin/seat", method = RequestMethod.GET)
	public String seatSetting(Model model) {
		model.addAttribute("row", rowService.getAllRow().size());
		return "/admin/seat";
	}

	@RequestMapping(value = "/admin/seat", method = RequestMethod.POST)
	public String seatSetting(Model model, String row, String error) {
		int numOfRow = Integer.parseInt(row);
		int oldRow = rowService.getAllRow().size();
		// moi hang co 12 ghe
		if (numOfRow > oldRow) {
			List<Row> rows = new ArrayList<Row>();
			List<Seat> seats = new ArrayList<Seat>();

			for (int i = oldRow + 1; i <= numOfRow; i++) {
				Row r = new Row();
				r.setId(Long.valueOf(i));
				r.setTittle(String.valueOf((char) (i + 64)));
				rows.add(r);

				for (int j = 1; j <= 12; j++) {
					Seat s = new Seat();
					s.setRow(r);
					// hang thu 6 -> 8 la ghe VIP
					s.setVIP(r.getId() >= 5 && r.getId() <= 8);
					seats.add(s);
				}
			}

			rowService.addAllRow(rows);
			seatServices.addAllSeat(seats);
		}
		model.addAttribute("error", "The new value can't be less than old value");
		return "redirect:/admin/seat";

	}
}
