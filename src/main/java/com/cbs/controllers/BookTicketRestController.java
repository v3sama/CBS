package com.cbs.controllers;

import com.cbs.dto.SeatReceiveDTO;
import com.cbs.dto.SeatRenderDTO;
import com.cbs.model.*;
import com.cbs.services.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    private final UserService userService;

    @Autowired
    public BookTicketRestController(MovieService movieService, MovieSessionService movieSessionService, CinemaService cinemaService, ProvinceService provinceService, TicketService ticketService, SeatServices seatServices, PriceService priceService, UserService userService) {
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.cinemaService = cinemaService;
        this.provinceService = provinceService;
        this.ticketService = ticketService;
        this.seatServices = seatServices;
        this.priceService = priceService;
        this.userService = userService;
    }

    private Price priceVipObj;
    private Price priceThuongObj;

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
                //đổ string map vào array
                rowMap.add(map);
                map = "";
            }
            //đổ array map vào dto
            seatDTO.setRowMap(rowMap);

        }catch (Exception ignored){
            ignored.printStackTrace();
            System.out.println("->>> loi render map");
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
                    priceVipObj = price;
                }else {
                    seatDTO.setPriceThuong(price.getPrice());
                    priceThuongObj = price;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("->>> loi add price");
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

    @PostMapping(value = "api/review")
    public String nhanGhe(@RequestBody SeatReceiveDTO seatReceiveDTO) throws Exception{
        try{
            Map<String, Integer> doubleBraceMap = new HashMap<String, Integer>() {{
                put("A", 1);
                put("B", 2);
                put("C", 3);
                put("D", 4);
                put("E", 5);
                put("F", 6);
                put("G", 7);
                put("H", 8);
                put("I", 9);
                put("J", 10);
                put("K", 11);
                put("L", 12);
                put("M", 13);
                put("N", 14);
                put("O", 15);
            }};
            long sessionReceived = seatReceiveDTO.getSesson();
            for (String seat : seatReceiveDTO.getDataghe()) {
                String rowtitle = seat.substring(0, 1);
                int seatAtRow = Integer.parseInt(seat.substring(1, seat.length()));
                int rowId = doubleBraceMap.get(rowtitle);
                int rowIdHandle = rowId == 1 ? 1 : (rowId - 1);
                long seatId = (rowIdHandle * 12) + seatAtRow;
                Seat newSeat = seatServices.getSeatById(seatId);
                MovieSession newMS = movieSessionService.getSessionById(sessionReceived);
                User user = userService.findById(1l);
                Ticket ticket = new Ticket();
                ticket.setSeat(newSeat);
                ticket.setMovieSession(newMS);
                ticket.setMember(user);
                if (newSeat.isVIP()) {
                    ticket.setPrice(priceVipObj);
                } else {
                    ticket.setPrice(priceThuongObj);
                }
                ticketService.addTicket(ticket);
                System.out.println(ticket.toString());
            }

        }catch (Exception e){

        }

        return "ok";
    }
}
