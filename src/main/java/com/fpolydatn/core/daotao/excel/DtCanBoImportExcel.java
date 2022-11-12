package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.chunhiem.model.request.CnCreateSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoExcelImport;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.constant.VaiTro;
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
            if (row.getRowNum()==0){
                continue;
            }
            DtCanBoExcelImport canBo = new DtCanBoExcelImport();
            canBo.setEmailFE(String.valueOf(getCellValue(row.getCell(1))));
            canBo.setEmailFPT(String.valueOf(getCellValue(row.getCell(2))));
            canBo.setSoDienThoai(String.valueOf(getCellValue(row.getCell(3))));
            canBo.setTenCanBo(String.valueOf(getCellValue(row.getCell(4))));
            canBo.setTenTaiKhoan(String.valueOf(getCellValue(row.getCell(5))));
            if(String.valueOf(getCellValue(row.getCell(6))).equals("DAO_TAO")){
                canBo.setVaiTro(VaiTro.DAO_TAO);
            }else if(String.valueOf(getCellValue(row.getCell(6))).equals("CHU_NHIEM_BO_MON")){
                canBo.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
            }
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
