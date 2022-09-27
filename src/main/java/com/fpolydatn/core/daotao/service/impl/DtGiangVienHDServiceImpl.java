package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.repository.DtCoSoRepository;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.repository.DtGiangVienHDRepository;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private DtCoSoRepository dtCoSoRepository;

    @Autowired
    private DtDotDangKyRepository dtDotDangKyRepository;


    private FormUtils formUtils = new FormUtils();

    @Override
    public GiangVienHuongDan create( DtCreateGiangVienHDRequest command) {
        String idByEmailFpt = dtGiangVienHDRepository.getIdByEmailFpt(command.getEmailFpt().trim());
        if (idByEmailFpt != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        String idByEmailFe = dtGiangVienHDRepository.getIdByEmailFe(command.getEmailFe());
        if (idByEmailFe != null) {
            throw new RestApiException(Message.EMAIL_FE_EXIST);
        }
        String idByTenTaiKhoan = dtGiangVienHDRepository.getIdByTenTaiKhoan(command.getTenTaiKhoan());
        if (idByTenTaiKhoan != null) {
            throw new RestApiException(Message.TEN_TAI_KHOAN_GIANG_VIEN_HUONG_DAN_EXIST);
        }
        GiangVienHuongDan gvhd = formUtils.convertToObject(GiangVienHuongDan.class, command);
        return dtGiangVienHDRepository.save(gvhd);
    }

    @Override
    public Boolean delete(String id) {
        Optional<GiangVienHuongDan> gvhdCheck = dtGiangVienHDRepository.findById(id);
        if (!gvhdCheck.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
        }
        dtGiangVienHDRepository.delete(gvhdCheck.get());
        return true;
    }

    @Override
    public GiangVienHuongDan update( DtUpdateGiangVienHDRequest command) {
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
    public List<GiangVienHuongDan> importExcel(MultipartFile file) {
        List<GiangVienHuongDan> list = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;
                GiangVienHuongDan gvhd = new GiangVienHuongDan();
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 1:
                            String tenCoSo = cell.getStringCellValue();
                            gvhd.setCoSoId(dtCoSoRepository.findName(tenCoSo).getId());
                            break;
                        case 2:
                            String tenDotDangKy = cell.getStringCellValue();
                            gvhd.setDotDangKyId(dtDotDangKyRepository.findName(tenDotDangKy).getId());
                            break;
                        case 3:
                            String tenGV = cell.getStringCellValue().trim();
                            gvhd.setTenGvhd(tenGV);
                            break;
                        case 4:
                            String tenTaiKhoan = cell.getStringCellValue().trim();
                            gvhd.setTenTaiKhoan(tenTaiKhoan);
                            break;
                        case 5:
                            double soNhom = cell.getNumericCellValue();
                            gvhd.setSoNhomHuongDanToiDa((short) soNhom);
                            break;
                        case 6:
                            String sdt = cell.getStringCellValue().trim();
                            gvhd.setSoDienThoai(sdt);
                            break;
                        case 7:
                            String emailFpt = cell.getStringCellValue().trim();
                            gvhd.setEmailFpt(emailFpt);
                            break;
                        case 8:
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
    public List<GiangVienHuongDan> checkValidFile(List<GiangVienHuongDan> list) {
        List<GiangVienHuongDan> listGVHD = new ArrayList<>();
        if(list == null){
            throw new RestApiException(Message.FILE_EMPTY);
        }
        for ( GiangVienHuongDan gvhd: list) {
            GiangVienHuongDan giangVien =  dtGiangVienHDRepository.getGVHDByEmailFpt(gvhd.getEmailFpt().trim());
            if(giangVien != null){
                giangVien.setTenGvhd(gvhd.getTenGvhd());
                giangVien.setSoNhomHuongDanToiDa(gvhd.getSoNhomHuongDanToiDa());
                giangVien.setTenTaiKhoan(gvhd.getTenTaiKhoan());
                giangVien.setSoDienThoai(gvhd.getSoDienThoai());
                giangVien.setEmailFpt(gvhd.getEmailFpt());
                giangVien.setEmailFe(gvhd.getEmailFe());
                giangVien.setDotDangKyId(gvhd.getDotDangKyId());
                dtGiangVienHDRepository.save(giangVien);
            }else{
                listGVHD.add(gvhd);
            }
        }
        dtGiangVienHDRepository.saveAll(listGVHD);
        return listGVHD;
    }



}
