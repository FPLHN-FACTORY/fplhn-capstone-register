package com.fpolydatn.core.daotao.excel;


import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author thepvph20110
 */
public class DtSinhVienTheoDotExport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<DtSinhVienTheoDotResponse> ListSinhVien;

    public DtSinhVienTheoDotExport(List<DtSinhVienTheoDotResponse> ListSinhVien) {
        this.ListSinhVien = ListSinhVien;
        xssfWorkbook = new XSSFWorkbook();
    }

    public DtSinhVienTheoDotExport() {
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

        ListSinhVien.stream().forEach(DtSinhVien -> {

            Row row = xssfSheet.createRow(DtSinhVien.getStt());
            int countColum = 0;

            createCell(row, countColum++, DtSinhVien.getStt(), style);
            createCell(row, countColum++, DtSinhVien.getMaSinhVien(), style);
            createCell(row, countColum++, DtSinhVien.getTenSinhVien(), style);
            createCell(row, countColum++, DtSinhVien.getSoDienThoai(), style);
            createCell(row, countColum++, DtSinhVien.getEmail(), style);
            createCell(row, countColum++, DtSinhVien.getTrangThai() == 0 ? "Đủ điều kiện" : "Không đủ điều kiện", style);
            createCell(row, countColum++, DtSinhVien.getKhoa(), style);
            createCell(row, countColum++, DtSinhVien.getTenMonDatn() == null ? "Chưa có" : DtSinhVien.getTenMonDatn(), style);
            createCell(row, countColum++, DtSinhVien.getMaNhom() == null ? "chưa có" : DtSinhVien.getMaNhom(), style);
            createCell(row, countColum++, DtSinhVien.getTenDeTai() == null ? "Chưa có" : DtSinhVien.getTenDeTai(), style);
            createCell(row, countColum++, DtSinhVien.getSoThanhVien() == null ? 0 : DtSinhVien.getSoThanhVien(), style);
            createCell(row, countColum++, DtSinhVien.getTenGvhd() == null ? "Chưa có" : DtSinhVien.getTenGvhd(), style);
            createCell(row, countColum++, DtSinhVien.getTenDotDangKy(), style);
            createCell(row, countColum++, DtSinhVien.getTenChuyenNganh(), style);
        });
    }


    public void headerLineMau(HttpServletResponse response) throws Exception {
        xssfSheet = xssfWorkbook.createSheet("Danh sách sinh viên");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "MSSV", cellStyle);
        createCell(row, 2, "Họ tên", cellStyle);
        createCell(row, 3, "Số điện thoại", cellStyle);
        createCell(row, 4, "Email", cellStyle);
        createCell(row, 5, "Khóa", cellStyle);
        createCell(row, 6, "Tên chuyên ngành", cellStyle);
        createCell(row, 7, "Đợt đăng ký", cellStyle);
        createCell(row, 8, "Mã môn chương trình", cellStyle);
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();
        outputStream.close();
    }

    public void employeeDetailReport(HttpServletResponse response, List<DtSinhVienTheoDotResponse> listDtSinhVien) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách sinh viên");

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
            cell.setCellValue("stt");
            cell.setCellStyle(cellStyle);


            Cell cell1 = row.createCell(1);
            cell1.setCellValue("MSSV");
            cell1.setCellStyle(cellStyle);


            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Họ tên");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Số điện thoại");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Email");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Trạng thái");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Khóa");
            cell6.setCellStyle(cellStyle);

            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Môn đăng ký");
            cell7.setCellStyle(cellStyle);

            Cell cell8 = row.createCell(8);
            cell8.setCellValue("Mã nhóm");
            cell8.setCellStyle(cellStyle);

            Cell cell9 = row.createCell(9);
            cell9.setCellValue("Tên đề tài");
            cell9.setCellStyle(cellStyle);

            Cell cell10 = row.createCell(10);
            cell10.setCellValue("Số thành viên");
            cell10.setCellStyle(cellStyle);

            Cell cell11 = row.createCell(11);
            cell11.setCellValue("GV hướng dẫn");
            cell11.setCellStyle(cellStyle);

            Cell cell12 = row.createCell(12);
            cell12.setCellValue("Đợt đăng ký");
            cell12.setCellStyle(cellStyle);

            Cell cell13 = row.createCell(13);
            cell13.setCellValue("Chuyên Ngành");
            cell13.setCellStyle(cellStyle);

            //Set data
            int rowNum = 1;
            for (DtSinhVienTheoDotResponse DtSinhVien : listDtSinhVien) {
                Row empDataRow = sheet.createRow(rowNum++);


                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(DtSinhVien.getStt());

                Cell empMaSinhVienCell = empDataRow.createCell(1);
                empMaSinhVienCell.setCellStyle(cellStyle);
                empMaSinhVienCell.setCellValue(DtSinhVien.getMaSinhVien());

                Cell empTenSinhVienCell = empDataRow.createCell(2);
                empTenSinhVienCell.setCellStyle(cellStyle);
                empTenSinhVienCell.setCellValue(DtSinhVien.getTenSinhVien());

                Cell empSoDienThoaiCell = empDataRow.createCell(3);
                empSoDienThoaiCell.setCellStyle(cellStyle);
                empSoDienThoaiCell.setCellValue(DtSinhVien.getSoDienThoai());

                Cell empEmailCell = empDataRow.createCell(4);
                empEmailCell.setCellStyle(cellStyle);
                empEmailCell.setCellValue(DtSinhVien.getEmail());

                Cell empTrangThaiCell = empDataRow.createCell(5);
                empTrangThaiCell.setCellStyle(cellStyle);
                empTrangThaiCell.setCellValue(DtSinhVien.getTrangThai() == 0 ? "Đủ điều kiện" : "Không đủ điều kiện");

                Cell khoaCell = empDataRow.createCell(6);
                khoaCell.setCellStyle(cellStyle);
                khoaCell.setCellValue(DtSinhVien.getKhoa());

                Cell svTenMonDatnCell = empDataRow.createCell(7);
                svTenMonDatnCell.setCellStyle(cellStyle);
                svTenMonDatnCell.setCellValue(DtSinhVien.getTenMonDatn() == null ? "Chưa có" : DtSinhVien.getTenMonDatn());

                Cell empMaNhomCell = empDataRow.createCell(8);
                empMaNhomCell.setCellStyle(cellStyle);
                empMaNhomCell.setCellValue(DtSinhVien.getMaNhom() == null ? "chưa có" : DtSinhVien.getMaNhom());

                Cell empTenDeTaiCell = empDataRow.createCell(9);
                empTenDeTaiCell.setCellStyle(cellStyle);
                empTenDeTaiCell.setCellValue(DtSinhVien.getTenDeTai() == null ? "Chưa có" : DtSinhVien.getTenDeTai());

                Cell empSoThanhVienCell = empDataRow.createCell(10);
                empSoThanhVienCell.setCellStyle(cellStyle);
                empSoThanhVienCell.setCellValue(DtSinhVien.getSoThanhVien() == null ? "Chưa tham gia nhóm" : DtSinhVien.getSoThanhVien() + "/7");

                Cell empTenGvhdCell = empDataRow.createCell(11);
                empTenGvhdCell.setCellStyle(cellStyle);
                empTenGvhdCell.setCellValue(DtSinhVien.getTenGvhd() == null ? "Chưa có" : DtSinhVien.getTenGvhd());

                Cell empTenDotDangKyCell = empDataRow.createCell(12);
                empTenDotDangKyCell.setCellStyle(cellStyle);
                empTenDotDangKyCell.setCellValue(DtSinhVien.getTenDotDangKy());

                Cell empTenChuyenNganhCell = empDataRow.createCell(13);
                empTenChuyenNganhCell.setCellStyle(cellStyle);
                empTenChuyenNganhCell.setCellValue(DtSinhVien.getTenChuyenNganh());
            }

            //write output to response
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
