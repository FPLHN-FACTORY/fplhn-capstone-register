package com.fpolydatn.core.chunhiem.excel;

import com.fpolydatn.core.chunhiem.model.response.CnNhomTheoDotResponse;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thangncph26123
 */
@Component
public class CnNhomTheoDotImport {

    public static List<CnNhomTheoDotResponse> importData(MultipartFile reapExcelDataFile) throws IOException {
        List<CnNhomTheoDotResponse> listNhom = new ArrayList<>();
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
            if (row.getCell(1).getCellType() != CellType.STRING) {
                continue;
            }
            CnNhomTheoDotResponse nhomDatn = new CnNhomTheoDotResponse();
            nhomDatn.setMaNhom(String.valueOf(getCellValue(row.getCell(1))).trim());
            nhomDatn.setTenDeTai(String.valueOf(getCellValue(row.getCell(2))).trim());
            nhomDatn.setGvhd(String.valueOf(getCellValue(row.getCell(3))).trim().toLowerCase());
            listNhom.add(nhomDatn);
        }
        return listNhom;
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
