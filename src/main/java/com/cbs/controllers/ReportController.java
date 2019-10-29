package com.cbs.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbs.dto.ExcelGenerator;
import com.cbs.dto.ReportForm;
import com.cbs.dto.TicketReportDTO;
import com.cbs.repository.CinemaRepository;
import com.cbs.repository.MovieRepository;
import com.cbs.repository.ProvinceRepository;
import com.cbs.repository.TicketRepository;
import com.cbs.repository.UserRepository;

@Controller
public class ReportController {
	private ProvinceRepository provinceRepo;
	private CinemaRepository cinemaRepo;
	private MovieRepository movieRepo;
	private UserRepository userRepo;
	private TicketRepository ticketRepo;

	@Autowired
	public ReportController(TicketRepository ticketRepo, ProvinceRepository provinceRepo, CinemaRepository cinemaRepo,
			MovieRepository movieRepo, UserRepository userRepo) {
		this.provinceRepo = provinceRepo;
		this.cinemaRepo = cinemaRepo;
		this.movieRepo = movieRepo;
		this.userRepo = userRepo;
		this.ticketRepo = ticketRepo;

	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.POST)
	public ResponseEntity<InputStreamResource> generateReport(@Valid ReportForm reportForm, Model model)
			throws IOException {

		List<TicketReportDTO> tickets = this.buildTicket(reportForm, model);
		// if(tickets.size() != 0) {
		ByteArrayInputStream in = ExcelGenerator.ticketsToExcel(tickets, reportForm.getFromDate(),
				reportForm.getToDate());
		// return IOUtils.toByteArray(in);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=report.xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		// };
		// return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.GET)
	public String reportHome(Model model) {
		model.addAttribute("provinces", provinceRepo.findAll());
		model.addAttribute("cinemas", cinemaRepo.findAll());
		model.addAttribute("movies", movieRepo.findAll());
		model.addAttribute("customers", userRepo.findAll());
		model.addAttribute("reportForm", new ReportForm());
		return "/admin/report";
	}

	public List<TicketReportDTO> buildTicket(@Valid ReportForm reportForm, Model model) {
		Long provinceId = reportForm.getProvinceId();
		Long cinemaId = reportForm.getCinemaId();
		Long movieId = reportForm.getMovieId();
		Long customerId = reportForm.getCustomerId();
		LocalDateTime fromDate = convertToLDT(reportForm.getFromDate());
		LocalDateTime toDate = reportForm.getToDate().atTime(23, 59);

		List<TicketReportDTO> list = new ArrayList<TicketReportDTO>();

		if (provinceId == 0 && cinemaId != 0 && movieId != 0) {
			list.addAll(ticketRepo.findTicketByMovieCinema(fromDate, toDate, cinemaId, movieId));
		} else if (provinceId != 0 && cinemaId == 0 && movieId != 0) {
			list.addAll(ticketRepo.findTicketByMovieProvince(fromDate, toDate, provinceId, movieId));
		} else if (cinemaId != 0 && movieId == 0) {
			list.addAll(ticketRepo.findTicketByCinema(fromDate, toDate, cinemaId));
		} else if (provinceId != 0 && cinemaId == 0 && movieId == 0) {
			list.addAll(ticketRepo.findTicketByProvince(fromDate, toDate, provinceId));
		} else if (provinceId == 0 && cinemaId == 0 && movieId != 0) {
			list.addAll(ticketRepo.findTicketByMovie(fromDate, toDate, movieId));
		} else if (customerId != 0) {
			list.addAll(ticketRepo.findTicketByCustomer(fromDate, toDate, customerId));
		} else if (provinceId == 0 && cinemaId == 0 && movieId == 0) {
			list.addAll(ticketRepo.findTicket(fromDate, toDate));
		}

//		list.addAll(ticketRepo.findTicket(fromDate, toDate));
		// model.addAttribute("ticket",list);
		return list;
	}

	public LocalDateTime convertToLDT(LocalDate date) {
		return date.atStartOfDay();
	}
}
