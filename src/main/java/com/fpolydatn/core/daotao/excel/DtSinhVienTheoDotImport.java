package com.fpolydatn.core.daotao.excel;


import com.fpolydatn.entity.SinhVien;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thepvph20110
 */
@Component
public class DtSinhVienTheoDotImport {


    public static List<SinhVien> importData(MultipartFile reapExcelDataFile) throws IOException {
        List<SinhVien> listSinhVien = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
           SinhVien sinhVien = new SinhVien();
            XSSFRow row = worksheet.getRow(i);
            sinhVien.setMaSinhVien(row.getCell(1).getStringCellValue());
            sinhVien.setTenSinhVien(row.getCell(2).getStringCellValue());
            sinhVien.setChuyenNganhId(row.getCell(3).getStringCellValue());
            sinhVien.setMonDatnId(row.getCell(4).getStringCellValue());
            sinhVien.setNhomId(row.getCell(5).getStringCellValue());
            sinhVien.setDotDangKyId(row.getCell(6).getStringCellValue());
            sinhVien.setEmail(row.getCell(7).getStringCellValue());
            sinhVien.setKhoa(row.getCell(8).getStringCellValue());
            sinhVien.setSoDienThoai(row.getCell(9).getStringCellValue());
            listSinhVien.add(sinhVien);
        }
        return listSinhVien;
    }

}
