
package com.cbs.controllers;

import org.apache.poi.ss.usermodel.Sheet;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.stereotype.Controller;

import com.cbs.model.Ticket;
import com.cbs.repository.TicketRepository;

@Controller
public class ReportController {
	private TicketRepository ticketRepo;
	
	@Autowired
	public ReportController(TicketRepository ticketRepository) {
		this.ticketRepo = ticketRepository;
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

	public List<Ticket> ticketByDimension(Long provinceId, Long cinemaId, Long screenId, Long movieId) {

		return null;
	}

	public List<Ticket> ticketsQuery(Long provinceId, Long cinemaId, Long screenId, Long movieId, Long memberId,
			LocalDate fromDate, LocalDate toDate ) {
		return ticketRepo.ticketsQuery( provinceId,  cinemaId,  screenId,  movieId,  memberId,
				 fromDate,  toDate);
	//	return null;
	}
}
