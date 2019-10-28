package com.cbs.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

	public static ByteArrayInputStream ticketsToExcel(List<TicketReportDTO> tickets) throws IOException {
		String[] COLUMNs = { "Customer ID", "OrderId", "Order Time", "Province", "Cinema ", "Movie", "Movie Format",
				"Session Time", "VIP Seat", "Ticket Price" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("ReportDATA");
			// print setup
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
			sheet.setPrintGridlines(false);
			sheet.setFitToPage(true);
			printSetup.setFitWidth((short) 1);
			printSetup.setFitHeight((short) 0);
			
			// Create a new font and alter it.
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short)24);
			font.setFontName("Stencil Std");
			font.setBold(true);
			CellStyle headerRowStyle = workbook.createCellStyle();
			headerRowStyle.setFont(font);
			headerRowStyle.setAlignment(HorizontalAlignment.CENTER);
			
			// sheet header
			sheet.addMergedRegion(new CellRangeAddress(
			        0, //first row (0-based)
			        0, //last row  (0-based)
			        0, //first column (0-based)
			        9  //last column  (0-based)
			));
			
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("BÁO CÁO DOANH THU");
			headerRow.getCell(0).setCellStyle(headerRowStyle);
			
			
			// column header
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short)13);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
			headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
			headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

			// Row for Header
			Row columnHeaderRow = sheet.createRow(4);
			
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = columnHeaderRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// Date Style
			CellStyle dateStyle = workbook.createCellStyle();
			dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
			dateStyle.setBorderBottom(BorderStyle.THIN);
			dateStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			dateStyle.setBorderLeft(BorderStyle.THIN);
			dateStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			dateStyle.setBorderRight(BorderStyle.THIN);
			dateStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			dateStyle.setBorderTop(BorderStyle.THIN);
			dateStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			dateStyle.setWrapText(true);
			// Number Style
			CellStyle numberStyle = workbook.createCellStyle();
			numberStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
			numberStyle.setBorderBottom(BorderStyle.THIN);
			numberStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			numberStyle.setBorderLeft(BorderStyle.THIN);
			numberStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			numberStyle.setBorderRight(BorderStyle.THIN);
			numberStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			numberStyle.setBorderTop(BorderStyle.THIN);
			numberStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

			// Border
			CellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());

			int rowIdx = 5;
			LocalDateTime fromDate = tickets.stream().findFirst().get().getOrderTime();
			LocalDateTime toDate = fromDate;
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
				//update toDate
				if(ticket.getOrderTime().compareTo(toDate) > 0)
					toDate = ticket.getOrderTime();
				// border
				for (int i = 0; i < COLUMNs.length; i++) {
					row.getCell(i).setCellStyle(style);
				}

				// set styles
				row.getCell(2).setCellStyle(dateStyle);
				row.getCell(7).setCellStyle(dateStyle);
				row.getCell(9).setCellStyle(numberStyle);
			}

			// Autofit
			for (int col = 0; col < COLUMNs.length; col++) {
				sheet.autoSizeColumn(col);
			}
			
			sheet.setColumnWidth(2, 16);
			sheet.setColumnWidth(7, 16);
	
			
			
			//LAST DATA ROW
			Integer rows = tickets.size();
			Row lastRow = sheet.getRow(rows + 5);
			
//			DVConstraint  dvConstraint = DVConstraint.createFormulaListConstraint("'ReportDATA'!$A$4:$J$"+(rows-5)+"");
			
			//sheet autofilter
			sheet.setAutoFilter(CellRangeAddress.valueOf("'ReportDATA'!$A$5:$J$"+(rows-5)+""));
			
			//From..To
			sheet.addMergedRegion(new CellRangeAddress(
			        1, //first row (0-based)
			        1, //last row  (0-based)
			        7, //first column (0-based)
			        9  //last column  (0-based)
			));
			sheet.addMergedRegion(new CellRangeAddress(
			        2, //first row (0-based)
			        2, //last row  (0-based)
			        7, //first column (0-based)
			        9  //last column  (0-based)
			));
			
			
			//FROM..TO IN THE HEADER
			Row fromDateRow = sheet.createRow(1);
			fromDateRow.createCell(7).setCellValue("From date: " + fromDate.toLocalDate() );
			fromDateRow.getCell(7).setCellStyle(headerCellStyle);
			Row toDateRow = sheet.createRow(2);
			toDateRow.createCell(7).setCellValue("To date: " + toDate.toLocalDate());
			toDateRow.getCell(7).setCellStyle(headerCellStyle);
			
			//ADD FORMULAS
			Row sumRow = sheet.createRow(rows + 5);
			
			CellStyle totalCellStyle = workbook.createCellStyle();
			totalCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			totalCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			totalCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			totalCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			totalCellStyle.setBorderRight(BorderStyle.MEDIUM);
			totalCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			totalCellStyle.setBorderTop(BorderStyle.MEDIUM);
			totalCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			totalCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
			totalCellStyle.setFont(headerFont);
			
			
			sheet.addMergedRegion(new CellRangeAddress(
			        rows+5, //first row (0-based)
			        rows+5, //last row  (0-based)
			        0, //first column (0-based)
			        8  //last column  (0-based)
			));
			
			sumRow.createCell(9).setCellFormula("SUBTOTAL(9,$J$6:$J$"+(rows+5)+")");
			sumRow.createCell(0).setCellValue("TOTAL");
			
			sumRow.getCell(0).setCellStyle(headerCellStyle);
			sumRow.getCell(9).setCellStyle(totalCellStyle);
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}