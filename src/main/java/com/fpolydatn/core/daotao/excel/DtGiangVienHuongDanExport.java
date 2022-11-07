package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
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
 * @author Vinhnv
 */
public class DtGiangVienHuongDanExport {

    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<DtGiangVienHDResponse> listGiangVien;


    private void headerLine() {
        xssfSheet = xssfWorkbook.createSheet("sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Tên cơ sở", cellStyle);
        createCell(row, 2, "Tên đợt đăng ký", cellStyle);
        createCell(row, 3, "Tên giảng viên", cellStyle);
        createCell(row, 4, "Tên tài khoản", cellStyle);
        createCell(row, 5, "Số nhóm đang hướng dẫn", cellStyle);
        createCell(row, 6, "Số nhóm hướng dẫn tối đa", cellStyle);
        createCell(row, 7, "Số nhóm ", cellStyle);
        createCell(row, 8, "Số điện thoại", cellStyle);
        createCell(row, 9, "Email FPT", cellStyle);
        createCell(row, 10, "Email FE", cellStyle);

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

    private void writeDataLines() {

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        int rowCount = 1;
        listGiangVien.stream().forEach(giangVien -> {

            Row row = xssfSheet.createRow(giangVien.getSTT());
            int countColum = 0;

            createCell(row, countColum++, giangVien.getSTT(), style);
            createCell(row, countColum++, giangVien.getTenCoSo(), style);
            createCell(row, countColum++, giangVien.getTenDotDangKy(), style);
            createCell(row, countColum++, giangVien.getTenGvhd(), style);
            createCell(row, countColum++, giangVien.getTenTaiKhoan(), style);
            createCell(row, countColum++, giangVien.getSoNhomDangHuongDan(), style);
            createCell(row, countColum++, giangVien.getSoNhomHuongDanToiDa(), style);
            createCell(row, countColum++, giangVien.getSoNhomDangHuongDan() + "/" + giangVien.getSoNhomHuongDanToiDa(), style);
            createCell(row, countColum++, giangVien.getSoDienThoai(), style);
            createCell(row, countColum++, giangVien.getEmailFpt(), style);
            createCell(row, countColum++, giangVien.getEmailFe(), style);
        });
    }

    public DtGiangVienHuongDanExport(List<DtGiangVienHDResponse> listGiangVien) {
        this.listGiangVien = listGiangVien;
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

    private void headerLineMau() {
        xssfSheet = xssfWorkbook.createSheet("sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Tên cơ sở", cellStyle);
        createCell(row, 2, "Tên đợt đăng ký", cellStyle);
        createCell(row, 3, "Tên giảng viên", cellStyle);
        createCell(row, 4, "Tên tài khoản", cellStyle);
        createCell(row, 5, "Số nhóm hướng dẫn tối đa", cellStyle);
        createCell(row, 6, "Số điện thoại", cellStyle);
        createCell(row, 7, "Email FPT", cellStyle);
        createCell(row, 8, "Email FE", cellStyle);
    }

    public void exportMauImport(HttpServletResponse response) throws IOException {
        final long startTime = System.currentTimeMillis();
        headerLineMau();

        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);

        final long endTime = System.currentTimeMillis();

        xssfWorkbook.close();
        outputStream.close();
    }
}
