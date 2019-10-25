/*
 * package com.cbs.controllers;
 * 
 * import org.apache.poi.ss.usermodel.Sheet; import
 * org.apache.poi.ss.usermodel.Cell; import
 * org.apache.poi.ss.usermodel.CellType; import
 * org.apache.poi.ss.usermodel.FormulaEvaluator; import
 * org.apache.poi.ss.usermodel.Row; import org.apache.poi.ss.usermodel.Workbook;
 * import org.apache.poi.ss.usermodel.WorkbookFactory; import
 * org.apache.poi.ss.util.WorkbookUtil; import
 * org.apache.poi.xssf.usermodel.XSSFWorkbook; import
 * org.springframework.stereotype.Controller;
 * 
 * @Controller public class ReportController { public String createExcel() {
 * 
 * Workbook wb2007 = new XSSFWorkbook();
 * 
 * // Tạo sheet với tên MySheetName String safeName =
 * WorkbookUtil.createSafeSheetName("MySheetName"); Sheet sheet =
 * wb2007.createSheet(safeName); //row int rowIndex =0,columnIndex =0; Row row =
 * sheet.createRow(rowIndex); //get row // Row row = sheet.getRow(1);
 * 
 * Cell cell = row.createCell(columnIndex); cell = row.createCell(columnIndex,
 * CellType.FORMULA);
 * 
 * cell.setCellValue("value"); cell.setCellFormula("SUM(E2:E6)");
 * 
 * 
 * // boolean value = cell.getBooleanCellValue(); // double value =
 * cell.getNumericCellValue(); String value = cell.getStringCellValue();
 * 
 * // Get Formula value Workbook workbook = cell.getSheet().getWorkbook();
 * FormulaEvaluator evaluator =
 * workbook.getCreationHelper().createFormulaEvaluator(); double value =
 * evaluator.evaluate(cell).getNumberValue();
 * 
 * //Tự động điều chỉnh độ rộng của cột vừa đủ để hiển thị đầy đủ nội dung for
 * (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
 * sheet.autoSizeColumn(columnIndex); }
 * 
 * 
 * // Tạo định dạng: font Times New Roman, in đậm, font-size 14, chữ màu trắng
 * Font font = sheet.getWorkbook().createFont();
 * font.setFontName("Times New Roman"); font.setBold(true);
 * font.setFontHeightInPoints((short) 14); // font size
 * font.setColor(IndexedColors.WHITE.getIndex()); // text color
 * 
 * // Tạo cell style áp dụng font ở trên // Sử dụng màu nền xanh (Blue), định
 * dạng border dưới CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
 * cellStyle.setFont(font);
 * cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
 * cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
 * cellStyle.setBorderBottom(BorderStyle.THIN);
 * 
 * // Áp dụng định dạng CellStyle cho một Cell cell.setCellStyle(cellStyle);
 * 
 * 
 * / Tạo format số short format =
 * (short)BuiltinFormats.getBuiltinFormat("#,##0");
 * 
 * // hoặc DataFormat df = workbook.createDataFormat(); short format =
 * df.getFormat("#,##0");
 * 
 * // Tạo CellStyle với format số CellStyle cellStyle =
 * workbook.createCellStyle(); cellStyle.setDataFormat(format);
 * 
 * // Áp dụng định dạng CellStyle cho một Cell cell.setCellStyle(cellStyle);
 * return ""; } }
 */