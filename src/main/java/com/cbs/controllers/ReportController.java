
package com.cbs.controllers;

import org.apache.poi.ss.usermodel.Sheet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cbs.dto.TicketReportDTO;
import com.cbs.model.Cinema;
import com.cbs.model.CinemaScreen;
import com.cbs.model.Province;
import com.cbs.model.Ticket;
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

	@RequestMapping(value = "/admin/report", method = RequestMethod.GET,params = {"cinemaId","fromDate","toDate"})
	public List<TicketReportDTO> ticketByCinema(@RequestParam Long cinemaId, 
			@RequestParam(required = false, value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, 
			@RequestParam(required = false, value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate toDate) {
		List<TicketReportDTO> list =  ticketRepo.findTicketByCinema(cinemaId, fromDate, toDate);
		return list;
	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.GET, params = {"provinceId","fromDate","toDate"})
	public List<TicketReportDTO> ticketsQuery(Long provinceId, LocalDate fromDate, LocalDate toDate) {
		List<TicketReportDTO> list = ticketRepo.findTicketByProvince(provinceId, fromDate, toDate);
		return list;
	}
	

}
