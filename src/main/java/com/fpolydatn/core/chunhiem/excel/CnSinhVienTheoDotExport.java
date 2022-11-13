package com.fpolydatn.core.chunhiem.excel;

import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotExcel;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
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
        });
    }

    public CnSinhVienTheoDotExport(List<CnSinhVienTheoDotExcel> listSinhVien) {
        this.listSinhVien = listSinhVien;
        xssfWorkbook = new XSSFWorkbook();
    }

    public CnSinhVienTheoDotExport(){
    }

    public void employeeDetailReport(HttpServletResponse response, List<CnSinhVienTheoDotExcel> listSinhVien) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sinh viên theo đợt");

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
            cell2.setCellValue("Họ tên sinh viên");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Mã nhóm");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Chức vụ");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Tên đề tài");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Giảng viên hướng dẫn");
            cell6.setCellStyle(cellStyle);

            Cell cell7 = row.createCell(7);
            cell7.setCellValue("Đợt đăng ký");
            cell7.setCellStyle(cellStyle);


            //Set data
            int rowNum = 1;
            for (CnSinhVienTheoDotExcel sinhVien : listSinhVien) {
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

                Cell empMaNhomCell = empDataRow.createCell(3);
                empMaNhomCell.setCellStyle(cellStyle);
                empMaNhomCell.setCellValue(sinhVien.getMaNhom());

                Cell empChucVuCell = empDataRow.createCell(4);
                empChucVuCell.setCellStyle(cellStyle);
                empChucVuCell.setCellValue(sinhVien.getChucVu());

                Cell empTenDeTaiCell = empDataRow.createCell(5);
                empTenDeTaiCell.setCellStyle(cellStyle);
                empTenDeTaiCell.setCellValue(sinhVien.getTenDeTai() == null ? "Chưa có" : sinhVien.getTenDeTai());

                Cell empGVHDCell = empDataRow.createCell(6);
                empGVHDCell.setCellStyle(cellStyle);
                empGVHDCell.setCellValue(sinhVien.getGvhd());

                Cell empDotDangKyCell = empDataRow.createCell(7);
                empDotDangKyCell.setCellStyle(cellStyle);
                empDotDangKyCell.setCellValue(sinhVien.getTenDotDangKy());
            }

            //write output to response
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
