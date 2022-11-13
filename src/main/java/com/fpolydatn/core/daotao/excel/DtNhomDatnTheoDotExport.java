package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DtNhomDatnTheoDotExport {
    public void exportData(HttpServletResponse response, List<DtNhomDatnTheoDotResponse> listNhomDatn) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Nhóm DATN");
            CellStyle cellStyle = workbook.createCellStyle();
            //set border to table
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            // Header
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("STT");
            cell.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Mã nhóm");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Môn đăng ký");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Tên đề tài");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Tên chuyên nghành");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Số thành viên");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Tên GVHD");
            cell6.setCellStyle(cellStyle);

            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Trạng thái");

            //Set data
            int rowNum = 1;
            for (DtNhomDatnTheoDotResponse dtNhomDatn : listNhomDatn) {
                Row empDataRow = sheet.createRow(rowNum++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(dtNhomDatn.getStt());

                Cell empMaNhomCell = empDataRow.createCell(1);
                empMaNhomCell.setCellStyle(cellStyle);
                empMaNhomCell.setCellValue(dtNhomDatn.getMaNhom());

                Cell empMonDangKyCell = empDataRow.createCell(2);
                empMonDangKyCell.setCellStyle(cellStyle);
                empMonDangKyCell.setCellValue(dtNhomDatn.getTenMonDatn());

                Cell empTenDeTaiCell = empDataRow.createCell(3);
                empTenDeTaiCell.setCellStyle(cellStyle);
                empTenDeTaiCell.setCellValue(dtNhomDatn.getTenDeTai() == null ? "Chưa có" : dtNhomDatn.getTenDeTai());

                Cell empTenChuyenNganhCell = empDataRow.createCell(4);
                empTenChuyenNganhCell.setCellStyle(cellStyle);
                empTenChuyenNganhCell.setCellValue(dtNhomDatn.getTenChuyenNganh());

                Cell empSoThanhVienCell = empDataRow.createCell(5);
                empSoThanhVienCell.setCellStyle(cellStyle);
                empSoThanhVienCell.setCellValue(dtNhomDatn.getCountSinhVien() + "/7");

                Cell empTenGvhdCell = empDataRow.createCell(6);
                empTenGvhdCell.setCellStyle(cellStyle);
                empTenGvhdCell.setCellValue(dtNhomDatn.getTenGvhd() == null ? "Chưa có" : dtNhomDatn.getTenGvhd());

                Cell empTrangThaiCell = empDataRow.createCell(7);
                empTrangThaiCell.setCellStyle(cellStyle);
                empTrangThaiCell.setCellValue(dtNhomDatn.getTrangThai() == 0 ? "Mới thành lập" : (dtNhomDatn.getTrangThai() == 1 ? "Đã liên hệ giảng viên" :
                        (dtNhomDatn.getTrangThai() == 2 ? "Sẵn sàng phê duyệt" : (dtNhomDatn.getTrangThai() == 3 ? "Cần sửa" : (dtNhomDatn.getTrangThai() == 4 ? "Giảng viên đã chốt" : "Chủ nhiệm bộ môn đã chốt")))));
            }
            //write output to response
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

