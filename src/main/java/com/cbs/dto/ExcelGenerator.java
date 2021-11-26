package com.cbs.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
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
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

	public static ByteArrayInputStream ticketsToExcel(List<TicketReportDTO> tickets, LocalDate fromDate,
			LocalDate toDate) throws IOException {
		String[] COLUMNs = { "STT", "Mã KH", "Đơn hàng", "Thời gian mua", "Tỉnh", "Cụm rạp", "Phim", "Định dạng phim",
				"Suất chiếu", "Ghế VIP", "Giá vé" };
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
			font.setFontHeightInPoints((short) 24);
			font.setFontName("Stencil Std");
			font.setBold(true);
			CellStyle headerRowStyle = workbook.createCellStyle();
			headerRowStyle.setFont(font);
			headerRowStyle.setAlignment(HorizontalAlignment.CENTER);

			// sheet header
			sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					10 // last column (0-based)
			));

			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("BÁO CÁO DOANH THU");
			headerRow.getCell(0).setCellStyle(headerRowStyle);

			// column header
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 13);
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

			int rowIdx = 6;
			// LocalDateTime fromDate = tickets.stream().findFirst().get().getOrderTime();
			// LocalDateTime toDate = fromDate;
			for (TicketReportDTO ticket : tickets) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(rowIdx - 6);
				row.createCell(1).setCellValue(ticket.getMemberId());
				row.createCell(2).setCellValue(ticket.getOrderId());
				row.createCell(3).setCellValue(ticket.getOrderTime());
				row.createCell(4).setCellValue(ticket.getProvince());
				row.createCell(5).setCellValue(ticket.getCinema());
				row.createCell(6).setCellValue(ticket.getMovie());
				row.createCell(7).setCellValue(ticket.getFormat());
				row.createCell(8).setCellValue(ticket.getSessionTime());
				row.createCell(9).setCellValue(ticket.getSeatType());
				row.createCell(10).setCellValue(ticket.getPrice());

				// border
				for (int i = 0; i < COLUMNs.length; i++) {
					row.getCell(i).setCellStyle(style);
				}

				// set styles
				row.getCell(3).setCellStyle(dateStyle);
				row.getCell(8).setCellStyle(dateStyle);
				row.getCell(10).setCellStyle(numberStyle);
			}

			// LAST DATA ROW
			Integer rows = tickets.size();
			Row lastRow = sheet.getRow(rows + 6);

//			DVConstraint  dvConstraint = DVConstraint.createFormulaListConstraint("'ReportDATA'!$A$4:$J$"+(rows-5)+"");

			// sheet autofilter
			sheet.setAutoFilter(CellRangeAddress.valueOf("'ReportDATA'!$A$6:$K$" + (rows + 6) + ""));

			// From..To
			Row fromDateRow = sheet.createRow(1);
			fromDateRow.createCell(8).setCellValue("Từ ngày: " + fromDate);
			
			Row toDateRow = sheet.createRow(2);
			toDateRow.createCell(8).setCellValue("Đến ngày: " + toDate);
			
			
			CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1, 1, 8, 10);
			sheet.addMergedRegion(cellRangeAddress1);
			RegionUtil.setBorderTop(BorderStyle.MEDIUM, cellRangeAddress1, sheet);
			RegionUtil.setBorderLeft(BorderStyle.MEDIUM, cellRangeAddress1, sheet);
			RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellRangeAddress1, sheet);
			RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellRangeAddress1, sheet);
			fromDateRow.getCell(8).setCellStyle(headerCellStyle);
			
			CellRangeAddress cellRangeAddress2 = new CellRangeAddress(2, 2, 8, 10);
			sheet.addMergedRegion(cellRangeAddress2);
			RegionUtil.setBorderTop(BorderStyle.MEDIUM, cellRangeAddress2, sheet);
			RegionUtil.setBorderLeft(BorderStyle.MEDIUM, cellRangeAddress2, sheet);
			RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellRangeAddress2, sheet);
			RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellRangeAddress2, sheet);
			toDateRow.getCell(8).setCellStyle(headerCellStyle);
			


			// ADD FORMULAS
			if (tickets.size() != 0) {
				Row sumRow = sheet.createRow(rows + 6);

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

				CellRangeAddress cellRangeAddress3 = new CellRangeAddress(rows + 6, rows + 6, 0, 9);
				sheet.addMergedRegion(cellRangeAddress3);
				sumRow.createCell(0).setCellValue("TỔNG TIỀN");
				RegionUtil.setBorderTop(BorderStyle.MEDIUM, cellRangeAddress3, sheet);
				RegionUtil.setBorderLeft(BorderStyle.MEDIUM, cellRangeAddress3, sheet);
				RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellRangeAddress3, sheet);
				RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellRangeAddress3, sheet);

				sumRow.createCell(10).setCellFormula("SUBTOTAL(9,$K$7:$K$" + (rows + 6) + ")");
				sumRow.createCell(0).setCellValue("TỔNG TIỀN");

				sumRow.getCell(0).setCellStyle(headerCellStyle);
				sumRow.getCell(10).setCellStyle(totalCellStyle);
			}

			// Row for Header
			Row columnHeaderRow = sheet.createRow(4);

			// Autofit
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = columnHeaderRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
				sheet.autoSizeColumn(col);
			}
			// set width /256
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(8, 5000);
			sheet.setColumnWidth(10, 3500);
			// signature
			Row signRow1 = sheet.createRow(rows + 8);
			signRow1.createCell(8).setCellValue("Nhân viên");

			sheet.addMergedRegion(new CellRangeAddress(rows + 8, // first row (0-based)
					rows + 8, // last row (0-based)
					8, // first column (0-based)
					10 // last column (0-based)
			));

			sheet.createRow(rows + 9).createCell(8).setCellValue("(Ký,ghi họ tên");
			sheet.addMergedRegion(new CellRangeAddress(rows + 9, // first row (0-based)
					rows + 9, // last row (0-based)
					8, // first column (0-based)
					10 // last column (0-based)
			));

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}