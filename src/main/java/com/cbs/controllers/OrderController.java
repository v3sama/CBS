package com.cbs.controllers;

import com.cbs.dto.MovieIndexClientDTO;
import com.cbs.model.Movie;
import com.cbs.model.Payment;
import com.cbs.model.SOrder;
import com.cbs.model.Ticket;
import com.cbs.services.OrderService;
import com.cbs.services.PaymentService;
import com.cbs.services.TicketService;
import com.cbs.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class OrderController {

    private final OrderService orderService;
  //  private final UserDetailsServiceImpl userDetailService;
 //   private final TicketService ticketService;

    
    @Autowired
     public OrderController(OrderService orderService) {
        this.orderService = orderService;
       // this.userDetailService = userDetailService;
        
      //  this.ticketService = ticketService;
       // this.paymentController = paymentController;
    }

    @RequestMapping(value = "/admin/order", method = RequestMethod.GET)
    public String allOrder(Model model, Long cinemaId) {
        model.addAttribute("orders", orderService.getAllOrder());
        return "/admin/order-list";
    }
    
    @RequestMapping(value = {"/admin/viewDetails/order","review"}, method = RequestMethod.GET,params = {"orderId"})
    public String viewOrderDetails(@RequestParam Long orderId, Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	
    	SOrder order = orderService.getOrderByID(orderId);
    	List<Ticket> tickets = new ArrayList<Ticket>(order.getTickets());
    	Payment payment = order.getPayment();
        model.addAttribute("order", order);
        model.addAttribute("tickets",order.getTickets());
        model.addAttribute("payment",order.getPayment());
        model.addAttribute("card",order.getPayment().getCardInformation());
        return "/admin/details/order";
    }
   
}
