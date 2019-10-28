package com.cbs.controllers;

import com.cbs.dto.MovieIndexClientDTO;
import com.cbs.model.Movie;
import com.cbs.services.GenreService;
import com.cbs.services.MovieService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
public class MovieDetailRestController {
    private final MovieService movieService;
    private final GenreService genreService;

    public MovieDetailRestController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }


    @GetMapping(value = "/api/movieDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody MovieIndexClientDTO getMovieDetail(@RequestParam(value = "id") String movieid) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        Movie movie = movieService.getMovieByID(Long.parseLong(movieid));
        MovieIndexClientDTO movieIndexClientDTO = new MovieIndexClientDTO();
        movieIndexClientDTO.setMovie_title(movie.getTitle());
        movieIndexClientDTO.setMovie_image(movie.getImage());
        movieIndexClientDTO.setTrailer_link(movie.getTrailer_link());
        movieIndexClientDTO.setMovie_thumbnail(movie.getThumbnail());
        movieIndexClientDTO.setDuration(String.valueOf(movie.getDuration()));
        movieIndexClientDTO.setAvg_point(String.valueOf(movie.getAvg_user_rating_star()));
        movieIndexClientDTO.setMovie_id(movie.getId());
        movieIndexClientDTO.setRate_type(movie.getRating_type());
        movieIndexClientDTO.setDate_release(formatter.format(movie.getDate_release()));
        movieIndexClientDTO.setFormat_type(movie.getFormatType().getName());
        movieIndexClientDTO.setDirector(movie.getDirector());
        movieIndexClientDTO.setGenres(movie.getGenres().toString());
        movieIndexClientDTO.setActors(movie.getActors().toString());
        movieIndexClientDTO.setLanguage(movie.getLanguage());
        movieIndexClientDTO.setDescription(movie.getDescription());
        return movieIndexClientDTO;
    }
}
