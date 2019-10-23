package com.cbs.controllers;

import com.cbs.dto.MovieIndexClientDTO;
import com.cbs.dto.ScheduleIndexClientDTO;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Movie;
import com.cbs.model.MovieSession;
import com.cbs.services.CinemaScreenService;
import com.cbs.services.MovieService;
import com.cbs.services.MovieSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexRestController {
    private final MovieService movieService;
    private final MovieSessionService movieSessionService;
    private final CinemaScreenService cinemaScreenService;

    @Autowired
    public IndexRestController(MovieService movieService, MovieSessionService movieSessionService, CinemaScreenService cinemaScreenService) {
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.cinemaScreenService = cinemaScreenService;
    }

    @GetMapping(value = "/api/movieShowing", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<MovieIndexClientDTO> getAllMovieNowShowing() {

        List<Movie> movies = movieService.getMovieActive();
        List<MovieIndexClientDTO> res = AddToMovieIndexDTO(movies);
        return res;
    }

    @GetMapping(value = "/api/movieUpComing", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<MovieIndexClientDTO> getAllMovieUpComing() {

        List<Movie> movies2 = movieService.getMovieSapChieu(LocalDate.now());
        List<MovieIndexClientDTO> res =  AddToMovieIndexDTO(movies2);
        return res;
    }

    public List<MovieIndexClientDTO> AddToMovieIndexDTO(List<Movie> movies){
        List<MovieIndexClientDTO> movieIndexClientDTOS = new ArrayList<>();
        for (Movie movie: movies) {
            MovieIndexClientDTO movieIndexClientDTO = new MovieIndexClientDTO();
            movieIndexClientDTO.setMovie_title(movie.getTitle());
            movieIndexClientDTO.setMovie_image(movie.getImage());
            movieIndexClientDTO.setTrailer_link(movie.getTrailer_link());
            movieIndexClientDTO.setMovie_thumbnail(movie.getThumbnail());
            movieIndexClientDTO.setDuration(String.valueOf(movie.getDuration()));
            movieIndexClientDTO.setAvg_point(String.valueOf(movie.getAvg_user_rating_star()));
            movieIndexClientDTOS.add(movieIndexClientDTO);
        }
        return movieIndexClientDTOS;
    }

    @GetMapping(value = "/api/sessionCinema", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<ScheduleIndexClientDTO> getSessionByCinema() {

        List<CinemaScreen> cinemaScreens = cinemaScreenService.findCinemaScreenByCinema_Id(1);

//        List<ScheduleIndexClientDTO> sessions = movieSessionService.findSessionByCachKhac(1, 1);
        System.out.println(cinemaScreens);
        List<ScheduleIndexClientDTO> res = new ArrayList<>();
        return res;
    }


//    @GetMapping(value = "/api/    schedule/")
//    public @ResponseBody Iterable<MovieIndexDTO> getScheduleIndex(){
//
//    }
}
