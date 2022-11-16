package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Vinhnv
 */
public class DtGiangVienHuongDanExport {

    public void exportMauImport(HttpServletResponse response) throws IOException {
        final long startTime = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách giảng viên");

        CellStyle cellStyle = workbook.createCellStyle();

        //set border to table
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // Header
        String[] dataRow = {"STT" , "Tên giảng viên" , "Tên tài khoản" , "Môn học ",
                "Số nhóm hướng dẫn tối đa" , "Số điện thoại" , "Email FPT" , "Email Fe"};
        int sizeDataRow = dataRow.length;
        Row row = sheet.createRow(0);
        for (int i = 0; i < sizeDataRow; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(dataRow[i]);
            cell.setCellStyle(cellStyle);
        }
        workbook.write(response.getOutputStream());
        final long endTime = System.currentTimeMillis();
    }

    public void employeeDetailReport(HttpServletResponse response, List<DtGiangVienHDResponse> list) {
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
            String[] dataRow = {"STT" , "Tên cơ sở" , "Tên đợt đăng ký" , "Tên giảng viên" , "Tên tài khoản" , "Số nhóm đang hướng dẫn" ,
                    "Số nhóm hướng dẫn tối đa" , "Số nhóm" , "Số điện thoại" , "Email FPT" , "Email Fe"};
            int sizeDataRow = dataRow.length;
            Row row = sheet.createRow(0);
            for (int i = 0; i < sizeDataRow; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(dataRow[i]);
                cell.setCellStyle(cellStyle);
            }

            //Set data
            int rowNum = 1;
            for (DtGiangVienHDResponse giangVien : list) {
                Row empDataRow = sheet.createRow(rowNum++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellValue(giangVien.getSTT());

                Cell tenCoSo = empDataRow.createCell(1);
                tenCoSo.setCellValue(giangVien.getTenCoSo());

                Cell empTenSinhVienCell = empDataRow.createCell(2);
                empTenSinhVienCell.setCellValue(giangVien.getTenDotDangKy());

                Cell empSoDienThoaiCell = empDataRow.createCell(3);
                empSoDienThoaiCell.setCellValue(giangVien.getTenGvhd());

                Cell empEmailCell = empDataRow.createCell(4);
                empEmailCell.setCellValue(giangVien.getTenTaiKhoan());

                Cell empTrangThaiCell = empDataRow.createCell(5);
                empTrangThaiCell.setCellValue(giangVien.getSoNhomHuongDanToiDa());

                Cell khoaCell = empDataRow.createCell(6);
                khoaCell.setCellValue(giangVien.getSoNhomDangHuongDan());

                Cell svTenMonDatnCell = empDataRow.createCell(7);
                svTenMonDatnCell.setCellValue(giangVien.getSoNhomDangHuongDan() +"/"+ giangVien.getSoNhomHuongDanToiDa());

                Cell empMaNhomCell = empDataRow.createCell(8);
                empMaNhomCell.setCellValue(giangVien.getSoDienThoai());

                Cell empTenDeTaiCell = empDataRow.createCell(9);
                empTenDeTaiCell.setCellValue(giangVien.getEmailFpt());

                Cell empSoThanhVienCell = empDataRow.createCell(10);
                empSoThanhVienCell.setCellValue(giangVien.getEmailFe());

            }
            //write output to response
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
