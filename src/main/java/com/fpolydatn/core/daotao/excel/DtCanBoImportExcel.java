package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.constant.VaiTro;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caodinh
 */
public class DtCanBoImportExcel {

    public List<CanBo> importData(MultipartFile reapExcelDataFile, String id) {
        List<CanBo> listCanBo;
        try {
            listCanBo = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                CanBo canBo = new CanBo();
                XSSFRow row = worksheet.getRow(i);
                canBo.setEmailFe(String.valueOf(getCellValue(row.getCell(1))));
                canBo.setEmailFpt(String.valueOf(getCellValue(row.getCell(2))));
                canBo.setSoDienThoai(String.valueOf(getCellValue(row.getCell(3))));
                canBo.setTenCanBo(String.valueOf(getCellValue(row.getCell(4))));
                canBo.setTenTaiKhoan(String.valueOf(getCellValue(row.getCell(5))));
                canBo.setVaiTro(VaiTro.valueOf(String.valueOf(getCellValue(row.getCell(6)))));
                canBo.setCoSoId(id);
                listCanBo.add(canBo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listCanBo;
    }

    private static Object getCellValue(XSSFCell cell) {
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
