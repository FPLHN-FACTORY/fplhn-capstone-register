package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DsSinhVienExporter;
import com.fpolydatn.core.daotao.excel.NhomDatnExportHelper;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.EmailSender;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Validated
public class DtNhomDatnTheoDotServiceImpl implements DtNhomDatnTheoDotService {
    @Autowired
    private DtNhomDatnTheoDotRepository nhomDatnRepository;

    @Autowired
    private DtSinhVienTheoDotRepository sinhVienRepository;

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    @Override
    public PageableObject<DtNhomDatnTheoDotResponse> findAllNhomDatn(FindDtNhomDatnTheoDotRequest request) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (request.getDotDangKyId() == null || request.getDotDangKyId().equalsIgnoreCase("")) {
            request.setDotDangKyId(dotDangKyId != null ? dotDangKyId : "");
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtNhomDatnTheoDotResponse> res = nhomDatnRepository.findAllNhomDatn(pageable, request);
        return new PageableObject<>(res);
    }

    @Override
    public List<DtSinhVienResponse> showDanhSachSinhVien(String idNhom, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = nhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        List<DtSinhVienResponse> res = nhomDatnRepository.showDanhSachSinhVien(idNhom, coSoId, nhomDatnFind.get().getDotDangKyId());
        return res;
    }

    @Override
    public void exportNhomDatn(HttpServletResponse response, String typeExcel, FindDtNhomDatnTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);

        List<DtNhomDatnTheoDotResponse> nhomDatnList = nhomDatnRepository.findAllNhomDatnToExcel(request);
        NhomDatnExportHelper.exportData(response, nhomDatnList);
    }

    @Override
    public void exportDsSinhVien(HttpServletResponse response, FindDtNhomDatnTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn.xlsx";
        response.setHeader(headerKey, headerValue);
        DsSinhVienExporter mau = new DsSinhVienExporter(
                sinhVienRepository.getSinhVienNhomDatnExportExcel(request),
                sinhVienRepository.getNhomDatnExportExcel(request));
        mau.exportData(response);
    }

    @Override
    @Transactional
    public Boolean deleteNhomDatn(List<String> listIdNhom) {
        List<NhomDatn> listNhomDatn = nhomDatnRepository.findAllById(listIdNhom);
        if (listNhomDatn.size() == 0) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (commonHelper.isDotDangKyDangDienRa(listNhomDatn.get(0).getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        Map<String, ChuyenNganh> chuyenNganhMap = fillMapChuyenNganh();
        List<SinhVien> listSinhVienAll = sinhVienRepository.findSinhVienByNhomIdIn(listIdNhom);
        for (SinhVien yy : listSinhVienAll) {
            yy.setMonDatnId(null);
            yy.setNhomId(null);
        }
        sinhVienRepository.saveAll(listSinhVienAll);
        nhomDatnRepository.deleteAllById(listIdNhom);
        int soThanhvien = listSinhVienAll.size();
        String[] email = new String[soThanhvien];
        for (int i = 0; i < soThanhvien; i++) {
            email[i] = listSinhVienAll.get(i).getEmail();
        }
        String[] message = new String[]{"Phòng dào tạo đã xóa nhóm của bạn"};
        Thread newThread = new Thread(() ->
                emailSender.sendEmail(email, "[FCR] Phòng dào tạo xóa nhóm",
                        "Nhóm đã bị xóa", message)
        );
        newThread.start();

        listNhomDatn.forEach(el -> {
            try {
                SinhVien truongNhom = sinhVienRepository.findById(el.getTruongNhomId()).get();
                ChuyenNganh chuyenNganh = chuyenNganhMap.get(truongNhom.getChuyenNganhId());
                String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Đào tạo đã xóa nhóm '" + el.getMaNhom() + "'",
                                pathFileLogActive, "Thành công");
                logService.sendLogMessage(loggerObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Đào tạo đã xóa nhóm '" + el.getMaNhom() + "'",
                                pathFile, "Thành công");
                loggerObject.setTenNhom(el.getMaNhom());
                loggerObject.setIdChuyenNganh(chuyenNganh.getId());
                loggerObject.setTenChuyenNganh(chuyenNganh.getTenChuyenNganh());
                logService.sendLogMessage(loggerObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        return true;
    }

    public Map<String, ChuyenNganh> fillMapChuyenNganh() {
        Map<String, ChuyenNganh> chuyenNganhMap = new HashMap<>();
        chuyenNganhRepository.findAll().stream().forEach(el -> {
            chuyenNganhMap.put(el.getId(), el);
        });
        return chuyenNganhMap;
    }
}
