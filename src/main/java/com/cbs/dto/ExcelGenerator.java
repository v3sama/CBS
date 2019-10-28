package com.cbs.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

	public static ByteArrayInputStream ticketsToExcel(List<TicketReportDTO> tickets) throws IOException {
		String[] COLUMNs = {"Customer ID", "OrderId", "Order Time","Province", "Cinema ", "Movie","Movie Format","Session Time","VIP Seat","Ticket Price" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("Reports by CBS");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
			

			// CellStyle for Age
			CellStyle dateStyle = workbook.createCellStyle();
			dateStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
			CellStyle numberStyle = workbook.createCellStyle();
			numberStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("#,##0"));

			int rowIdx = 1;
			for (TicketReportDTO ticket : tickets) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(ticket.getMemberId());
				row.createCell(1).setCellValue(ticket.getOrderId());
				row.createCell(2).setCellValue(ticket.getOrderTime());
				row.createCell(3).setCellValue(ticket.getProvince());
				row.createCell(4).setCellValue(ticket.getCinema());
				row.createCell(5).setCellValue(ticket.getMovie());
				row.createCell(6).setCellValue(ticket.getFormat());
				row.createCell(7).setCellValue(ticket.getSessionTime());
				row.createCell(8).setCellValue(ticket.getSeatType());
				row.createCell(9).setCellValue(ticket.getPrice());
				
				//set styles
				row.getCell(2).setCellStyle(dateStyle);
				row.getCell(7).setCellStyle(dateStyle);
				row.getCell(9).setCellStyle(numberStyle);
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}