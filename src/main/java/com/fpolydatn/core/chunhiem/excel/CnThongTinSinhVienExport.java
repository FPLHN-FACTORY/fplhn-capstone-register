package com.fpolydatn.core.chunhiem.excel;

import com.fpolydatn.core.chunhiem.model.response.CnThongTinSinhVienExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author thangncph26123
 */
public class CnThongTinSinhVienExport {

    public void exportThongTinSinhVien(HttpServletResponse response, List<CnThongTinSinhVienExcel> listSinhVien) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Thông tin sinh viên theo đợt");

            CellStyle cellStyle = workbook.createCellStyle();

            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setFontHeight(12);
            cellStyle.setFont(font);

            // Header
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("STT");
            cell.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Mã sinh viên");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Họ và tên");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Email");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Số điện thoại");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Khóa");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Chuyên ngành");
            cell6.setCellStyle(cellStyle);

            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Môn Datn");
            cell7.setCellStyle(cellStyle);

            Cell cell8 = row.createCell(8);
            cell8.setCellValue("Mã nhóm");
            cell8.setCellStyle(cellStyle);

            Cell cell9 = row.createCell(9);
            cell9.setCellValue("Đợt đăng ký");
            cell9.setCellStyle(cellStyle);

            //Set data
            int rowNum = 1;
            for (CnThongTinSinhVienExcel sinhVien : listSinhVien) {
                Row empDataRow = sheet.createRow(rowNum++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(sinhVien.getStt());

                Cell empMaSinhVienCell = empDataRow.createCell(1);
                empMaSinhVienCell.setCellStyle(cellStyle);
                empMaSinhVienCell.setCellValue(sinhVien.getMaSinhVien());

                Cell empTenSinhVienCell = empDataRow.createCell(2);
                empTenSinhVienCell.setCellStyle(cellStyle);
                empTenSinhVienCell.setCellValue(sinhVien.getTenSinhVien());

                Cell empEmailCell = empDataRow.createCell(3);
                empEmailCell.setCellStyle(cellStyle);
                empEmailCell.setCellValue(sinhVien.getEmail());

                Cell empSdtCell = empDataRow.createCell(4);
                empSdtCell.setCellStyle(cellStyle);
                empSdtCell.setCellValue(sinhVien.getSoDienThoai());

                Cell empKhoaCell = empDataRow.createCell(5);
                empKhoaCell.setCellStyle(cellStyle);
                empKhoaCell.setCellValue(sinhVien.getKhoa());

                Cell empChuyenNganhCell = empDataRow.createCell(6);
                empChuyenNganhCell.setCellStyle(cellStyle);
                empChuyenNganhCell.setCellValue(sinhVien.getTenChuyenNganh());

                Cell empMonDatnCell = empDataRow.createCell(7);
                empMonDatnCell.setCellStyle(cellStyle);
                empMonDatnCell.setCellValue(sinhVien.getMonDatn());

                Cell empMaNhomCell = empDataRow.createCell(8);
                empMaNhomCell.setCellStyle(cellStyle);
                empMaNhomCell.setCellValue(sinhVien.getMaNhom());

                Cell empDotDangKyCell = empDataRow.createCell(9);
                empDotDangKyCell.setCellStyle(cellStyle);
                empDotDangKyCell.setCellValue(sinhVien.getDotDangKy());
            }

            //write output to response
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
