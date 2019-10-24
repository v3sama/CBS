package com.cbs.controllers;

import com.cbs.dto.MovieIndexClientDTO;
import com.cbs.model.Movie;
import com.cbs.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/datve", produces = MediaType.APPLICATION_JSON_VALUE)
    public String datVe(@RequestParam(value = "session") String sessionid) {


        return "client/seat-reserve";
    }
}
