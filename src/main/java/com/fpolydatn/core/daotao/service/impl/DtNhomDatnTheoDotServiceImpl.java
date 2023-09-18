package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtNhomDatnExportExcel;
import com.fpolydatn.core.daotao.excel.DtNhomDatnTheoDotExport;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.NhomDatnRepository;
import com.fpolydatn.repository.SinhVienRepository;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.EmailSender;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DtNhomDatnTheoDotServiceImpl implements DtNhomDatnTheoDotService {
    @Autowired
    private DtNhomDatnTheoDotRepository dtNhomDatnTheoDotRepository;

    @Autowired
    private DtSinhVienTheoDotRepository sinhVienTheoDotRepository;

    @Autowired
    @Qualifier(SinhVienRepository.NAME)
    private SinhVienRepository sinhVienRepository;

    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private WriterFileCSV writerFileCSV;

    private List<DtNhomDatnTheoDotResponse> listNhomDatn;

    @Override
    public PageableObject<DtNhomDatnTheoDotResponse> findAllNhomDatn(FindDtNhomDatnTheoDotRequest request) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (request.getDotDangKyId() == null) {
            if (dotDangKyId != null) {
                request.setDotDangKyId(dotDangKyId);
            } else {
                request.setDotDangKyId("");
            }
        } else if (request.getDotDangKyId().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                request.setDotDangKyId(dotDangKyId);
            } else {
                request.setDotDangKyId("");
            }
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtNhomDatnTheoDotResponse> res = dtNhomDatnTheoDotRepository.findAllNhomDatn(pageable, request);
        listNhomDatn = res.toList();
        return new PageableObject<>(res);
    }

    @Override
    public List<DtSinhVienResponse> showDanhSachSinhVien(String idNhom, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = dtNhomDatnTheoDotRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        List<DtSinhVienResponse> res = dtNhomDatnTheoDotRepository.showDanhSachSinhVien(idNhom, coSoId, nhomDatnFind.get().getDotDangKyId());
        return res;
    }

    @Override
    public void DTNhomDatnExport(HttpServletResponse response, String typeExcel, FindDtNhomDatnTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        listNhomDatn = dtNhomDatnTheoDotRepository.findAllNhomDatnToExcel(request);
        DtNhomDatnTheoDotExport dtNhomDatnTheoDotExport = new DtNhomDatnTheoDotExport();
        dtNhomDatnTheoDotExport.exportData(response, listNhomDatn);
    }

    @Override
    public void DTNhomDatnSinhVienExport(HttpServletResponse response, FindDtNhomDatnTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn.xlsx";
        response.setHeader(headerKey, headerValue);
        DtNhomDatnExportExcel mau = new DtNhomDatnExportExcel(sinhVienTheoDotRepository.getSinhVienNhomDatnExportExcel(request), sinhVienTheoDotRepository.getNhomDatnExportExcel(request));
        mau.exportData(response);
    }

    private Boolean checkDotDangKyHienTai(String dotDangKyId) {
        Optional<DotDangKy> dotDangKyFind = dotDangKyRepository.findById(dotDangKyId);
        if (!dotDangKyFind.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime >= dotDangKyFind.get().getNgayBatDau() && currentTime <= dotDangKyFind.get().getNgayKetThuc()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteNhomDatn(List<String> listIdNhom) {
        List<NhomDatn> listNhomDatn = dtNhomDatnTheoDotRepository.findAllById(listIdNhom);
        List<LoggerObject> list = new ArrayList<>();
        if (listNhomDatn.size() == 0) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (checkDotDangKyHienTai(listNhomDatn.get(0).getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        List<SinhVien>listSinhVienAll = new ArrayList<>();
        for (NhomDatn nhomDatn : listNhomDatn) {
            List<SinhVien> listSinhVien = sinhVienTheoDotRepository.getAllSinhVien(nhomDatn.getId(), commonHelper.getDotDangKyHienTai());
            listSinhVienAll.addAll(listSinhVien);
            LoggerObject loggerObject = new LoggerObject();
            loggerObject.setContent("Đào tạo đã xóa nhóm: " + nhomDatn.getMaNhom());
            loggerObject.setStatus(ConfigurationsConstant.TRUE);
            list.add(loggerObject);
        }
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

        try {
            String tenDotDangKy = writerFileCSV.tenDotDangKyHienTai();
            writerFileCSV.writerAllObjectFileCSV(list,
                    writerFileCSV.getPathFileByDotDangKyAndCoSo(tenDotDangKy) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_DANG_KY_DATN));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }
}
