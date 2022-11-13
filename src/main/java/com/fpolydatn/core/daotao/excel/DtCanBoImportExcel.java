package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.response.DtCanBoExcelImport;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caodinh
 */
public class DtCanBoImportExcel {

    public static List<DtCanBoExcelImport> importData(MultipartFile reapExcelDataFile, String id) throws IOException {
        List<DtCanBoExcelImport> listCanBo = new ArrayList<>();
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
            if (row.getCell(1).getCellType() != CellType.STRING
                    && row.getCell(2).getCellType() != CellType.STRING
                    && row.getCell(3).getCellType() != CellType.STRING
                    && row.getCell(4).getCellType() != CellType.STRING
                    && row.getCell(5).getCellType() != CellType.STRING
                    && row.getCell(6).getCellType() != CellType.STRING
            ) {
                continue;
            }
            DtCanBoExcelImport canBo = new DtCanBoExcelImport();
            canBo.setEmailFE(String.valueOf(getCellValue(row.getCell(1))).trim());
            canBo.setEmailFPT(String.valueOf(getCellValue(row.getCell(2))).trim());
            canBo.setSoDienThoai(String.valueOf(getCellValue(row.getCell(3))).trim());
            canBo.setTenCanBo(String.valueOf(getCellValue(row.getCell(4))).trim());
            canBo.setTenTaiKhoan(String.valueOf(getCellValue(row.getCell(5))).trim());
            canBo.setVaiTro(String.valueOf(getCellValue(row.getCell(6))).trim());
            canBo.setCoSoId(id);
            listCanBo.add(canBo);
        }
        return listCanBo;
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
