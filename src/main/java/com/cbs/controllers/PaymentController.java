package com.cbs.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbs.model.CardInformation;
import com.cbs.services.CardService;

@Controller
public class PaymentController {
	private CardService cardService;
	@Autowired
	public PaymentController(CardService cardService) {
		this.cardService = cardService;
	}
	
	@RequestMapping(value = "/admin/pay/order", method = RequestMethod.POST)
    public void makePayment(@Valid CardInformation card, Model model) {
		
		cardService.addCard(card);
        //return "redirect:/admin/viewDetails/order?orderId="+card.get;
    }
}
