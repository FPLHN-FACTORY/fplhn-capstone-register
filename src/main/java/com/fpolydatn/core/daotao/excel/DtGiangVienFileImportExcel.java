package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.request.DtGiagVienHDImportResquest;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DtGiangVienFileImportExcel {

    public  List<DtGiagVienHDImportResquest> importData(MultipartFile reapExcelDataFile) throws IOException {
        List<DtGiagVienHDImportResquest> listGVHD = new ArrayList<>();
        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(reapExcelDataFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row :
                worksheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (String.valueOf(getCellValue(row.getCell(0))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(1))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(2))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(3))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(4))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(6))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(7))).trim().length() == 0
            ) {
                continue;
            }
            DtGiagVienHDImportResquest giangVien = new DtGiagVienHDImportResquest();
            giangVien.setTenGvhd(String.valueOf(getCellValue(row.getCell(1))).trim());
            giangVien.setTenTaiKhoan(String.valueOf(getCellValue(row.getCell(2))).trim());
            giangVien.setMonHoc(String.valueOf(getCellValue(row.getCell(3))).trim());
            giangVien.setSoNhomHuongDanToiDa((short) row.getCell(4).getNumericCellValue());
            giangVien.setSoDienThoai(String.valueOf(getCellValue(row.getCell(5))).trim());
            giangVien.setEmailFpt(String.valueOf(getCellValue(row.getCell(6))).trim());
            giangVien.setEmailFe(String.valueOf(getCellValue(row.getCell(7))).trim());
            listGVHD.add(giangVien);
        }
        return listGVHD;
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
