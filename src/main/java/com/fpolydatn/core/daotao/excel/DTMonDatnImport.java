package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.request.DtCreateImportMonDatnRequest;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huynq
 */
public class DTMonDatnImport {

    public List<DtCreateImportMonDatnRequest> importExcelList(MultipartFile file) throws IOException {
        List<DtCreateImportMonDatnRequest> list = new ArrayList<>();

        Workbook workbook = StreamingReader.builder()
                .bufferSize(4096)
                .rowCacheSize(50)
                .open(file.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        for (Row row :
                worksheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            DtCreateImportMonDatnRequest createMonDatn = new DtCreateImportMonDatnRequest();

            if (String.valueOf(getCellValue(row.getCell(0))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(1))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(2))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(3))).trim().length() == 0 &&
                    String.valueOf(getCellValue(row.getCell(4))).trim().length() == 0
            ) {
                continue;
            }

            createMonDatn.setStt(row.getRowNum());
            createMonDatn.setMaMon(String.valueOf(getCellValue(row.getCell(1))).trim().toUpperCase());
            createMonDatn.setTenMonDatn(String.valueOf(getCellValue(row.getCell(2))).trim());
            createMonDatn.setChuyenNganhId(String.valueOf(getCellValue(row.getCell(3))).trim());
            createMonDatn.setNhomMonDatnId(String.valueOf(getCellValue(row.getCell(4))).trim());

            list.add(createMonDatn);
        }

        return list;
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
