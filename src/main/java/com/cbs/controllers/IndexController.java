<<<<<<< HEAD
package com.cbs.controllers;

import com.cbs.model.Movie;
import com.cbs.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    private final MovieService movieService;

    @Autowired
    public IndexController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping({"/", "index"})
    public String index(){
        return "/client/index";
    }

    @GetMapping("/moviehehe")
    public String getAllMovieApi(Model model) {
        System.out.println("loc" + movieService.getAllMovies());
        model.addAttribute("movies", movieService.getAllMovies());
        return "/client/test";
    }
}
=======
package com.cbs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "index"})
    public String index(){
        return "/client/index";
    }
}
>>>>>>> 6fcaa57e7af655f77c499bd74d2851b93a648c49
