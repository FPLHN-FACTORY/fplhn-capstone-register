package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienTheoDotRequest;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row :
                worksheet) {
            if (row.getRowNum()==0){
                continue;
            }
            DtCreateSinhVienTheoDotRequest sinhVien = new DtCreateSinhVienTheoDotRequest();
            sinhVien.setMaSinhVien(String.valueOf(getCellValue(row.getCell(1))).trim());
            sinhVien.setTenSinhVien(String.valueOf(getCellValue(row.getCell(2))).trim());
            sinhVien.setSoDienThoai(String.valueOf(getCellValue(row.getCell(3))).trim());
            sinhVien.setEmail(String.valueOf(getCellValue(row.getCell(4))).trim());
            sinhVien.setKhoa(String.valueOf(getCellValue(row.getCell(5))).trim());
//            sinhVien.setMaNhom(String.valueOf(getCellValue(row.getCell(6))).trim());
            sinhVien.setChuyenNganh(String.valueOf(getCellValue(row.getCell(6))).trim());
            sinhVien.setDotDangKy(String.valueOf(getCellValue(row.getCell(7))).trim());
            sinhVien.setMonDATN(String.valueOf(getCellValue(row.getCell(8))).trim());
            if (sinhVien.getTenSinhVien().isEmpty() && sinhVien.getMaSinhVien().isEmpty() &&
                    sinhVien.getEmail().isEmpty() &&
                    sinhVien.getChuyenNganh().isEmpty() &&
                    sinhVien.getDotDangKy().isEmpty() &&
                    sinhVien.getMonDATN().isEmpty()) {
                continue;
            }
            listSinhVien.add(sinhVien);

        }
        return listSinhVien;
    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
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
        } catch (Exception e) {
            return "";
        }
    }

}
