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
 * @author caodinh
 */
public class DtCanBoExportExcel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public DtCanBoExportExcel() {
        this.workbook = new XSSFWorkbook();
    }

    // create header Line
    private void headerLine() {
        sheet = workbook.createSheet("sheet");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Email FE", cellStyle);
        createCell(row, 2, "EMAIL FPT", cellStyle);
        createCell(row, 3, "SO DIEN THOAI", cellStyle);
        createCell(row, 4, "TEN CAN BO", cellStyle);
        createCell(row, 5, "TEN TAI KHOAN", cellStyle);
        createCell(row, 6, "VAI TRO", cellStyle);

    }

    private void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
        // TODO Auto-generated method stub
        sheet.autoSizeColumn(countColumn);
        Cell cell = row.createCell(countColumn);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    public void exportData(HttpServletResponse response) throws IOException {
        // calling method headerLine
        headerLine();
        // calling methods writedataline
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();

    }

}
