package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtNhomDatnSinhVienExportExcelReponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DtNhomDatnExportExcel {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;

    private List<DtNhomDatnSinhVienExportExcelReponse> listSv;

    public DtNhomDatnExportExcel(List<DtNhomDatnSinhVienExportExcelReponse> listSv) {
        xssfWorkbook = new XSSFWorkbook();
        this.listSv = listSv;
    }

    private void headerLine() {
        xssfSheet = xssfWorkbook.createSheet("Sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();

        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Mã sinh viên", cellStyle);
        createCell(row, 2, "Tên sinh viên", cellStyle);
        createCell(row, 3, "Email sinh viên", cellStyle);
        createCell(row, 4, "Trưởng nhóm", cellStyle);
        createCell(row, 5, "Mã nhóm", cellStyle);
        createCell(row, 6, "Môn đăng ký", cellStyle);
        createCell(row, 7, "Tên đề tài", cellStyle);
        createCell(row, 8, "Tên chuyên ngành", cellStyle);
        createCell(row, 9, "Tên GVHD", cellStyle);
        createCell(row, 10, "Tên tài khoản", cellStyle);
        createCell(row, 11, "Trạng thái", cellStyle);

        CellStyle cellDataStyle = xssfWorkbook.createCellStyle();
        XSSFFont fontData = xssfWorkbook.createFont();
        fontData.setFontHeight(11);
        cellDataStyle.setFont(fontData);
        cellDataStyle.setBorderTop(BorderStyle.THIN);
        cellDataStyle.setBorderBottom(BorderStyle.THIN);
        cellDataStyle.setBorderLeft(BorderStyle.THIN);
        cellDataStyle.setBorderRight(BorderStyle.THIN);
        cellDataStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < listSv.size(); i++) {
            DtNhomDatnSinhVienExportExcelReponse sv = listSv.get(i);
            Row row1 = xssfSheet.createRow(i + 1);
            createCell(row1, 0, i + 1, cellDataStyle);
            createCell(row1, 1, sv.getMaSinhVien(), cellDataStyle);
            createCell(row1, 2, sv.getTenSinhVien(), cellDataStyle);
            createCell(row1, 3, sv.getEmail(), cellDataStyle);
            createCell(row1, 4, sv.getId().equals(sv.getTruongNhomId()) ? "X" : "", cellDataStyle);
            createCell(row1, 5, sv.getMaNhom(), cellDataStyle);
            createCell(row1, 6, sv.getMonDatn(), cellDataStyle);
            createCell(row1, 7, sv.getTenDeTai(), cellDataStyle);
            createCell(row1, 8, sv.getChuyenNganh(), cellDataStyle);
            createCell(row1, 9, sv.getTenGvhd(), cellDataStyle);
            createCell(row1, 10, sv.getTenTaiKhoan(), cellDataStyle);
            createCell(row1, 11, sv.getTrangThai() == 0 ? "Mới thành lập" : (sv.getTrangThai() == 1 ? "Đã liên hệ giảng viên" :
                    (sv.getTrangThai() == 2 ? "Sẵn sàng phê duyệt" : (sv.getTrangThai() == 3 ? "Cần sửa" : (sv.getTrangThai() == 4 ? "Giảng viên đã chốt" : "Chủ nhiệm bộ môn đã chốt")))), cellDataStyle);
        }

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
