package com.fpolydatn.core.daotao.excel;

import com.fpolydatn.core.daotao.model.request.DtGiagVienHDImportResquest;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class DtGiangVienImportExcel {

    public List<DtGiagVienHDImportResquest> mapReapExcelDatatoDB(MultipartFile file, String idCoSo, String idDotDangKy) {
        List<DtGiagVienHDImportResquest> tempStudentList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                DtGiagVienHDImportResquest giangVien = new DtGiagVienHDImportResquest();
                XSSFRow row = worksheet.getRow(i);

                if (checkIfRowIsEmpty(row)) continue;

                giangVien.setTenGvhd(row.getCell(1).getStringCellValue());
                giangVien.setTenTaiKhoan(row.getCell(2).getStringCellValue());
                giangVien.setMonHoc(row.getCell(3).getStringCellValue());
                giangVien.setSoNhomHuongDanToiDa((short) row.getCell(4).getNumericCellValue());
                giangVien.setSoDienThoai(row.getCell(5).getStringCellValue().trim());
                giangVien.setEmailFpt(row.getCell(6).getStringCellValue().trim());
                giangVien.setEmailFe(row.getCell(7).getStringCellValue().trim());
                giangVien.setDotDangKyId(idCoSo);
                giangVien.setCoSoId(idDotDangKy);
                tempStudentList.add(giangVien);
                System.out.println(giangVien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempStudentList;
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }

    public List<GiangVienHuongDan> checkListImport(List<DtGiagVienHDImportResquest> list) {
        List<GiangVienHuongDan> listGVHD = new ArrayList<>();
        if (list.isEmpty()) {
            return null;
        }
        list.parallelStream().forEach(gv -> {
            if (!gv.getTenGvhd().isBlank() || !gv.getTenTaiKhoan().isBlank() || !gv.getEmailFe().isBlank() ||
                    !gv.getEmailFpt().isBlank() || !gv.getSoDienThoai().isBlank() || gv.getSoNhomHuongDanToiDa() ==null){
                GiangVienHuongDan giangVien = new GiangVienHuongDan();
                giangVien.setTenGvhd(gv.getTenGvhd());
                giangVien.setTenTaiKhoan(gv.getTenTaiKhoan());
                giangVien.setSoNhomHuongDanToiDa((gv.getSoNhomHuongDanToiDa()));
                giangVien.setSoDienThoai(gv.getSoDienThoai());
                giangVien.setEmailFpt(gv.getEmailFpt());
                giangVien.setEmailFe(gv.getEmailFe());
                giangVien.setDotDangKyId(gv.getDotDangKyId());
                giangVien.setCoSoId(gv.getCoSoId());
                listGVHD.add(giangVien);
            }
        });
        return listGVHD;
    }
}
