package com.fpolydatn.core.chunhiem.excel;

import com.fpolydatn.core.chunhiem.model.response.CnNhomTheoDotExcel;
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
public class CnNhomTheoDotExport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<CnNhomTheoDotExcel> listNhom;

    public CnNhomTheoDotExport(List<CnNhomTheoDotExcel> listNhom) {
        this.listNhom = listNhom;
        xssfWorkbook = new XSSFWorkbook();
    }

    public CnNhomTheoDotExport() {
    }

    public void export(HttpServletResponse response, List<CnNhomTheoDotExcel> listNhom) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Nhóm theo đợt");

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
            cell1.setCellValue("Mã nhóm");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Tên đề tài");
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Chuyên ngành");
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Số thành viên");
            cell4.setCellStyle(cellStyle);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("Giảng viên hướng dẫn");
            cell5.setCellStyle(cellStyle);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue("Số nhóm đang hướng dẫn");
            cell6.setCellStyle(cellStyle);
            //Set data
            int rowNum = 1;
            for (CnNhomTheoDotExcel xx : listNhom) {
                Row empDataRow = sheet.createRow(rowNum++);

                Cell empSttCell = empDataRow.createCell(0);
                empSttCell.setCellStyle(cellStyle);
                empSttCell.setCellValue(xx.getStt());

                Cell empMaNhomCell = empDataRow.createCell(1);
                empMaNhomCell.setCellStyle(cellStyle);
                empMaNhomCell.setCellValue(xx.getMaNhom());

                Cell empTenDeTaiCell = empDataRow.createCell(2);
                empTenDeTaiCell.setCellStyle(cellStyle);
                empTenDeTaiCell.setCellValue(xx.getTenDeTai());

                Cell empChuyenNganhCell = empDataRow.createCell(3);
                empChuyenNganhCell.setCellStyle(cellStyle);
                empChuyenNganhCell.setCellValue(xx.getChuyenNganh());

                Cell empSoThanhVienCell = empDataRow.createCell(4);
                empSoThanhVienCell.setCellStyle(cellStyle);
                empSoThanhVienCell.setCellValue(xx.getSoThanhVien() + "/7");

                Cell empGVHDCell = empDataRow.createCell(5);
                empGVHDCell.setCellStyle(cellStyle);
                empGVHDCell.setCellValue(xx.getGvhd());

                Cell empSoNhomDangHuongDanCell = empDataRow.createCell(6);
                empSoNhomDangHuongDanCell.setCellStyle(cellStyle);
                empSoNhomDangHuongDanCell.setCellValue(xx.getSoNhomDangHuongDan());
            }

            //write output to response
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

