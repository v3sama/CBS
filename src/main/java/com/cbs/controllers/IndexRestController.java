package com.cbs.controllers;

import com.cbs.dto.MovieIndexDTO;
import com.cbs.model.Movie;
import com.cbs.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexRestController {
    private final MovieService movieService;

    @Autowired
    public IndexRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/api/moviehehe", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<MovieIndexDTO> getAllMovieApi() {

        List<Movie> movies = movieService.getMovieActive();
        List<MovieIndexDTO> movieIndexDTOS = new ArrayList<>();
        for (Movie movie: movies) {
            MovieIndexDTO movieIndexDTO = new MovieIndexDTO();
            movieIndexDTO.setMovie_title(movie.getTitle());
            movieIndexDTO.setMovie_image(movie.getImage());
            movieIndexDTO.setTrailer_link(movie.getTrailer_link());
            movieIndexDTO.setMovie_thumbnail(movie.getThumbnail());
            movieIndexDTOS.add(movieIndexDTO);
        }
        System.out.println(movieIndexDTOS);
//        model.addAttribute("movies", movieService.getAllMovies());
        return movieIndexDTOS;
    }

//    @GetMapping(value = "/api/schedule/")
//    public @ResponseBody Iterable<MovieIndexDTO> getScheduleIndex(){
//
//    }
}
