
package com.cbs.controllers;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

		System.out.println("Create file excel");
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Customer_Info");
	    int rowNum = 0;
	    Row firstRow = sheet.createRow(rowNum++);
	    Cell firstCell = firstRow.createCell(0);
	    firstCell.setCellValue("List of Customer");
	    List<TicketReportDTO> tickets = new ArrayList<TicketReportDTO>();
	 
	    for (TicketReportDTO ticket : tickets) {
	      Row row = sheet.createRow(rowNum++);
	      Cell cell1 = row.createCell(0);
	      cell1.setCellValue(ticket.getMemberId());
	      Cell cell2 = row.createCell(1);
	      cell2.setCellValue(ticket.getOrderId());
	      Cell cell3 = row.createCell(2);
	      cell3.setCellValue(ticket.getOrderTime());
	      Cell cell4 = row.createCell(3);
	      cell3.setCellValue(ticket.getAmount());
	    }
	    try {
	      FileOutputStream outputStream = new FileOutputStream("Demo-ApachePOI-Excel.xlsx");
	      workbook.write(outputStream);
	      workbook.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    System.out.println("Done");
	    return "Download";
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
