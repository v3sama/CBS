package com.cbs.controllers;

import com.cbs.model.Movie;
import com.cbs.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    private final MovieService movieService;

    @Autowired
    public IndexController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping({"", "index"})
    public String index(){
        return "client/index";
    }

    @GetMapping(value = "/datve", produces = MediaType.APPLICATION_JSON_VALUE)
    public String datVe(@RequestParam(value = "session") String sessionid) {
        return "client/seat-reserve";
    }

    @GetMapping("/confirmVe")
    public String confirmVe(){
        return "client/seat-review";
    }
}
