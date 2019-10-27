
package com.cbs.controllers;

import org.apache.poi.ss.usermodel.Sheet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbs.dto.ReportForm;
import com.cbs.dto.TicketReportDTO;
import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Movie;
import com.cbs.model.Province;
import com.cbs.model.Ticket;
import com.cbs.model.User;
import com.cbs.repository.CinemaRepository;
import com.cbs.repository.MovieRepository;
import com.cbs.repository.MovieSessionRepository;
import com.cbs.repository.OrderRepository;
import com.cbs.repository.ProvinceRepository;
import com.cbs.repository.TicketRepository;
import com.cbs.repository.UserRepository;

@Controller
public class ReportController {
	private ProvinceRepository provinceRepo;
	private CinemaRepository cinemaRepo;
	private MovieRepository movieRepo;
	private MovieSessionRepository movieSessionRepo;
	private UserRepository userRepo;
	private OrderRepository orderRepo;
	private TicketRepository ticketRepo;
	private LocalDate fromDate;
	private LocalDate toDate;

	@Autowired
	public ReportController(TicketRepository ticketRepo, ProvinceRepository provinceRepo, CinemaRepository cinemaRepo,
			MovieRepository movieRepo, UserRepository userRepo, OrderRepository orderRepo,
			MovieSessionRepository movieSessionRepo) {
		this.provinceRepo = provinceRepo;
		this.cinemaRepo = cinemaRepo;
		this.movieRepo = movieRepo;
		this.userRepo = userRepo;
		this.orderRepo = orderRepo;
		this.movieSessionRepo = movieSessionRepo;
		this.ticketRepo = ticketRepo;

	}

	public String createExcel() {

		// Tạo workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// # Tạo spreadsheet
		XSSFSheet spreadsheet = workbook.createSheet("Sheet Name");

		// # Tạo row trong spreadsheet
		XSSFRow row = spreadsheet.createRow(1);

		// # Tạo cell trong row
		Cell cell = row.createCell(1);
		return "";
	}
	@RequestMapping(value = "/admin/report", method = RequestMethod.GET)
	public String reportHome(Model model) {
		model.addAttribute("provinces", provinceRepo.findAll());
		model.addAttribute("cinemas", cinemaRepo.findAll());
		model.addAttribute("movies", movieRepo.findAll());
		model.addAttribute("customers",  userRepo.findAll());
		model.addAttribute("reportForm", new ReportForm());

		return "/admin/report";
	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<TicketReportDTO> ticketByCinema(@Valid ReportForm reportForm, Model model) {
		Long provinceId = reportForm.getProvinceId();
		Long cinemaId= reportForm.getCinemaId();
		Long movieId = reportForm.getMovieId();
		Long customerId = reportForm.getCustomerId();
		LocalDateTime fromDate = convertToLDT(reportForm.getFromDate());
		LocalDateTime toDate = reportForm.getToDate().atTime(23, 59);
		
		List<TicketReportDTO> list = new ArrayList<TicketReportDTO>();
		if(provinceId == 0 && cinemaId != 0 && movieId != 0) {
			list.addAll(ticketRepo.findTicketByMovieCinema(cinemaId, movieId, fromDate, toDate));
		} else if(provinceId != 0 && cinemaId == 0 && movieId != 0) {
			list.addAll(ticketRepo.findTicketByMovieProvince(provinceId, movieId, fromDate, toDate));
		} else if (cinemaId != 0 && movieId == 0) {
			list.addAll(ticketRepo.findTicketByCinema(cinemaId, fromDate, toDate));
		} else if(provinceId != 0 && cinemaId == 0 && movieId == 0) {
			list.addAll(ticketRepo.findTicketByProvince(provinceId, fromDate, toDate));
		} else if(provinceId == 0 && cinemaId == 0 && movieId != 0) {
			list.addAll(ticketRepo.findTicketByMovie(movieId, fromDate, toDate));
		} else if(customerId != 0) {
			list.addAll(ticketRepo.findTicketByCustomer(customerId, fromDate, toDate));
		} else if(provinceId == 0 && cinemaId == 0 && movieId == 0) {
			list.addAll(ticketRepo.findTicket(fromDate, toDate));
		}
		
	
		
		//model.addAttribute("ticket",list);
		return list;
	}
	
	public LocalDateTime convertToLDT(LocalDate date) {
        return date.atStartOfDay();
	}
}
