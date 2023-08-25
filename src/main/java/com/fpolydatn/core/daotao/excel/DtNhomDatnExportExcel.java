package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtNhomDatnExportResponse;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DtNhomDatnExportExcel {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;

    private List<DtNhomDatnSinhVienExportExcelReponse> listSv;
    private ConcurrentHashMap<String, DtNhomDatnExportResponse> map = new ConcurrentHashMap<>();

    public DtNhomDatnExportExcel(List<DtNhomDatnSinhVienExportExcelReponse> listSv, List<DtNhomDatnExportResponse> listNhom) {
        xssfWorkbook = new XSSFWorkbook();
        this.listSv = listSv;
        listNhom.parallelStream().forEach(el -> {
            map.put(el.getId(), el);
        });
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

        int index = 0;
        for (int i = 0; i < listSv.size(); i++) {
            DtNhomDatnSinhVienExportExcelReponse sv = listSv.get(i);
            if (!map.contains(sv.getNhomId())) {
                DtNhomDatnExportResponse nhomDatn = map.get(sv.getNhomId());
                if (nhomDatn != null) {
                    index ++;
                    Row row1 = xssfSheet.createRow(index);
                    row1.createCell(0).setCellValue(String.valueOf(index));
                    row1.createCell(1).setCellValue(sv.getMaSinhVien());
                    row1.createCell(2).setCellValue(sv.getTenSinhVien());
                    row1.createCell(3).setCellValue(sv.getEmail());
                    row1.createCell(4).setCellValue(sv.getId().equals(nhomDatn.getTruongNhomId()) ? "X" : "");
                    row1.createCell(5).setCellValue(nhomDatn.getMaNhom());
                    row1.createCell(6).setCellValue(sv.getTenMonDatn());
                    row1.createCell(7).setCellValue(nhomDatn.getTenDeTai());
                    row1.createCell(8).setCellValue(sv.getChuyenNganh());
                    row1.createCell(9).setCellValue(nhomDatn.getTenGvhd());
                    row1.createCell(10).setCellValue(nhomDatn.getTenTaiKhoan());
                    row1.createCell(11).setCellValue(nhomDatn.getTrangThai() == 0 ? "Mới thành lập" : (nhomDatn.getTrangThai() == 1 ? "Chủ nhiệm bộ môn đã xác nhận" :
                            (nhomDatn.getTrangThai() == 2 ? "Giảng viên đã liên hệ" : (nhomDatn.getTrangThai() == 3 ? "Cần sửa" : (nhomDatn.getTrangThai() == 4 ? "Giảng viên đã chốt" : "Chủ nhiệm bộ môn đã chốt")))));

                }
            }
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
