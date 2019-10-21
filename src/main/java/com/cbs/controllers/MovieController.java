package com.cbs.controllers;

import com.cbs.model.Movie;
import com.cbs.model.MyReponse;
import com.cbs.services.ActorService;
import com.cbs.services.FormatTypeService;
import com.cbs.services.MovieService;
import com.cbs.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;

    private final ActorService actorService;

    private final GenreService genreService;
    
    private final FormatTypeService formatTypeService;

    @Autowired
    public MovieController(MovieService movieService, ActorService actorService, GenreService genreService, FormatTypeService formatTypeService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.genreService = genreService;
        this.formatTypeService = formatTypeService;
    }

    @RequestMapping(value = "/admin/movie", method = RequestMethod.GET)
    public String allMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "/admin/movie-list";
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String allMovieUser(@RequestParam(defaultValue = "1", required = false) Integer page, Model model) {
        Page<Movie> pages = movieService.getAllMoviesPage(page);
        model.addAttribute("allMovie", pages);
        model.addAttribute("movies", movieService.getAllMovies());
        return "/movie";
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET, params = {"movieTitle"})
    public String searchMovie(@RequestParam String movieTitle, @RequestParam(defaultValue = "1", required = false) Integer page, Model model) {
        Page<Movie> searchResult = movieService.searchByTittle(movieTitle, page);
        model.addAttribute("allMovie", searchResult);
        return "/movie";
    }

    @GetMapping("/api/movie")
    public ResponseEntity<Object> getAllMovieApi() {
        MyReponse<List<Movie>> response = new MyReponse<>("success", movieService.getAllMovies());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/add/movie", method = RequestMethod.GET)
    public String addMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("formats", formatTypeService.getAllFormatType());
        model.addAttribute("allActors", actorService.getAllActors());
        return "/admin/add/movie";
    }

    @RequestMapping(value = "/admin/add/movie", method = RequestMethod.POST)
    public String addMovie(@Valid Movie movie, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
//        System.out.println("-----------" + movie.getDate_end());
        movieService.addMovie(movie);

        return "redirect:/admin/movie";
    }

    @RequestMapping(value = "/admin/delete/movie", method = RequestMethod.GET, params = {"movieId"})
    public String deleteMovie(@RequestParam Long movieId, Model model) {
        movieService.deleteMovieByID(movieId);
        return "redirect:/admin/movie";
    }

    @RequestMapping(value = "/admin/edit/movie", method = RequestMethod.GET, params = {"movieId"})
    public String editMovie(@RequestParam Long movieId, Model model) {
        model.addAttribute("movie", movieService.getMovieByID(movieId));
        model.addAttribute("formats", formatTypeService.getAllFormatType());
        model.addAttribute("allActors", actorService.getAllActors());
        return "/admin/add/movie";
    }

    @RequestMapping(value = "/admin/add/genre_to_movie", method = RequestMethod.GET, params = {"movieId"})
    public String addGenres(@RequestParam Long movieId, Model model) {
        model.addAttribute("allGenres", genreService.getAllGenre());
        model.addAttribute("movie", movieService.getMovieByID(movieId));
        
        return "/admin/add/genre_to_movie";
    }

    @RequestMapping(value = "/admin/add/genre_to_movie", method = RequestMethod.POST)
    public String addGenres(@Valid Movie movie, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        movieService.addMovie(movie);
        return "redirect:/admin/movie";
    }

    @RequestMapping(value = "/details/movie", method = RequestMethod.GET)
    public String getMovie(@RequestParam Long movieId, Model model) {
        model.addAttribute("movie", movieService.getMovieByID(movieId));
        return "/details/movie";
    }

    @RequestMapping(value = "/admin/add/actor_to_movie", method = RequestMethod.GET, params = {"movieId"})
    public String addActors(@RequestParam Long movieId, Model model) {
        model.addAttribute("allActors", actorService.getAllActors());
        model.addAttribute("movie", movieService.getMovieByID(movieId));
        return "/admin/add/actor_to_movie";
    }

    @RequestMapping(value = "/admin/add/actor_to_movie", method = RequestMethod.POST)
    public String addActors(@Valid Movie movie, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        movieService.addMovie(movie);
        return "redirect:/admin/movie";
    }

    private void validateImage(MultipartFile image) {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new RuntimeException("Only JPG images are accepted");
        }
    }

}
