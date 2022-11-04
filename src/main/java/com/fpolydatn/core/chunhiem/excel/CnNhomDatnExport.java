package com.fpolydatn.core.chunhiem.excel;

import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
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
public class CnNhomDatnExport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<CnNhomDatnResponse> listNhomDatn;


    private void headerLine() {
        xssfSheet = xssfWorkbook.createSheet("sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Mã nhóm", cellStyle);
        createCell(row, 2, "Tên đề tài", cellStyle);
        createCell(row, 3, "Mã môn", cellStyle);
        createCell(row, 4, "SL thành viên", cellStyle);
        createCell(row, 5, "Giảng viên hướng dẫn", cellStyle);
        createCell(row, 6, "Đợt đăng ký", cellStyle);
        createCell(row, 7, "Trạng thái", cellStyle);
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

        listNhomDatn.stream().forEach(nhomDatn -> {

            Row row = xssfSheet.createRow(nhomDatn.getStt());
            int countColum = 0;

            createCell(row, countColum++, nhomDatn.getStt() == null ? "Chưa có" : nhomDatn.getStt(), style);
            createCell(row, countColum++, nhomDatn.getMaNhom() == null ? "Chưa có" : nhomDatn.getMaNhom(), style);
            createCell(row, countColum++, nhomDatn.getTenDeTai() == null ? "Chưa có" : nhomDatn.getTenDeTai(), style);
            createCell(row, countColum++, nhomDatn.getMaMonDatn() == null ? "Chưa có" : nhomDatn.getMaMonDatn(), style);
            createCell(row, countColum++, nhomDatn.getTotal(), style);
            createCell(row, countColum++, nhomDatn.getTenTaiKhoan() == null ? "Chưa có" : nhomDatn.getTenTaiKhoan() + " - " + nhomDatn.getTenGvhd(), style);
            createCell(row, countColum++, nhomDatn.getTenDotDangKy(), style);
            createCell(row, countColum++, nhomDatn.getTrangThai() == 0 ? "Mới thành lập" : (nhomDatn.getTrangThai() == 1 ? "Chủ nhiệm bộ môn đã xác nhận" : (nhomDatn.getTrangThai() == 2 ? "Giảng viên đã liên hệ" : (nhomDatn.getTrangThai() == 3 ? "Cần sửa" : (nhomDatn.getTrangThai() == 4 ? "Giảng viên đã chốt" : "Chủ nhiệm bộ môn đã chốt")))), style);
        });
    }

    public CnNhomDatnExport(List<CnNhomDatnResponse> listNhomDatn) {
        this.listNhomDatn = listNhomDatn;
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
        createCell(row, 1, "Mã nhóm", cellStyle);
        createCell(row, 2, "Tên đề tài", cellStyle);
        createCell(row, 3, "Mã môn", cellStyle);
        createCell(row, 4, "SL thành viên", cellStyle);
        createCell(row, 5, "Giảng viên hướng dẫn", cellStyle);
        createCell(row, 6, "Đợt đăng ký", cellStyle);
        createCell(row, 7, "Trạng thái", cellStyle);
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
