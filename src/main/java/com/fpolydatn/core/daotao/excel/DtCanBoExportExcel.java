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
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Email FE", cellStyle);
        createCell(row, 2, "EMAIL FPT", cellStyle);
        createCell(row, 3, "SO DIEN THOAI", cellStyle);
        createCell(row, 4, "TEN CAN BO", cellStyle);
        createCell(row, 5, "TEN TAI KHOAN", cellStyle);
        createCell(row, 6, "VAI TRO", cellStyle);

        CellStyle cellStyleRow = workbook.createCellStyle();
        XSSFFont fontRow = workbook.createFont();
        fontRow.setFontHeight(10);
        cellStyle.setFont(fontRow);

        Row rowFakeDataOne = sheet.createRow(1);

        createCell(rowFakeDataOne, 0, "1",cellStyleRow);
        createCell(rowFakeDataOne, 1, "nguyenvanxuan@fe.edu.vn", cellStyleRow);
        createCell(rowFakeDataOne, 2, "nguyenvanxuan@fpt.edu.vn", cellStyleRow);
        createCell(rowFakeDataOne, 3, "0875864758", cellStyleRow);
        createCell(rowFakeDataOne, 4, "Nguyễn Văn Xuân", cellStyleRow);
        createCell(rowFakeDataOne, 5, "xuannv", cellStyleRow);
        createCell(rowFakeDataOne, 6, "DAO_TAO", cellStyleRow);

        Row rowFakeDataTwo = sheet.createRow(2);

        createCell(rowFakeDataTwo, 0, "2", cellStyleRow);
        createCell(rowFakeDataTwo, 1, "trantrungquan@fe.edu.vn", cellStyleRow);
        createCell(rowFakeDataTwo, 2, "trantrungquan@fpt.edu.vn", cellStyleRow);
        createCell(rowFakeDataTwo, 3, "0987654321", cellStyleRow);
        createCell(rowFakeDataTwo, 4, "Trần Trung Quân", cellStyleRow);
        createCell(rowFakeDataTwo, 5, "quantt", cellStyleRow);
        createCell(rowFakeDataTwo, 6, "CHU_NHIEM_BO_MON", cellStyleRow);

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
