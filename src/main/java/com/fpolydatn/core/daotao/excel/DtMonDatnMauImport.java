package com.fpolydatn.core.daotao.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huynq
 */
public class DtMonDatnMauImport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;

    public DtMonDatnMauImport() {
        xssfWorkbook = new XSSFWorkbook();
    }

    private void headerLine() {
        xssfSheet = xssfWorkbook.createSheet("Sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);
        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Mã môn", cellStyle);
        createCell(row, 2, "Tên môn", cellStyle);
        createCell(row, 3, "Tên chuyên ngành", cellStyle);
        createCell(row, 4, "Mã nhóm môn", cellStyle);

        CellStyle cellDataStyle = xssfWorkbook.createCellStyle();
        XSSFFont fontData = xssfWorkbook.createFont();
        fontData.setFontHeight(11);
        cellDataStyle.setFont(fontData);

        Row row1 = xssfSheet.createRow(1);
        createCell(row1, 0, 1, cellDataStyle);
        createCell(row1, 1, "PRO2112", cellDataStyle);
        createCell(row1, 2, "Dự án tốt nghiệp (UDPM-Spring Boot)", cellDataStyle);
        createCell(row1, 3, "Công nghệ thông tin", cellDataStyle);
        createCell(row1, 4, "PRO", cellDataStyle);

        Row row2 = xssfSheet.createRow(2);
        createCell(row2, 0, 2, cellDataStyle);
        createCell(row2, 1, "PRO2113", cellDataStyle);
        createCell(row2, 2, "Dự án tốt nghiệp C#", cellDataStyle);
        createCell(row2, 3, "Công nghệ thông tin", cellDataStyle);
        createCell(row2, 4, "PRO", cellDataStyle);
    }

    private void createCell(Row row, int countColum, Object value, CellStyle cellStyle) {
        xssfSheet.autoSizeColumn(countColum);
        Cell cell = row.createCell(countColum);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Short) {
            cell.setCellValue((Short) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    public void exportData(HttpServletResponse response) throws IOException {
        headerLine();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();
        outputStream.close();
    }

}
