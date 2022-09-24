package com.fpolydatn.core.daotao.helper;

import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.constant.VaiTro;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caodinh
 */
@Component
public class ExcelHelper {

    @Autowired
    private DtCanBoService dtCanBoService;

    public  List<CanBo> importData(MultipartFile reapExcelDataFile,String id) throws IOException {
        List<CanBo> listCanBo = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());

        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            CanBo canBo = new CanBo();
            XSSFRow row = worksheet.getRow(i);
            canBo.setEmailFe(row.getCell(0).getStringCellValue());
            canBo.setEmailFpt(row.getCell(1).getStringCellValue());
            canBo.setSoDienThoai(row.getCell(2).getStringCellValue());
            canBo.setTenCanBo(row.getCell(3).getStringCellValue());
            canBo.setTenTaiKhoan(row.getCell(4).getStringCellValue());
            canBo.setVaiTro(VaiTro.valueOf(row.getCell(5).getStringCellValue()));
            canBo.setCoSoId(id);
            if(!dtCanBoService.findByEmailFe(canBo.getEmailFe()).isPresent()) {
                listCanBo.add(canBo);
            }
        }
        return listCanBo;
    }

}