package com.cbs.controllers;

import com.cbs.dto.*;
import com.cbs.model.Movie;
import com.cbs.model.Rating;
import com.cbs.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final OrderService orderService;

    public MovieDetailRestController(MovieService movieService, GenreService genreService, RatingService ratingService, TicketService ticketService, OrderService orderService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.ratingService = ratingService;
        this.ticketService = ticketService;
        this.orderService = orderService;
    }

    //Khai báo rating map
    Map<String, Float> ratingMap = new HashMap<String, Float>() {{
        put("starhalf", 0.5f);
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
        movieIndexClientDTO.setAvg_point(String.format("%.1f",movie.getAvg_user_rating_star()));
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

                //lấy order theo member với movie
                List<TicketReviewDTO> reviewDTOList = ticketService.findListTicketByUAM(loggedInUser.getUserId().intValue(), Integer.parseInt(reviewFormDTO.getMovie()));
                String ordid = reviewDTOList.get(0).getId();
                if (ratingService.existRatingByOrder(Long.parseLong(ordid))){
                    return "davote";
                }else {
                    rating.setOrder(orderService.findOrderByID(Long.parseLong(ordid)));

                    Float star = ratingMap.get(reviewFormDTO.getStar());
                    rating.setStar(star);

                    System.out.println("star" + star);
                    ratingService.saveRate(rating);

                    Movie movie = movieService.getMovieByID(Long.parseLong(reviewFormDTO.getMovie()));
                    rating.setMovie(movie);
                    int currentVote = movie.getVote_count();
                    System.out.println("currem "+currentVote);
                    int nowVote = currentVote+1;
                    System.out.println("now "+nowVote);
                    movie.setVote_count(nowVote);
                    float lastestPoint = movie.getAvg_user_rating_star();
                    float nowPoint = ((lastestPoint * currentVote) + star)/(nowVote);

                    movie.setAvg_user_rating_star(nowPoint);
                    movieService.addMovie(movie);
                    return "vui";
                }
            }
            return "chuamua";
        }
        return "dangnhap";
    }

    @GetMapping(value = "api/ratingList", produces = MediaType.APPLICATION_JSON_VALUE)
    public RatingListDTO getAllRating(@RequestParam(value = "page") String page, @RequestParam(value = "movie") String movieid){
//        if (page == null){
//            page = 0;
//        }
        RatingListDTO lists = new RatingListDTO();
        Page<Rating> ratings = ratingService.getRatingByMovie(Long.parseLong(movieid), PageRequest.of(Integer.parseInt(page),10));
        lists.setTotalPage(ratings.getTotalPages());
        lists.setTotalElememt(ratings.getTotalElements());
//        lists.setPage();
//        ratings.
        for (Rating rating : ratings) {
            RatingDTO ratingDTO = new RatingDTO();
            ratingDTO.setContent(rating.getContent());
            ratingDTO.setUname(rating.getOrder().getMember().getFirstName());
//            lists.add(ratingDTO);
        }
//        System.out.println(ratings.toString());
        return lists;
    }


}
