package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienTheoDotRequest;
import org.apache.poi.xssf.usermodel.XSSFCell;
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


    public static List<DtCreateSinhVienTheoDotRequest> importData(MultipartFile reapExcelDataFile) throws IOException {
        List<DtCreateSinhVienTheoDotRequest> listSinhVien = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            DtCreateSinhVienTheoDotRequest sinhVien = new DtCreateSinhVienTheoDotRequest();
            XSSFRow row = worksheet.getRow(i);
            row.getCell(i).getCellTypeEnum();

            sinhVien.setMaSinhVien(String.valueOf(getCellValue(row.getCell(1))));
            sinhVien.setTenSinhVien(String.valueOf(getCellValue(row.getCell(2))));
            sinhVien.setSoDienThoai(String.valueOf(getCellValue(row.getCell(3))));
            sinhVien.setEmail(String.valueOf(getCellValue(row.getCell(4))));
            sinhVien.setKhoa(String.valueOf(getCellValue(row.getCell(5))));
            sinhVien.setMaNhom(String.valueOf(getCellValue(row.getCell(6))));
            sinhVien.setChuyenNganh(String.valueOf(getCellValue(row.getCell(7))));
            sinhVien.setDotDangKy(String.valueOf(getCellValue(row.getCell(8))));
            sinhVien.setMonDATN(String.valueOf(getCellValue(row.getCell(9))));


            listSinhVien.add(sinhVien);
        }
        return listSinhVien;
    }

    private static Object getCellValue(XSSFCell cell) {
        try {
            switch (cell.getCellTypeEnum()) {
                case NUMERIC -> {
                    return cell.getNumericCellValue();
                }
                case BOOLEAN -> {
                    return cell.getBooleanCellValue();
                }
                default -> {
                    return cell.getStringCellValue();
                }

            }
        }catch (Exception e){
            return "";
        }
    }

}
