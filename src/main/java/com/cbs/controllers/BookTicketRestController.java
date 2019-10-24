package com.cbs.controllers;

import com.cbs.dto.SeatRenderDTO;
import com.cbs.model.MovieSession;
import com.cbs.model.Seat;
import com.cbs.model.Ticket;
import com.cbs.services.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookTicketRestController {
    private final MovieService movieService;
    private final MovieSessionService movieSessionService;
    private final CinemaService cinemaService;
    private final ProvinceService provinceService;
    private final TicketService ticketService;
    private final SeatServices seatServices;

    @Autowired
    public BookTicketRestController(MovieService movieService, MovieSessionService movieSessionService, CinemaService cinemaService, ProvinceService provinceService, TicketService ticketService, SeatServices seatServices) {
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.cinemaService = cinemaService;
        this.provinceService = provinceService;
        this.ticketService = ticketService;
        this.seatServices = seatServices;
    }


    //Lấy ghế theo session id
//    public @ResponseBody I
    @GetMapping(value = "/api/getSeat", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SeatRenderDTO getSeatAvail(@RequestParam(value = "session") String sessionId) throws Exception{

        //Lấy thông tin trong phòng chiếu theo suất chiếu
        MovieSession movieSession = movieSessionService.getSessionById(Long.parseLong(sessionId));
        int screenRow = movieSession.getCinemaScreen().getRows();
        Long screenID = movieSession.getCinemaScreen().getId();

        //Khai báo
        SeatRenderDTO seatDTO = new SeatRenderDTO();
        ArrayList<String> rowMap = new ArrayList<String>();
        String map ="";

        //Lấy danh sách ghế đã đặt trong suất này
        List<Ticket> ticketList = ticketService.findBySessionId(Long.parseLong(sessionId));
        Multimap<Long, Long> takenSeat = HashMultimap.create();
        for (Ticket ticket : ticketList){
            takenSeat.put(ticket.getSeat().getRow().getId(), ticket.getSeat().getId()%12);
//            System.out.println(takenSeat);
        }

        //Lấy danh sách ghế vip
        List<Seat> seats = seatServices.findVipSeat();
        Multimap<Long, Long> vipSeat = HashMultimap.create();
        for (Seat seat : seats){
            vipSeat.put(seat.getRow().getId(), seat.getId()%12);
        }

        //Tạo List String Seat Map
        for (int i = 1; i <= screenRow; i++) {
            ArrayList<Long> rowTakenSeatList = new ArrayList<>();
            for (Long index : takenSeat.get(Long.valueOf(i))){
                rowTakenSeatList.add(index);
            }

            ArrayList<Long> rowVipSeatList = new ArrayList<>();
            for (Long index : vipSeat.get(Long.valueOf(i))){
                rowVipSeatList.add(index);
            }

            for (int j = 1; j <= 12; j++) {
                if (rowTakenSeatList.contains(Long.valueOf(j))){
                    map += "u";
                }else if(rowVipSeatList.contains(Long.valueOf(j))){
                    map += "v";
                }
                else {
                    map += "e";
                }
            }
            rowTakenSeatList.clear();
            System.out.println(map);
            rowMap.add(map);
            map = "";
        }
        seatDTO.setRowMap(rowMap);

        //Lấy Giá
        //Check ngày cuối tuần (t7, cn)



        return seatDTO;
    }

}
