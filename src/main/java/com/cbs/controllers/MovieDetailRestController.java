package com.cbs.controllers;

import com.cbs.dto.*;
import com.cbs.model.Movie;
import com.cbs.model.Rating;
import com.cbs.model.Ticket;
import com.cbs.services.GenreService;
import com.cbs.services.MovieService;
import com.cbs.services.RatingService;
import com.cbs.services.TicketService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieDetailRestController {
    private final MovieService movieService;
    private final GenreService genreService;
    private final RatingService ratingService;
    private final TicketService ticketService;

    public MovieDetailRestController(MovieService movieService, GenreService genreService, RatingService ratingService, TicketService ticketService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.ratingService = ratingService;
        this.ticketService = ticketService;
    }

    //Khai báo rating map
    Map<String, Float> ratingMap = new HashMap<String, Float>() {{
        put("star1", 1f);
        put("star1half", 1.5f);
        put("star2", 2f);
        put("star2half", 2.5f);
        put("star3", 3f);
        put("star3half", 3.5f);
        put("star4", 4f);
        put("star4half", 4.5f);
        put("star5", 5f);
    }};


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
    //http://localhost:8080/

    @PostMapping(value = "api/postReview")
    public String nhanGhe(@RequestBody ReviewFormDTO reviewFormDTO) throws Exception {

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)
        ) {
            CustomUserDetail loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            TicketReviewDTO reviewDTO = ticketService.findTicketUAM(loggedInUser.getUserId().intValue(), Integer.parseInt(reviewFormDTO.getMovie()));
            String loc = reviewDTO.getId();
            if (Integer.parseInt(loc) > 0) {
                Rating rating = new Rating();
                rating.setContent(reviewFormDTO.getContent());
                List<TicketReviewDTO> reviewDTOList = ticketService.findListTicketByUAM(loggedInUser.getUserId().intValue(), Integer.parseInt(reviewFormDTO.getMovie()));
                String tickid = reviewDTOList.get(0).getId();
                System.out.println(tickid);
                return "vui";
            }
            return "chuamua";
        }
        return "dangnhap";
    }


}
