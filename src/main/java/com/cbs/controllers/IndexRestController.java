
package com.cbs.controllers;

import com.cbs.dto.*;
import com.cbs.model.*;
import com.cbs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexRestController {
    private final MovieService movieService;
    private final MovieSessionService movieSessionService;
    private final CinemaService cinemaService;
    private final ProvinceService provinceService;


    @Autowired
    public IndexRestController(MovieService movieService, MovieSessionService movieSessionService, CinemaService cinemaService, ProvinceService provinceService) {
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.cinemaService = cinemaService;
        this.provinceService = provinceService;
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
            movieIndexClientDTO.setMovie_id(movie.getId());
            movieIndexClientDTOS.add(movieIndexClientDTO);
        }
        return movieIndexClientDTOS;
    }

    //Lấy List Cinema
    @GetMapping(value = "/api/cinemaList", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<CinemaIndexClientDTO> getCinemaByProvince(@RequestParam(value = "provinceId") String provinceId) throws Exception{
        List<CinemaIndexClientDTO> cinemaListDTO = new ArrayList<>();
        List<Cinema> cinemaList = cinemaService.getCinemaByProvince(Long.parseLong(provinceId));
//        List<SessionList2DTO> sessionListDTOS = movieSessionService.findSessionByCinemaAndMovie();
//        System.out.println(sessionListDTOS);
        for (Cinema cinema: cinemaList) {
            CinemaIndexClientDTO cinemaDTO = new CinemaIndexClientDTO();
            cinemaDTO.setCinemaId(String.valueOf(cinema.getId()));
            cinemaDTO.setName(cinema.getTitle());
            cinemaDTO.setAddress(cinema.getAddress());
            cinemaListDTO.add(cinemaDTO);
        }
        return cinemaListDTO;
    }

    //Lấy session theo tỉnh thành, cinema và movie
    @RequestMapping(value = "/api/cinemaList1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SessionList2DTO> getCinemas1(@RequestParam(value = "provinceId") String provinceId, @RequestParam(value = "cinemaId") String cinemaId, @RequestParam(value = "movieId") String movieId) throws Exception{
        return movieSessionService.findSessionByCinemaAndMovie(Integer.parseInt(provinceId),Integer.parseInt(cinemaId),Integer.parseInt(movieId));
    }

    //Lấy List Province
    @GetMapping(value = "/api/provinceList", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<ProvinceIndexDTO> getProvinces() throws Exception{
        List<ProvinceIndexDTO> provinceIndexDTOS = new ArrayList<>();
        List<Province> provinces = provinceService.getAllProvince();
//        List<SessionList2DTO> sessionListDTOS = movieSessionService.findSessionByCinemaAndMovie();
//        System.out.println(sessionListDTOS);
        for (Province province: provinces) {
            ProvinceIndexDTO provinceDTO = new ProvinceIndexDTO();
            provinceDTO.setProvince_id(province.getId());
            provinceDTO.setProvince_name(province.getName());

            provinceIndexDTOS.add(provinceDTO);
        }
        return provinceIndexDTOS;
    }

    @GetMapping(value = "/api/UserSession")
    public UserIndexDTO getUserSession(){
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)
        ){
            UserIndexDTO user = new UserIndexDTO();
            CustomUserDetail loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setId(loggedInUser.getUserId());
            user.setName(loggedInUser.getFname());
            return user;
        }else {
            return null;
        }

    }

}
