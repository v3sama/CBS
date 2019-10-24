package com.cbs.controllers;

import com.cbs.dto.SeatRenderDTO;
import com.cbs.model.MovieSession;
import com.cbs.model.Price;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
public class BookTicketRestController {
    private final MovieService movieService;
    private final MovieSessionService movieSessionService;
    private final CinemaService cinemaService;
    private final ProvinceService provinceService;
    private final TicketService ticketService;
    private final SeatServices seatServices;
    private final PriceService priceService;

    @Autowired
    public BookTicketRestController(MovieService movieService, MovieSessionService movieSessionService, CinemaService cinemaService, ProvinceService provinceService, TicketService ticketService, SeatServices seatServices, PriceService priceService) {
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.cinemaService = cinemaService;
        this.provinceService = provinceService;
        this.ticketService = ticketService;
        this.seatServices = seatServices;
        this.priceService = priceService;
    }


    //Lấy ghế theo session id
//    public @ResponseBody I
    @GetMapping(value = "/api/getSeat", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SeatRenderDTO getSeatAvail(@RequestParam(value = "session") String sessionId) throws Exception{

        //Lấy thông tin trong phòng chiếu theo suất chiếu
        MovieSession movieSession = movieSessionService.getSessionById(Long.parseLong(sessionId));
        int screenRow = movieSession.getCinemaScreen().getRows();
        Long screenID = movieSession.getCinemaScreen().getId();
        Long movieId = movieSession.getMovie().getId();

        //Khai báo
        SeatRenderDTO seatDTO = new SeatRenderDTO();
        ArrayList<String> rowMap = new ArrayList<>();
        String map ="";

        //Lấy danh sách ghế đã đặt trong suất này
        List<Ticket> ticketList = ticketService.findBySessionId(Long.parseLong(sessionId));
        Multimap<Long, Long> takenSeat = HashMultimap.create();
        for (Ticket ticket : ticketList){
            long remainder = ticket.getId()%12;
            takenSeat.put(ticket.getSeat().getRow().getId(), (remainder==0?12l:remainder));
        }

        //Lấy danh sách ghế vip
        List<Seat> seats = seatServices.findVipSeat();
        Multimap<Long, Long> vipSeat = HashMultimap.create();
        for (Seat seat : seats){
            long remainder = seat.getId()%12;
            vipSeat.put(seat.getRow().getId(), (remainder==0?12l:remainder));
        }
        //Đổ 2 danh sách vào string seat map
        try {
            //Tạo List String Seat Map
            for (int i = 1; i <= screenRow; i++) {
                ArrayList<Long> rowTakenSeatList = new ArrayList<>();
                for (Long index : takenSeat.get(Long.valueOf(i))){
                    rowTakenSeatList.add(index);
                }
//                System.out.println("row" + i +"co ghe da dat:"+rowTakenSeatList.size() );

                ArrayList<Long> rowVipSeatList = new ArrayList<>();
                for (Long index : vipSeat.get(Long.valueOf(i))){
                    rowVipSeatList.add(index);
                }
//                System.out.println("row" + i +"co ghe vip :"+rowVipSeatList.size() );

                for (int j = 1; j <= 12; j++) {
                    if (!rowTakenSeatList.isEmpty() && rowTakenSeatList.contains(Long.valueOf(j))){
                        map += "u";
                    }else if(!rowVipSeatList.isEmpty() && rowVipSeatList.contains(Long.valueOf(j))){
                        map += "v";
                    }
                    else {
                        map += "e";
                    }
                }
                rowTakenSeatList.clear();
                rowVipSeatList.clear();
                System.out.println(map);
                //đổ string map vào array
                rowMap.add(map);
                map = "";
            }
            //đổ array map vào dto
            seatDTO.setRowMap(rowMap);

        }catch (Exception ignored){
            System.out.println("loi render map");
        }

        //Lấy Giá
        //Check ngày cuối tuần (t7, cn)
        LocalDateTime ngaychieu = movieSession.getTime();
        try {
            Boolean checkWeekend = checkWeekend(ngaychieu);
            List<Price> priceList = priceService.findPricebyMovie(movieId, checkWeekend);
            for (Price price : priceList){
                if (price.getIsVIP()){
                    seatDTO.setPriceVip(price.getPrice());
                }else {
                    seatDTO.setPriceThuong(price.getPrice());
                }
            }
        }catch (Exception e){
            System.out.println("loi add price");
        }
        //Đổ giá vào dto

        return seatDTO;
    }

    private Boolean checkWeekend(LocalDateTime dateTime){
        ZoneId z = ZoneId.of( "Asia/Ho_Chi_Minh" );
        LocalDate today = dateTime.toLocalDate();
        DayOfWeek dow = today.getDayOfWeek();

        Set<DayOfWeek> weekend = EnumSet.of( DayOfWeek.SATURDAY , DayOfWeek.SUNDAY );

        Boolean todayIsWeekend = weekend.contains( dow );
        return todayIsWeekend;
    }

}
