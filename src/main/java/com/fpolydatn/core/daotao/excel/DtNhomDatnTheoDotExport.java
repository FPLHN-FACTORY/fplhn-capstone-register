package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DtNhomDatnTheoDotExport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<DtNhomDatnTheoDotResponse> listNhomDatn;

    public DtNhomDatnTheoDotExport(List<DtNhomDatnTheoDotResponse> listNhomDatn) {
        this.listNhomDatn = listNhomDatn;
        xssfWorkbook = new XSSFWorkbook();
    }

    private void headerLine() {
        xssfSheet = xssfWorkbook.createSheet("nhomDatn");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);
        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Tên nhóm", cellStyle);
        createCell(row, 2, "Tên đề tài", cellStyle);
        createCell(row, 3, "Tên chuyên ngành", cellStyle);
        createCell(row, 4, "Số thành viên", cellStyle);
        createCell(row, 5, "Trưởng nhóm", cellStyle);
        createCell(row, 6, "Tên GVHD", cellStyle);
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
        } else if (value instanceof Short) {
            cell.setCellValue((Short) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    private void writeDataLines() {
        final long startTime = System.currentTimeMillis();

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);
        listNhomDatn.stream().forEach(xx -> {
            Row row = xssfSheet.createRow(xx.getStt());
            int countColum = 0;
            createCell(row, countColum++, xx.getStt(), style);
            createCell(row, countColum++, xx.getTenNhom(), style);
            createCell(row, countColum++, xx.getTenDeTai(), style);
            createCell(row, countColum++, xx.getTenChuyenNganh(), style);
            createCell(row, countColum++, xx.getCountSinhVien() + "/7", style);
            createCell(row, countColum++, xx.getMaSinhVien() + "-" + xx.getTenSinhVien(), style);
            createCell(row, countColum++, xx.getTenGvhd() + "-" + xx.getTenTaiKhoan(), style);
            createCell(row, countColum++, xx.getTrangThai() == 0 ? "Mới thành lập" : (xx.getTrangThai() == 1 ? "Đã liên hệ giảng viên" : (xx.getTrangThai() == 2 ? "Sẵn sàng phê duyệt" : (xx.getTrangThai() == 3 ? "Cần sửa" : (xx.getTrangThai() == 4 ? "Giảng viên đã chốt" : "Chủ nhiệm bộ môn đã chốt")))), style);
        });

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    public void exportData(HttpServletResponse response) throws IOException {
        final long startTime = System.currentTimeMillis();
        headerLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time end: " + (endTime - startTime));
        xssfWorkbook.close();
        outputStream.close();
    }

}

