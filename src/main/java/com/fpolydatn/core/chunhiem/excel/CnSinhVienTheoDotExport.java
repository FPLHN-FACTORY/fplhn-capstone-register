package com.fpolydatn.core.chunhiem.excel;

import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author thangncph26123
 */
public class CnSinhVienTheoDotExport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<CnSinhVienTheoDotExcel> listSinhVien;


    private void headerLine() {
        xssfSheet = xssfWorkbook.createSheet("sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Mã sinh viên", cellStyle);
        createCell(row, 2, "Tên sinh viên", cellStyle);
        createCell(row, 3, "Mã nhóm", cellStyle);
        createCell(row, 4, "Chức vụ", cellStyle);
        createCell(row, 5, "Tên đề tài", cellStyle);
        createCell(row, 6, "Giảng viên hướng dẫn", cellStyle);
        createCell(row, 7, "Đợt đăng ký", cellStyle);
        createCell(row, 8, "Trạng thái", cellStyle);
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
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    private void writeDataLines() {

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        listSinhVien.stream().forEach(sinhVien -> {

            Row row = xssfSheet.createRow(sinhVien.getStt());
            int countColum = 0;

            createCell(row, countColum++, sinhVien.getStt(), style);
            createCell(row, countColum++, sinhVien.getMaSinhVien(), style);
            createCell(row, countColum++, sinhVien.getTenSinhVien(), style);
            createCell(row, countColum++, sinhVien.getMaNhom(), style);
            createCell(row, countColum++, sinhVien.getChucVu(), style);
            createCell(row, countColum++, sinhVien.getTenDeTai() == null ? "Chưa có" : sinhVien.getTenDeTai(), style);
            createCell(row, countColum++, sinhVien.getGvhd(), style);
            createCell(row, countColum++, sinhVien.getTenDotDangKy(), style);
            createCell(row, countColum++, sinhVien.getTrangThai(), style);
        });
    }

    public CnSinhVienTheoDotExport(List<CnSinhVienTheoDotExcel> listSinhVien) {
        this.listSinhVien = listSinhVien;
        xssfWorkbook = new XSSFWorkbook();
    }

    public void exportData(HttpServletResponse response) throws IOException {
        final long startTime = System.currentTimeMillis();
        headerLine();

        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);

        final long endTime = System.currentTimeMillis();

        xssfWorkbook.close();
        outputStream.close();
    }

}
