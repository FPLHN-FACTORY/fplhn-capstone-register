package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtGiangVienHuongDanExport;
import com.fpolydatn.core.daotao.excel.DtGiangVienImportExcel;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtGiagVienHDImportResquest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.repository.DtGiangVienHDRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.repository.DtPhanCongHuongDanRepository;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.FormUtils;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author Vinhnv
 */
@Service
@Validated
public class DtGiangVienHDServiceImpl implements DtGiangVienHDService {

    @Autowired
    private DtGiangVienHDRepository dtGiangVienHDRepository;

    @Autowired
    private DtMonDatnRepository dtMonDatnRepository;

    @Autowired
    private DtPhanCongHuongDanRepository dtPhanCongHuongDanRepository;

    private HashMap<String, String> map;

    private FormUtils formUtils = new FormUtils();

    @Override
    public GiangVienHuongDan create(@Valid DtCreateGiangVienHDRequest command) {
        String idByEmailFpt = dtGiangVienHDRepository.getIdByEmailFpt(command.getEmailFpt().trim());
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        String idByEmailFe = dtGiangVienHDRepository.getIdByEmailFe(command.getEmailFe());
        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }
        String idByTenGiangVien = dtGiangVienHDRepository.getIdByTenTaiKhoan(command.getTenTaiKhoan());
        if (idByTenGiangVien != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan gvhd = formUtils.convertToObject(GiangVienHuongDan.class, command);
        return dtGiangVienHDRepository.save(gvhd);
    }

    @Override
    public Boolean delete(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        int countSoNhomDangHuongDan = dtGiangVienHDRepository.getCountSoNhomDangHuongDan(id);
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        if (countSoNhomDangHuongDan != 0) {
            throw new RestApiException(Message.GIANG_VIEN_DANG_CO_NHOM_HUONG_DAN);
        }
        dtGiangVienHDRepository.delete(gvhdCheck.get());
        return true;
    }

    @Override
    public GiangVienHuongDan update(DtUpdateGiangVienHDRequest command) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(command.getId());
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }

        String idByEmailFpt = dtGiangVienHDRepository.getIdUpdateByEmailFpt(command.getEmailFpt().trim(), command.getId());
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        String idByEmailFe = dtGiangVienHDRepository.getIdUpdateByEmailFe(command.getEmailFe(), command.getId());
        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }
        String idByTenGiangVien = dtGiangVienHDRepository.getIdUpdateByTenTaiKhoan(command.getTenTaiKhoan(), command.getId());
        if (idByTenGiangVien != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan giangVien = gvhdCheck.get();
        giangVien.setTenGvhd(command.getTenGvhd());
        giangVien.setSoNhomHuongDanToiDa(command.getSoNhomHuongDanToiDa());
        giangVien.setTenTaiKhoan(command.getTenTaiKhoan());
        giangVien.setSoDienThoai(command.getSoDienThoai());
        giangVien.setEmailFpt(command.getEmailFpt());
        giangVien.setEmailFe(command.getEmailFe());
        giangVien.setDotDangKyId(command.getDotDangKyId());
        return dtGiangVienHDRepository.save(giangVien);
    }

    @Override
    public GiangVienHuongDan findById(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        return gvhdCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenGiangVien) {
        return dtGiangVienHDRepository.countByName(tenGiangVien);
    }

    @Override
    public PageableObject<DtGiangVienHDResponse> search(final DtFindGiangVienHDRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtGiangVienHDResponse> res = dtGiangVienHDRepository.findByName(request, pageable);
        return new PageableObject<>(res);
    }

    @Override
    public List<DtGiagVienHDImportResquest> importExcel(MultipartFile file, String idCoSo, String idDotDangKy) throws IOException {
        List<DtGiagVienHDImportResquest> list = new ArrayList<>();
        try {
            Workbook workbook = StreamingReader.builder()
                    .bufferSize(4096)
                    .rowCacheSize(50)
                    .open(file.getInputStream());
            int rowNumber = 0;
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                DtGiagVienHDImportResquest gvhd = new DtGiagVienHDImportResquest();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 1:
                            gvhd.setCoSoId(idCoSo);
                            gvhd.setDotDangKyId(idDotDangKy);
                            String tenGV = cell.getStringCellValue().trim();
                            gvhd.setTenGvhd(tenGV);
                            break;
                        case 2:
                            String tenTaiKhoan = cell.getStringCellValue().trim();
                            gvhd.setTenTaiKhoan(tenTaiKhoan);
                            break;
                        case 3:
                            String monHoc = cell.getStringCellValue().trim();
                            gvhd.setMonHoc(monHoc);
                            break;
                        case 4:
                            double soNhom = cell.getNumericCellValue();
                            gvhd.setSoNhomHuongDanToiDa((short) soNhom);
                            break;
                        case 5:
                            String sdt = cell.getStringCellValue().trim();
                            gvhd.setSoDienThoai(sdt);
                            break;
                        case 6:
                            String emailFpt = cell.getStringCellValue().trim();
                            gvhd.setEmailFpt(emailFpt);
                            break;
                        case 7:
                            String emailFe = cell.getStringCellValue().trim();
                            gvhd.setEmailFe(emailFe);
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(gvhd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        checkValidFile(list);
        return list;
    }


    @Override
    public List<GiangVienHuongDan> checkValidFile(List<DtGiagVienHDImportResquest> list) {
        List<GiangVienHuongDan> listGVHD = new ArrayList<>();
        List<GiangVienHuongDan> listGVHDUpdate = new ArrayList<>();
        Map<String, GiangVienHuongDan> mapGiangVien = new HashMap<>();
        if (list == null) {
            throw new RestApiException(Message.FILE_EMPTY);
        }
        for (DtGiagVienHDImportResquest gvhd : list) {
            GiangVienHuongDan giangVien = new GiangVienHuongDan();
            giangVien.setTenGvhd(gvhd.getTenGvhd());
            giangVien.setTenTaiKhoan(gvhd.getTenTaiKhoan());
            giangVien.setSoNhomHuongDanToiDa(gvhd.getSoNhomHuongDanToiDa());
            giangVien.setSoDienThoai(gvhd.getSoDienThoai());
            giangVien.setEmailFpt(gvhd.getEmailFpt());
            giangVien.setEmailFe(gvhd.getEmailFe());
            giangVien.setDotDangKyId(gvhd.getDotDangKyId());
            giangVien.setCoSoId(gvhd.getCoSoId());
            listGVHD.add(giangVien);
            mapGiangVien.put(giangVien.getTenTaiKhoan(), giangVien);
        }
        dtGiangVienHDRepository.saveAll(listGVHD);
        List<PhanCongHuongDan> listPhanCong = new ArrayList<>();
        list.parallelStream().forEach(gv -> {
            GiangVienHuongDan giangVienHuongDan = mapGiangVien.get(gv.getTenTaiKhoan());
            String array[] = new String[]{};
            array = gv.getMonHoc().split(",");
            for (int i = 0; i < array.length; i++) {
                PhanCongHuongDan phanCongHuongDan = new PhanCongHuongDan();
                MonDatn monDatn = dtMonDatnRepository.findByMaMon(array[i].trim());
                phanCongHuongDan.setGiangVienId(giangVienHuongDan.getId());
                phanCongHuongDan.setMonDatnId(monDatn.getId());
                listPhanCong.add(phanCongHuongDan);
            }
        });
        dtPhanCongHuongDanRepository.saveAll(listPhanCong);
        return listGVHD;
    }

    @Override
    public void DtGiangVienHDExportExcel(HttpServletResponse response, String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=GiangVienHuongDan" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        List<DtGiangVienHDResponse> data = dtGiangVienHDRepository.exportExcel(request);
        DtGiangVienHuongDanExport dtGiangVienHuongDanExport = new DtGiangVienHuongDanExport();
        dtGiangVienHuongDanExport.employeeDetailReport(response, data);
    }

    @Override
    public void DtGiangVienHDMauExportExcel(HttpServletResponse response, String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=GiangVienHuongDan" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        DtGiangVienHuongDanExport dtGiangVienHuongDanExport = new DtGiangVienHuongDanExport();
        dtGiangVienHuongDanExport.exportMauImport(response);
    }

    @Override
    public void importExcelGVHD(MultipartFile file, String idCoSo, String idDotDangKy) {
        List<DtGiagVienHDImportResquest> importList =  new DtGiangVienImportExcel().mapReapExcelDatatoDB(file,idCoSo,idDotDangKy);
        List<GiangVienHuongDan> list = new DtGiangVienImportExcel().checkListImport(importList);
        dtGiangVienHDRepository.saveAll(list);
    }

    public void addDataInMap(HashMap<String, String> mapAll, String coSoId) {

        // lấy id, GVHD theo đợt đăng ký của cơ sở
        List<SimpleEntityProj> getAllMaMon = dtGiangVienHDRepository.getAllGiangVien(coSoId);
        getAllPutMap(mapAll, getAllMaMon);
    }

    public void getAllPutMap(HashMap<String, String> mapSimple, List<SimpleEntityProj> getAllSimpleEntityProjs) {
        for (SimpleEntityProj simpleEntityProj : getAllSimpleEntityProjs) {
            mapSimple.put(simpleEntityProj.getName(), simpleEntityProj.getId());
        }
    }

}
