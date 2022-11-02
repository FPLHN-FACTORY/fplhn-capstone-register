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
        createCell(row, 4, "Mã cơ sở", cellStyle);
        createCell(row, 5, "Mã nhóm môn", cellStyle);
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
        final long startTime = System.currentTimeMillis();
        headerLine();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time end: " + (endTime - startTime));
        xssfWorkbook.close();
        outputStream.close();
    }

}
