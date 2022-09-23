package com.fpolydatn.core.daotao.excel;


import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
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
 * @author thepvph20110
 */
public class DtSinhVienTheoDotExport {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private List<DtSinhVienTheoDotResponse> ListSinhVien;


    private void headerLine(){
        xssfSheet = xssfWorkbook.createSheet("sheet");
        Row row = xssfSheet.createRow(0);

        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        createCell(row, 0,"stt",cellStyle);
        createCell(row, 1,"MSSV",cellStyle);
        createCell(row, 2,"Họ tên",cellStyle);
        createCell(row, 3,"Chuyên Ngành",cellStyle);
        createCell(row, 4,"Môn đăng ký",cellStyle);
        createCell(row, 5,"ID nhóm",cellStyle);
        createCell(row, 6,"Tên đề tài",cellStyle);
        createCell(row, 7,"GV hướng dẫn",cellStyle);
        createCell(row, 8,"Đợt đăng ký",cellStyle);
    }

    private void createCell(Row row, int countColum, Object value, CellStyle cellStyle){
        xssfSheet.autoSizeColumn(countColum);
        Cell cell = row.createCell(countColum);
        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if(value instanceof  Boolean){
            cell.setCellValue((Boolean) value);
        }else if(value instanceof  Long){
            cell.setCellValue((Long) value);
        }else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    private void writeDataLines(){

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        int rowCount = 1;
        ListSinhVien.stream().forEach(DtSinhVien -> {

            Row row = xssfSheet.createRow(DtSinhVien.getStt());
            int countColum = 0;

            createCell(row, countColum++, DtSinhVien.getStt(),style);
            createCell(row, countColum++, DtSinhVien.getMaSinhVien(),style);
            createCell(row, countColum++, DtSinhVien.getTenSinhVien(),style);
            createCell(row, countColum++, DtSinhVien.getTenChuyenNganh(),style);
            createCell(row, countColum++, DtSinhVien.getTenMonDatn(),style);
            createCell(row, countColum++, DtSinhVien.getMaNhom()== null ? "chưa có": DtSinhVien.getMaNhom(),style);
            createCell(row, countColum++, DtSinhVien.getTenDeTai()== null? "Chưa có": DtSinhVien.getTenDeTai(),style);
            createCell(row, countColum++, DtSinhVien.getTenGvhd() ==null ? "Chưa có" : DtSinhVien.getTenGvhd(),style);
            createCell(row, countColum++, DtSinhVien.getTenDotDangKy(),style);
        });
    }

    public DtSinhVienTheoDotExport(List<DtSinhVienTheoDotResponse> ListSinhVien) {
        this.ListSinhVien = ListSinhVien;
        xssfWorkbook= new XSSFWorkbook();
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
}
