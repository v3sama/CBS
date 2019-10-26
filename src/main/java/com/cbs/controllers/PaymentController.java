package com.cbs.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cbs.model.CardInformation;
import com.cbs.services.CardService;

@Controller
public class PaymentController {
	private CardService cardService;
	@Autowired
	public PaymentController(CardService cardService) {
		this.cardService = cardService;
	}
	
	@RequestMapping(value = "/admin/pay/order", method = RequestMethod.POST, params = {"orderId"})
    public String makePayment(@Valid CardInformation card, Model model, BindingResult bindingResult, Long orderId) {
		//validate card here
		if(card.getCard_no().length() != 16 || card.getCard_date() == null) {
			return "redirect:/admin/update/order?orderId="+orderId;
		}
		cardService.addCard(card);
		
        return "redirect:/admin/update/order?orderId="+orderId;
    }
}
