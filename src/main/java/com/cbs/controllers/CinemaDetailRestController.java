package com.cbs.controllers;

import com.cbs.dto.CinemaIndexClientDTO;
import com.cbs.model.Cinema;
import com.cbs.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaDetailRestController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaDetailRestController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }
    
    @GetMapping(value = "/api/cinemadetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CinemaIndexClientDTO getCinemaById(@RequestParam(value = "id") String id) throws Exception{
        CinemaIndexClientDTO cinemaDTO = new CinemaIndexClientDTO();

        Cinema cinema = cinemaService.getCinemaByID(Long.parseLong(id));
//        List<SessionList2DTO> sessionListDTOS = movieSessionService.findSessionByCinemaAndMovie();
//        System.out.println(sessionListDTOS);
        cinemaDTO.setAddress(cinema.getAddress());
        cinemaDTO.setName(cinema.getTitle());
        return cinemaDTO;
    }


}
