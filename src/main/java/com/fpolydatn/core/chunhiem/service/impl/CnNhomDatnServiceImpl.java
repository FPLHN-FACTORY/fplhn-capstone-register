package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.excel.CnSinhVienTheoDotExport;
import com.fpolydatn.core.chunhiem.excel.CnSinhVienTheoDotImport;
import com.fpolydatn.core.chunhiem.excel.CnThongTinSinhVienExport;
import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnCreateSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.request.CnSearchNhomInputRequest;
import com.fpolydatn.core.chunhiem.model.response.CnGiangVienHuongDanDanhSachNhomResponse;
import com.fpolydatn.core.chunhiem.model.response.CnGvhdAndDanhSachNhomResponse;
import com.fpolydatn.core.chunhiem.model.response.CnLoggerResponse;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienImport;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotExcel;
import com.fpolydatn.core.chunhiem.model.response.CnThongTinNhomResponse;
import com.fpolydatn.core.chunhiem.model.response.CnThongTinSinhVienExcel;
import com.fpolydatn.core.chunhiem.repository.CnChuyenNganhRepository;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.EmailSender;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.NhomDatnHelper;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class CnNhomDatnServiceImpl implements CnNhomDatnService {

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private CnSinhVienRepository cnSinhVienRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    private NhomDatnHelper nhomDatnHelper;

    @Autowired
    private CnMonDatnRepository cnMonDatnRepository;

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private CnChuyenNganhRepository cnChuyenNganhRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    private List<CnSinhVienTheoDotExcel> list;

    private List<CnThongTinSinhVienExcel> listThongTinSinhVien;

    private List<CnCreateSinhVienTheoDotRequest> sinhVienList;

    private ConcurrentHashMap<String, SinhVien> mapSinhVien;

    private ConcurrentHashMap<String, SinhVien> mapSinhVienKeyId;

    private ConcurrentHashMap<String, SinhVien> mapSinhVienBefore;

    private ConcurrentHashMap<String, SinhVien> mapTruongNhom;

    private ConcurrentHashMap<String, NhomDatn> mapNhomDatn;

    private ConcurrentHashMap<String, NhomDatn> mapNhomDatnBefore;

    private ConcurrentHashMap<String, NhomDatn> mapNhomDatnKeyId;

    private ConcurrentHashMap<String, GiangVienHuongDan> mapGiangVien;

    private ConcurrentHashMap<String, GiangVienHuongDan> mapGiangVienKeyId;

    private ConcurrentHashMap<String, ChuyenNganh> mapChuyenNganh;

    private ConcurrentHashMap<String, CnGvhdAndDanhSachNhomResponse> mapGiangVienAndDanhSachNhom;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<CnNhomDatnResponse> search(CnFindNhomDatnRequest request, String idChuNhiem) {
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
        Page<CnNhomDatnResponse> res = cnNhomDatnRepository.findNhomDatn(request, pageable, idChuNhiem);
        return new PageableObject(res);
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
    public boolean chotDeTai(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (checkDotDangKyHienTai(nhomDatnCheck.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.GVHD_CHOT) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CNBM_CHOT);
        nhomDatnCheck.get().setNhanXet(null);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        SinhVien truongNhomFind = cnSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Đã chốt tên đề tài nhóm '" + nhomDatnCheck.get().getMaNhom() + "' thành '"
                                    + nhomDatnCheck.get().getTenDeTai() + "'",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Đã chốt tên đề tài nhóm '" + nhomDatnCheck.get().getMaNhom() + "' thành '"
                                    + nhomDatnCheck.get().getTenDeTai() + "'",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObject.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean chotNhieuDeTai(List<String> listIdNhom) {
        List<NhomDatn> listNhomDatn = cnNhomDatnRepository.findAllById(listIdNhom);
        List<LoggerObject> loggerObjectList = new ArrayList<>();
        if (listIdNhom.size() != listNhomDatn.size()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        SinhVien truongNhomFind = cnSinhVienRepository.findById(listNhomDatn.get(0).getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        for (NhomDatn nhomDatn : listNhomDatn) {
            if (nhomDatn.getTrangThai() != TrangThaiNhom.GVHD_CHOT) {
                throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
            }
            if (checkDotDangKyHienTai(nhomDatn.getDotDangKyId())) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
            nhomDatn.setNhanXet(null);
            nhomDatn.setTrangThai(TrangThaiNhom.CNBM_CHOT);
            try {
                String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Đã chốt tên đề tài nhóm '" + nhomDatn.getMaNhom() + "' thành '"
                                        + nhomDatn.getTenDeTai() + "'",
                                pathFileLogActive, "Thành công");
                loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
                loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
                loggerObjectList.add(loggerObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Đã chốt tên đề tài nhóm '" + nhomDatn.getMaNhom() + "' thành '"
                                        + nhomDatn.getTenDeTai() + "'",
                                pathFile, "Thành công");
                loggerObject.setTenNhom(nhomDatn.getMaNhom());
                loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
                loggerObject.setTenChuyenNganh(tenChuyenNganh);
                loggerObjectList.add(loggerObject);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        cnNhomDatnRepository.saveAll(listNhomDatn);
        loggerObjectList.forEach(el -> {
            logService.sendLogMessage(el);
        });
        return true;
    }

    @Override
    public boolean xacNhanNhom(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.MOI_THANH_LAP) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        if (checkDotDangKyHienTai(nhomDatnCheck.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        Optional<GiangVienHuongDan> gvhd = null;
        List<SinhVien> listSinhVien = cnSinhVienRepository.getAllSinhVien(nhomDatnCheck.get().getId(), commonHelper.getDotDangKyHienTai());
        int soThanhvien = listSinhVien.size();
        String[] email = new String[soThanhvien];
        for (int i = 0; i < soThanhvien; i++) {
            email[i] = listSinhVien.get(i).getEmail();
        }
        if (nhomDatnCheck.get().getDotDangKyId() != null) {
            SinhVien truongNhom = null;
            for (SinhVien sv : listSinhVien) {
                if (sv.getId().equals(nhomDatnCheck.get().getTruongNhomId())) {
                    truongNhom = sv;
                }
            }
            if (nhomDatnCheck.get().getGiangVienId() != null) {
                gvhd = cnGiangVienHuongDanReponsitory.findById(nhomDatnCheck.get().getGiangVienId());
                String[] messageGV = new String[]{"CNBM đã xác nhận nhóm bạn hướng dẫn nhóm:",
                        "Mã nhóm: " + nhomDatnCheck.get().getMaNhom(), "Số thành viên: " + soThanhvien,
                        "Trưởng nhóm: " + truongNhom.getMaSinhVien() + " - " + truongNhom.getTenSinhVien(),
                        "SĐT: " + truongNhom.getSoDienThoai()};
                emailSender.sendEmail(new String[]{gvhd.get().getEmailFe()},
                        "[FCR] Chủ nhiệm bộ môn xác nhận nhóm",
                        "Xác nhận nhóm thành lập", messageGV);
            }
        }
        String[] messageSV = null;
        if (gvhd == null || !gvhd.isPresent()) {
            messageSV = new String[]{"Chủ nhiệm đã xác nhận nhóm của bạn được thành lập:", "GVHD: Chưa có",
                    "Tên đề tài: " + (nhomDatnCheck.get().getTenDeTai() == null ? "chưa có" : nhomDatnCheck.get().getTenDeTai())};
        } else if (gvhd.isPresent() && gvhd != null) {
            messageSV = new String[]{"Chủ nhiệm đã xác nhận nhóm của bạn được thành lập:",
                    "GVHD: " + gvhd.get().getTenGvhd() + " - " + gvhd.get().getTenTaiKhoan(),
                    "SĐT: " + gvhd.get().getSoDienThoai(),
                    "Email: " + gvhd.get().getEmailFe(),
                    "Tên đề tài: " + (nhomDatnCheck.get().getTenDeTai() == null ? "chưa có" : nhomDatnCheck.get().getTenDeTai())};
        }
        emailSender.sendEmail(email, "[FCR] Chủ nhiệm bộ môn xác nhận nhóm",
                "Xác nhận nhóm thành lập", messageSV);
        SinhVien truongNhomFind = cnSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Đã xác nhận thành lập nhóm '" + nhomDatnCheck.get().getMaNhom() + "'",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Đã xác nhận thành lập nhóm '" + nhomDatnCheck.get().getMaNhom() + "'",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObject.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean xacNhanNhieuNhom(List<String> listIdNhom) {
        List<NhomDatn> listNhomDatn = cnNhomDatnRepository.findAllById(listIdNhom);
        List<LoggerObject> loggerObjectList = new ArrayList<>();
        if (listIdNhom.size() != listNhomDatn.size()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        SinhVien truongNhomFind = cnSinhVienRepository.findById(listNhomDatn.get(0).getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        for (NhomDatn nhomDatn : listNhomDatn) {
            if (nhomDatn.getTrangThai() != TrangThaiNhom.MOI_THANH_LAP) {
                throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
            }
            if (checkDotDangKyHienTai(nhomDatn.getDotDangKyId())) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
            nhomDatn.setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
            try {
                String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Đã xác nhận thành lập nhóm '" + nhomDatn.getMaNhom() + "'",
                                pathFileLogActive, "Thành công");
                loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
                loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
                loggerObjectList.add(loggerObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Đã xác nhận thành lập nhóm '" + nhomDatn.getMaNhom() + "'",
                                pathFile, "Thành công");
                loggerObject.setTenNhom(nhomDatn.getMaNhom());
                loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
                loggerObject.setTenChuyenNganh(tenChuyenNganh);
                loggerObjectList.add(loggerObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        cnNhomDatnRepository.saveAll(listNhomDatn);
        loggerObjectList.forEach(el -> {
            logService.sendLogMessage(el);
        });
        return true;
    }

    @Override
    public boolean tuChoiChotDeTai(String id, String nhanXet) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (checkDotDangKyHienTai(nhomDatnCheck.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.GVHD_CHOT) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CAN_SUA);
        nhomDatnCheck.get().setNhanXet(nhanXet);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        SinhVien truongNhomFind = cnSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Đã từ chối đề tài nhóm '" + nhomDatnCheck.get().getMaNhom() + "'" + " với lí do là: " + nhanXet + ".",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Đã từ chối đề tài nhóm '" + nhomDatnCheck.get().getMaNhom() + "'" + " với lí do là: " + nhanXet + ".",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObject.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean delete(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (checkDotDangKyHienTai(nhomDatnCheck.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        cnNhomDatnRepository.delete(nhomDatnCheck.get());
        List<SinhVien> listSinhVien = cnSinhVienRepository.getAllSinhVien(id, commonHelper.getDotDangKyHienTai());
        StringBuffer stringBuffer = new StringBuffer();
        for (SinhVien xx : listSinhVien) {
            cnSinhVienRepository.deleteSinhVienOutGroup(xx.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
            stringBuffer.append(" Đã xóa sinh viên " + xx.getTenSinhVien() + "-" + xx.getMaSinhVien() + ".");
        }
        SinhVien truongNhomFind = cnSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Đã xóa nhóm '" + nhomDatnCheck.get().getMaNhom() + "'." + stringBuffer.toString(),
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Đã xóa nhóm '" + nhomDatnCheck.get().getMaNhom() + "'" + stringBuffer.toString(),
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObject.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        int soThanhvien = listSinhVien.size();
        String[] email = new String[soThanhvien];
        for (int i = 0; i < soThanhvien; i++) {
            email[i] = listSinhVien.get(i).getEmail();
        }
        String[] message = new String[]{"Chủ nhiệm đã xóa nhóm của bạn"};
        Thread newThread = new Thread(() ->
                emailSender.sendEmail(email, "[FCR] Chủ nhiệm bộ môn xóa nhóm",
                        "Nhóm đã bị xóa", message)
        );
        newThread.start();
        return true;
    }

    @Override
    public NhomDatn findById(String id) {
        Optional<NhomDatn> nhomdatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomdatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return nhomdatnCheck.get();
    }

    @Override
    public boolean create(@Valid final CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        String idGiangVien = cnCreateNhomDatnRequest.getIdGiangVien();
        if (!idGiangVien.equals("")) {
            Optional<GiangVienHuongDan> giangVienHuongDan = cnGiangVienHuongDanReponsitory.findById(idGiangVien);
            if (!giangVienHuongDan.isPresent()) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
            }
            if (checkDotDangKyHienTai(giangVienHuongDan.get().getDotDangKyId())) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
        }
        if (!idGiangVien.equals("")) {
            if (commonHelper.getSoNhomGiangVienDangHuongDan(idGiangVien) ==
                    cnNhomDatnRepository.getSoNhomGiangVienHuongDan(idGiangVien, commonHelper.getDotDangKyHienTai())) {
                throw new RestApiException(Message.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM);
            }
        }
        String idTruongNhomCheck = cnNhomDatnRepository.getSinhVienByMaSinhVien(cnCreateNhomDatnRequest.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
        if (idTruongNhomCheck == null) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        String idTruongNhom = cnNhomDatnRepository.getSinhVienByMaSinhVienAndMaNhom(cnCreateNhomDatnRequest.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
        if (idTruongNhom == null) {
            throw new RestApiException(Message.STUDENT_HAVE_GROUP);
        }
        String monDatnId = cnCreateNhomDatnRequest.getMonDatnId();
        if (cnMonDatnRepository.findById(monDatnId) == null) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        String checkMonTuongDuong = cnSinhVienRepository.checkMonTuongDuong(monDatnId, idTruongNhom, commonHelper.getDotDangKyHienTai());
        if (checkMonTuongDuong == null) {
            throw new RestApiException(Message.MON_DATN_KHONG_THOA_MAN_VOI_SINH_VIEN);
        }
        NhomDatn nhomDatn = new NhomDatn();
        nhomDatn.setTruongNhomId(idTruongNhom);
        if ("".equals(cnCreateNhomDatnRequest.getIdGiangVien().trim())) {
            nhomDatn.setGiangVienId(null);
        }
        String maNhom = nhomDatnHelper.genMaNhomTuDong();
        if (maNhom == null) {
            throw new RestApiException(Message.TAO_NHOM_THAT_BAI);
        }
        if (!idGiangVien.equals("")) {
            nhomDatn.setGiangVienId(cnCreateNhomDatnRequest.getIdGiangVien());
        }
        nhomDatn.setMaNhom(maNhom);
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        nhomDatn.setCoSoId(cnCreateNhomDatnRequest.getCoSoId());
        nhomDatn.setDotDangKyId(cnCreateNhomDatnRequest.getDotDangKyId());
        nhomDatn.setId(cnNhomDatnRepository.save(nhomDatn).getId());
        SinhVien truongNhomFind = cnSinhVienRepository.findById(nhomDatn.getTruongNhomId()).get();
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(truongNhomFind.getChuyenNganhId());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Đã tạo nhóm " + nhomDatn.getMaNhom() + ".");
        if (nhomDatn.getGiangVienId() != null) {
            Optional<GiangVienHuongDan> giangVienHuongDan = cnGiangVienHuongDanReponsitory.findById(idGiangVien);
            stringBuffer.append(". Phân công giảng viên " + giangVienHuongDan.get().getTenGvhd() + " - " + giangVienHuongDan.get().getTenTaiKhoan() + " hướng dẫn nhóm");
        }
        if (nhomDatn.getTruongNhomId() != null) {
            stringBuffer.append(". Trưởng nhóm là: " + cnCreateNhomDatnRequest.getMaSinhVien());
        }
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject(stringBuffer.toString(),
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObjectActive.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject(stringBuffer.toString(),
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatn.getMaNhom());
            loggerObject.setIdChuyenNganh(truongNhomFind.getChuyenNganhId());
            loggerObject.setTenChuyenNganh(tenChuyenNganh);
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        cnNhomDatnRepository.setNhomForTruongNhom(nhomDatn.getId(), monDatnId, idTruongNhom, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public List<String> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req, String idChuNhiem) {
        return cnSinhVienRepository.getStringListSinhVienNoGroup(req, idChuNhiem);
    }

    @Override
    public boolean checkHanChuNhiem() {
        return mocThoiGianCommon.checkMocThoiGianChuNhiem();
    }

    @Override
    public List<String> searchNhomDatnInput(final CnSearchNhomInputRequest request) {
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
        System.out.println(cnNhomDatnRepository.searchNhomDatnInput(request) + " aaaaaaaa");
        return cnNhomDatnRepository.searchNhomDatnInput(request);
    }

    @Override
    public CnThongTinNhomResponse getThongTinNhom(String idNhom, String coSoId) {
        return cnNhomDatnRepository.getThongTinNhom(idNhom, coSoId);
    }

    @Override
    public void CnSinhVienExport(HttpServletResponse response, final CnFindSinhVienTheoDotRequest request, String idChuNhiem) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=SinhVienTheoDot" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            CnSinhVienTheoDotExport cnNhomDatnExport = new CnSinhVienTheoDotExport();
            String dotDangKyId = commonHelper.getDotDangKyHienTai();
            if (request.getIdDotDangKy() == null) {
                if (dotDangKyId != null) {
                    request.setIdDotDangKy(dotDangKyId);
                } else {
                    request.setIdDotDangKy("");
                }
            } else if (request.getIdDotDangKy().equalsIgnoreCase("")) {
                if (dotDangKyId != null) {
                    request.setIdDotDangKy(dotDangKyId);
                } else {
                    request.setIdDotDangKy("");
                }
            }
            if (checkDotDangKyHienTai(request.getIdDotDangKy())) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
            list = cnNhomDatnRepository.findNhomDatnExport(request, request.getIdDotDangKy(), idChuNhiem, TrangThaiSinhVien.DU_DIEU_KIEN.ordinal());
            cnNhomDatnExport.employeeDetailReport(response, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void CnExportThongTinSinhVien(HttpServletResponse response, CnFindSinhVienTheoDotRequest request, String idChuNhiem) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=ThongTinSinhVien" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            CnThongTinSinhVienExport cnThongTinSinhVienExport = new CnThongTinSinhVienExport();
            String dotDangKyId = commonHelper.getDotDangKyHienTai();
            if (request.getIdDotDangKy() == null) {
                if (dotDangKyId != null) {
                    request.setIdDotDangKy(dotDangKyId);
                } else {
                    request.setIdDotDangKy("");
                }
            } else if (request.getIdDotDangKy().equalsIgnoreCase("")) {
                if (dotDangKyId != null) {
                    request.setIdDotDangKy(dotDangKyId);
                } else {
                    request.setIdDotDangKy("");
                }
            }
            listThongTinSinhVien = cnSinhVienRepository.findThongTinSinhVien(request, request.getIdDotDangKy(), idChuNhiem);
            cnThongTinSinhVienExport.exportThongTinSinhVien(response, listThongTinSinhVien);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CnSinhVienImport CnSinhVienImport(MultipartFile file, String dotDangKyId, String coSoId, String idChuNhiem) throws IOException {
        CnSinhVienImport response = new CnSinhVienImport();
        List<LoggerObject> loggerObjectList = new ArrayList<>();
        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
        String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
        try {
            if (checkDotDangKyHienTai(dotDangKyId)) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
            response.setStatus(true);
            CnSinhVienTheoDotImport sinhVienTheoDotImport = new CnSinhVienTheoDotImport();
            sinhVienList = new ArrayList<>();
            sinhVienList = sinhVienTheoDotImport.importData(file);
            ConcurrentHashMap<String, SinhVien> listSinhVienEntity = new ConcurrentHashMap();
            ConcurrentHashMap<String, NhomDatn> listNhomDatnEntity = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, String> listMaNhomCreate = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, CnCreateSinhVienTheoDotRequest> mapTruongNhomCreate = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, String> mapListMaNhomCreateTenDeTai = new ConcurrentHashMap<>();
            mapSinhVien = new ConcurrentHashMap<>();
            mapSinhVienKeyId = new ConcurrentHashMap<>();
            mapSinhVienBefore = new ConcurrentHashMap<>();
            mapNhomDatn = new ConcurrentHashMap<>();
            mapNhomDatnBefore = new ConcurrentHashMap<>();
            mapNhomDatnKeyId = new ConcurrentHashMap<>();
            mapGiangVien = new ConcurrentHashMap<>();
            mapGiangVienKeyId = new ConcurrentHashMap<>();
            mapTruongNhom = new ConcurrentHashMap<>();
            mapChuyenNganh = new ConcurrentHashMap<>();
            mapGiangVienAndDanhSachNhom = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, CnCreateSinhVienTheoDotRequest> mapSinhVienTaoNhom = new ConcurrentHashMap<>();
            addDataInMapSinhVien(mapSinhVien, mapSinhVienBefore, mapSinhVienKeyId, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapNhomDatn(mapNhomDatn, mapNhomDatnKeyId, mapNhomDatnBefore, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapGiangVien(mapGiangVien, mapGiangVienKeyId, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapTruongNhomCu(mapTruongNhom, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapChuyenNganh(mapChuyenNganh, idChuNhiem, coSoId);
            sinhVienList.parallelStream().forEach(sinhVien -> {
                SinhVien sv = mapSinhVien.get(sinhVien.getMaSinhVien());
                NhomDatn nhomDatn = mapNhomDatn.get(sinhVien.getMaNhom());
                if (nhomDatn != null) {
                    if (countSoLuongThanhVien(nhomDatn.getMaNhom())) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " quá 7 người");
                        return;
                    }
                }
                GiangVienHuongDan giangVien = null;
                if (!sinhVien.getGvhd().isEmpty() && sinhVien.getChucVu().equalsIgnoreCase("x")) {
                    giangVien = mapGiangVien.get(sinhVien.getGvhd());
                    if (giangVien == null) {
                        response.setStatus(false);
                        response.setMessage("Giảng viên " + sinhVien.getGvhd() + " không tồn tại");
                        return;
                    }
                }
                if (giangVien != null) {
                    if (countSoNhomGiangVienDangHuongDan(giangVien.getTenTaiKhoan().toLowerCase())) {
                        response.setStatus(false);
                        response.setMessage("Giảng viên " + giangVien.getTenTaiKhoan() + " đang hướng dẫn số nhóm nhiều hơn đăng ký");
                        return;
                    }
                }
                if (nhomDatn != null) {
                    if (countTruongNhom(nhomDatn.getMaNhom()) == 0) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " đang không có trưởng nhóm");
                        return;
                    }
                    if (countTruongNhom(nhomDatn.getMaNhom()) > 1) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " đang nhiều hơn 1 trưởng nhóm");
                        return;
                    }
                    if (!sinhVien.getChucVu().isEmpty()) {
//                        if (sinhVien.getGvhd().isEmpty()) {
//                            response.setStatus(false);
//                            response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " chưa có giảng viên hướng dẫn");
//                            return;
//                        }
                        nhomDatn.setTruongNhomId(sv.getId());
                        if (!sinhVien.getTenDeTai().equals("")) {
                            nhomDatn.setTenDeTai(sinhVien.getTenDeTai());
                        } else {
                            nhomDatn.setTenDeTai(null);
                        }
                        if (giangVien != null) {
                            nhomDatn.setGiangVienId(giangVien.getId());
                        } else {
                            nhomDatn.setGiangVienId(null);
                        }
//                        if (nhomDatn.getTrangThai() == TrangThaiNhom.MOI_THANH_LAP) {
//                            nhomDatn.setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
//                        }
                    }
                    sv.setNhomId(nhomDatn.getId());
                    SinhVien truongNhom = mapSinhVien.get(nhomDatn.getId());
                    if (!sv.getMaSinhVien().equals(truongNhom.getMaSinhVien())) {
                        if (!sv.getChuyenNganhId().equals(truongNhom.getChuyenNganhId())) {
                            response.setStatus(false);
                            response.setMessage("Sinh viên " + sv.getMaSinhVien() + "-" + sv.getTenSinhVien() + " có chuyên ngành khác với các thành viên trong nhóm " + nhomDatn.getMaNhom() + ".");
                            return;
                        }
                        sv.setMonDatnId(truongNhom.getMonDatnId());
                    }
                }
                if (!sinhVien.getMaNhom().isEmpty() && sinhVien.getMaNhom() != null && nhomDatn == null) {
                    if (countTruongNhom(sinhVien.getMaNhom()) == 0) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + sinhVien.getMaNhom() + " đang không có trưởng nhóm");
                        return;
                    }
                    if (countTruongNhom(sinhVien.getMaNhom()) > 1) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + sinhVien.getMaNhom() + " đang nhiều hơn 1 trưởng nhóm");
                        return;
                    }
//                    if (!sinhVien.getChucVu().isEmpty()) {
//                        if (sinhVien.getGvhd().isEmpty()) {
//                            response.setStatus(false);
//                            response.setMessage("Nhóm " + sinhVien.getMaNhom() + " đang không có giảng viên hướng dẫn");
//                            return;
//                        }
//                    }
                    mapSinhVienTaoNhom.put(sinhVien.getMaSinhVien(), sinhVien);
                    if (sinhVien.getChucVu().equalsIgnoreCase("x")) {
                        mapTruongNhomCreate.put(sinhVien.getMaNhom(), sinhVien);
                        listMaNhomCreate.put(sinhVien.getMaNhom(), sinhVien.getMaNhom());
                        mapListMaNhomCreateTenDeTai.put(sinhVien.getMaNhom(), sinhVien.getTenDeTai());
                    }
                }
                if (sinhVien.getMaNhom().isEmpty()) {
                    sv.setNhomId(null);
                }
                listSinhVienEntity.put(sv.getId(), sv);
                if (nhomDatn != null) {
                    listNhomDatnEntity.put(nhomDatn.getId(), nhomDatn);
                }
            });

            for (Map.Entry<String, String> item : listMaNhomCreate.entrySet()) {
                String maNhomCreate = item.getValue();
                for (Map.Entry<String, CnCreateSinhVienTheoDotRequest> xx : mapSinhVienTaoNhom.entrySet()) {
                    SinhVien truongNhomCreate = mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien());
                    if (xx.getValue().getMaNhom().equals(mapTruongNhomCreate.get(maNhomCreate).getMaNhom())) {
                        SinhVien sinhVienCurrent = mapSinhVien.get(xx.getValue().getMaSinhVien());
                        if (!sinhVienCurrent.getChuyenNganhId().equals(truongNhomCreate.getChuyenNganhId())) {
                            response.setStatus(false);
                            response.setMessage("Nhóm " + xx.getValue().getMaNhom() + ": Các thành viên trong nhóm không cùng 1 chuyên ngành");
                            return response;
                        }
                    }
                }
            }

            List<CnLoggerResponse> loggerResponseList = new ArrayList<>();
            if (response.getStatus() == true) {
                for (Map.Entry<String, SinhVien> item : listSinhVienEntity.entrySet()) {
                    SinhVien sinhVien = item.getValue();
                    if (sinhVien.getNhomId() == null) {
                        sinhVien.setMonDatnId(null);
                    }
                    SinhVien sinhVienBefore = mapSinhVienBefore.get(sinhVien.getMaSinhVien());
                    String maNhomCu = sinhVienBefore.getNhomId();
                    String maNhomMoi = sinhVien.getNhomId();
                    if (maNhomCu != null && maNhomMoi != null && !maNhomCu.equals(maNhomMoi)) {
                        NhomDatn nhomDatnMoi = mapNhomDatnKeyId.get(maNhomMoi);
                        NhomDatn nhomDatnCu = mapNhomDatnKeyId.get(maNhomCu);
                        if (nhomDatnMoi != null) {
                            CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                            cnLoggerResponse.setMaNhom(nhomDatnMoi.getMaNhom());
                            cnLoggerResponse.setValue(" Đã chuyển sinh viên '" + sinhVien.getTenSinhVien() + "-" + sinhVien.getMaSinhVien() + "' từ nhóm " + nhomDatnCu.getMaNhom() + " đến nhóm " + nhomDatnMoi.getMaNhom() + ".");
                            loggerResponseList.add(cnLoggerResponse);
                        }
                    }
                    if (maNhomCu != null && maNhomMoi == null) {
                        NhomDatn nhomDatnCu = mapNhomDatnKeyId.get(maNhomCu);
                        if (nhomDatnCu != null) {
                            CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                            cnLoggerResponse.setMaNhom(nhomDatnCu.getMaNhom());
                            cnLoggerResponse.setValue(" Đã xóa sinh viên '" + sinhVien.getTenSinhVien() + "-" + sinhVien.getMaSinhVien() + "' ra khỏi nhóm " + nhomDatnCu.getMaNhom() + ".");
                            loggerResponseList.add(cnLoggerResponse);
                        }
                    }
                    if (maNhomCu == null && maNhomMoi != null) {
                        NhomDatn nhomDatnMoi = mapNhomDatnKeyId.get(maNhomMoi);
                        if (nhomDatnMoi != null) {
                            CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                            cnLoggerResponse.setMaNhom(nhomDatnMoi.getMaNhom());
                            cnLoggerResponse.setValue(" Đã thêm sinh viên '" + sinhVien.getTenSinhVien() + "-" + sinhVien.getMaSinhVien() + "' vào nhóm " + nhomDatnMoi.getMaNhom() + ".");
                            loggerResponseList.add(cnLoggerResponse);
                        }
                    }
                }
                for (Map.Entry<String, NhomDatn> item : listNhomDatnEntity.entrySet()) {
                    String maGiangVienMoi = item.getValue().getGiangVienId();
                    String maGiangVienCu = mapNhomDatnBefore.get(item.getValue().getId()).getGiangVienId();
                    if (maGiangVienCu == null && maGiangVienMoi != null) {
                        GiangVienHuongDan giangVienMoi = mapGiangVienKeyId.get(maGiangVienMoi);
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã phân cho giảng viên '" + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan() + "' hướng dẫn nhóm " + item.getValue().getMaNhom() + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
                    if (maGiangVienCu != null && maGiangVienMoi == null) {
                        GiangVienHuongDan giangVienCu = mapGiangVienKeyId.get(maGiangVienCu);
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã xóa giảng viên '" + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + "' ra khỏi nhóm " + item.getValue().getMaNhom() + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
                    if (maGiangVienCu != null && maGiangVienMoi != null && !maGiangVienCu.equals(maGiangVienMoi)) {
                        GiangVienHuongDan giangVienCu = mapGiangVienKeyId.get(maGiangVienCu);
                        GiangVienHuongDan giangVienMoi = mapGiangVienKeyId.get(maGiangVienMoi);
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã chuyển giảng viên của nhóm '" + item.getValue().getMaNhom() + "' từ " + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + " sang " + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan() + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
                    String maTruongNhomMoi = item.getValue().getTruongNhomId();
                    String maTruongNhomCu = mapNhomDatnBefore.get(item.getValue().getId()).getTruongNhomId();
                    if (maTruongNhomMoi != null && maTruongNhomCu != null && !maTruongNhomMoi.equals(maTruongNhomCu)) {
                        SinhVien truongNhomCu = mapSinhVienKeyId.get(maTruongNhomCu);
                        SinhVien truongNhomMoi = mapSinhVienKeyId.get(maTruongNhomMoi);
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã chuyển trường nhóm của nhóm '" + item.getValue().getMaNhom() + "' từ " + truongNhomCu.getTenSinhVien() + "-" + truongNhomCu.getMaSinhVien() + " sang " + truongNhomMoi.getTenSinhVien() + "-" + truongNhomMoi.getMaSinhVien() + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
                    String tenDeTaiMoi = item.getValue().getTenDeTai();
                    String tenDeTaiCu = mapNhomDatnBefore.get(item.getValue().getId()).getTenDeTai();
                    if (tenDeTaiCu == null && tenDeTaiMoi != null) {
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã đổi tên đề tài của nhóm '" + item.getValue().getMaNhom() + "' thành " + tenDeTaiMoi + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
                    if (tenDeTaiCu != null && tenDeTaiMoi == null) {
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã xóa tên đề tài của nhóm '" + item.getValue().getMaNhom() + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
                    if (tenDeTaiCu != null && tenDeTaiMoi != null && !tenDeTaiCu.trim().equals(tenDeTaiMoi.trim())) {
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
                        cnLoggerResponse.setValue(" Đã đổi tên đề tài của nhóm '" + item.getValue().getMaNhom() + "' từ '" + tenDeTaiCu + "' sang '" + tenDeTaiMoi + "' " + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    }
//                    TrangThaiNhom trangThaiNhomMoi = item.getValue().getTrangThai();
//                    TrangThaiNhom trangThaiNhomCu = mapNhomDatnBefore.get(item.getValue().getId()).getTrangThai();
//                    if (trangThaiNhomMoi != trangThaiNhomCu) {
//                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
//                        cnLoggerResponse.setMaNhom(item.getValue().getMaNhom());
//                        cnLoggerResponse.setValue(" Đã cập nhật trạng thái nhóm của nhóm '" + item.getValue().getMaNhom() + "' từ 'Mới thành lập' sang 'Chủ nhiệm bộ môn đã xác nhận'" + ".");
//                        loggerResponseList.add(cnLoggerResponse);
//                    }
                }
                ConcurrentHashMap<String, NhomDatn> listNhomDatnCreate = new ConcurrentHashMap<>();

                String maNhom = nhomDatnHelper.genMaNhomTuDong();
                if (maNhom == null) {
                    response.setStatus(false);
                    response.setMessage("Tạo nhóm thất bại");
                    return response;
                }
                String array[] = maNhom.split("_");
                Long count = Long.parseLong(array[1]);

                for (Map.Entry<String, String> item : listMaNhomCreate.entrySet()) {
                    String maNhomCreate = item.getValue();
                    String tenDeTai = mapListMaNhomCreateTenDeTai.get(maNhomCreate);
                    NhomDatn nhomDatn = new NhomDatn();
                    String maNhomCreateAuto = array[0] + "_" + count++;
                    nhomDatn.setMaNhom(maNhomCreateAuto);
                    nhomDatn.setCoSoId(coSoId);
                    nhomDatn.setDotDangKyId(dotDangKyId);
                    nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(maNhomCreateAuto);
                    cnLoggerResponseNew.setValue(" Đã tạo nhóm '" + nhomDatn.getMaNhom() + "'" + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                    if (!tenDeTai.equals("")) {
                        nhomDatn.setTenDeTai(tenDeTai);
                        CnLoggerResponse cnLoggerResponseTenDeTai = new CnLoggerResponse();
                        cnLoggerResponseTenDeTai.setMaNhom(maNhomCreateAuto);
                        cnLoggerResponseTenDeTai.setValue(" Đã đặt tên đề tài cho nhóm '" + nhomDatn.getMaNhom() + "' là " + tenDeTai + ".");
                        loggerResponseList.add(cnLoggerResponseTenDeTai);
                    }
//                    for (Map.Entry<String, CnCreateSinhVienTheoDotRequest> xx : mapTruongNhomCreate.entrySet()) {
//                        if (xx.getValue().getChucVu().equalsIgnoreCase("x")) {
                    GiangVienHuongDan giangVien = mapGiangVien.get(mapTruongNhomCreate.get(maNhomCreate).getGvhd());
                    nhomDatn.setTruongNhomId(mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien()).getId());
                    CnLoggerResponse cnLoggerResponseTruongNhom = new CnLoggerResponse();
                    cnLoggerResponseTruongNhom.setMaNhom(maNhomCreateAuto);
                    cnLoggerResponseTruongNhom.setValue(" Đã phân sinh viên " + mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien()).getTenSinhVien() + "-" + mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien()).getMaSinhVien() + " làm trưởng nhóm" + ".");
                    loggerResponseList.add(cnLoggerResponseTruongNhom);
                    if (giangVien != null) {
                        nhomDatn.setGiangVienId(giangVien.getId());
                        CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                        cnLoggerResponse.setMaNhom(maNhomCreateAuto);
                        cnLoggerResponse.setValue(" Đã phân cho giảng viên '" + giangVien.getTenGvhd() + "-" + giangVien.getTenTaiKhoan() + "' hướng dẫn nhóm " + maNhomCreateAuto + ".");
                        loggerResponseList.add(cnLoggerResponse);
                    } else {
                        nhomDatn.setGiangVienId(null);
                    }
//                        }
//                    }
                    listNhomDatnCreate.put(nhomDatn.getTruongNhomId(), nhomDatn);

                }
                cnNhomDatnRepository.saveAll(listNhomDatnCreate.values());
                addDataInMapNhomDatn(mapNhomDatn, mapNhomDatnKeyId, mapNhomDatnBefore, dotDangKyId, coSoId, idChuNhiem);
                for (Map.Entry<String, String> item : listMaNhomCreate.entrySet()) {
                    String maNhomCreate = item.getValue();
                    for (Map.Entry<String, CnCreateSinhVienTheoDotRequest> xx : mapSinhVienTaoNhom.entrySet()) {
                        SinhVien truongNhomCreate = mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien());
                        truongNhomCreate.setMonDatnId(truongNhomCreate.getMonChuongTrinhId());
                        truongNhomCreate.setNhomId(listNhomDatnCreate.get(truongNhomCreate.getId()).getId());
                        if (xx.getValue().getMaNhom().equals(mapTruongNhomCreate.get(maNhomCreate).getMaNhom())) {
                            SinhVien sinhVienCurrent = mapSinhVien.get(xx.getValue().getMaSinhVien());
                            sinhVienCurrent.setNhomId(truongNhomCreate.getNhomId());
                            sinhVienCurrent.setMonDatnId(truongNhomCreate.getMonDatnId());
                            listSinhVienEntity.put(sinhVienCurrent.getId(), sinhVienCurrent);
                            NhomDatn nhomDatnMoi = mapNhomDatnKeyId.get(truongNhomCreate.getNhomId());
                            CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                            cnLoggerResponseNew.setMaNhom(nhomDatnMoi.getMaNhom());
                            cnLoggerResponseNew.setValue(" Đã thêm sinh viên '" + sinhVienCurrent.getTenSinhVien() + "-" + sinhVienCurrent.getMaSinhVien() + "' vào nhóm " + nhomDatnMoi.getMaNhom() + ".");
                            loggerResponseList.add(cnLoggerResponseNew);
                        }
                        listSinhVienEntity.put(truongNhomCreate.getId(), truongNhomCreate);
                    }
                }
                cnSinhVienRepository.saveAll(listSinhVienEntity.values());

                for (Map.Entry<String, NhomDatn> nhomDatn : mapNhomDatn.entrySet()) {
                    boolean shouldCreateStringBuffer = false;
                    StringBuffer stringBuffer = new StringBuffer();
                    SinhVien sinhVien = mapSinhVienKeyId.get(nhomDatn.getValue().getTruongNhomId());
                    ChuyenNganh chuyenNganh = mapChuyenNganh.get(sinhVien.getChuyenNganhId());
                    stringBuffer.append("Nhóm " + nhomDatn.getValue().getMaNhom() + ":");

                    for (CnLoggerResponse logger : loggerResponseList) {
                        if (nhomDatn.getValue().getMaNhom().equals(logger.getMaNhom())) {
                            shouldCreateStringBuffer = true;
                            stringBuffer.append(logger.getValue());
                        }
                    }

                    if (shouldCreateStringBuffer) {
                        try {
                            LoggerObject loggerObjectActive = writerFileCSV.
                                    createLoggerObject(stringBuffer.toString(),
                                            fileSinhVienCsv, "Thành công");
                            loggerObjectActive.setIdChuyenNganh(chuyenNganh.getId());
                            loggerObjectActive.setTenChuyenNganh(chuyenNganh.getTenChuyenNganh());
                            loggerObjectList.add(loggerObjectActive);

                            LoggerObject loggerObject = writerFileCSV.
                                    createLoggerObject(stringBuffer.toString(),
                                            pathFile, "Thành công");
                            loggerObject.setTenNhom(nhomDatn.getValue().getMaNhom());
                            loggerObject.setIdChuyenNganh(chuyenNganh.getId());
                            loggerObject.setTenChuyenNganh(chuyenNganh.getTenChuyenNganh());
                            loggerObjectList.add(loggerObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                loggerObjectList.forEach(el -> {
                    logService.sendLogMessage(el);
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi hệ thống");
            return response;
        }
        return response;
    }

    public boolean countSoLuongThanhVien(String maNhom) {
        AtomicInteger count = new AtomicInteger(0);
        sinhVienList.parallelStream().forEach(sinhVien -> {
            if (sinhVien.getMaNhom().equals(maNhom)) {
                count.set(count.get() + 1);
            }
        });
        if (count.get() > 7) {
            return true;
        }
        return false;
    }

    public int countTruongNhom(String maNhom) {
        AtomicInteger count = new AtomicInteger(0);
        sinhVienList.parallelStream().forEach(sinhVien -> {
            if (sinhVien.getMaNhom().equals(maNhom) && sinhVien.getChucVu().equalsIgnoreCase("x")) {
                count.set(count.get() + 1);
            }
        });
        return count.get();
    }

    public boolean countSoNhomGiangVienDangHuongDan(String tenTaiKhoan) {
        AtomicInteger count = new AtomicInteger(0);
//        CnGvhdAndDanhSachNhomResponse cnGvhdAndDanhSachNhomResponse = mapGiangVienAndDanhSachNhom.get(tenTaiKhoan.toLowerCase());
//        boolean allNull = cnGvhdAndDanhSachNhomResponse.getListMaNhomDangHuongDan().stream().allMatch(item -> item == null);
//
//        if (!allNull) {
//            sinhVienList.parallelStream().forEach(sinhVien -> {
//                if (sinhVien.getGvhd().equals(tenTaiKhoan) && sinhVien.getChucVu().equalsIgnoreCase("x")) {
//                    if (!cnGvhdAndDanhSachNhomResponse.getListMaNhomDangHuongDan().contains(sinhVien.getMaNhom())) {
//                        count.set(count.get() + 1);
//                    }
//                }
//            });
//            if (count.get() + cnGvhdAndDanhSachNhomResponse.getListMaNhomDangHuongDan().size()
//                    > mapGiangVien.get(tenTaiKhoan).getSoNhomHuongDanToiDa()) {
//                return true;
//            }
//        } else {
        sinhVienList.parallelStream().forEach(sinhVien -> {
            if (sinhVien.getGvhd().equals(tenTaiKhoan) && sinhVien.getChucVu().equalsIgnoreCase("x")) {
                count.set(count.get() + 1);
            }
        });
        if (count.get() > mapGiangVien.get(tenTaiKhoan).getSoNhomHuongDanToiDa()) {
            return true;
        }
//        }
        return false;
    }

    public void addDataInMapSinhVien(ConcurrentHashMap<String, SinhVien> mapAll, ConcurrentHashMap<String, SinhVien> mapAllBefore, ConcurrentHashMap<String, SinhVien> mapAllKeyId, String dotDangKyId, String
            coSoId, String idChuNhiem) {
        List<SinhVien> sinhVienList = cnSinhVienRepository.getALlSinhVienByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        List<SinhVien> truongNhomList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        List<SinhVien> truongNhomCuList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getAllPutMapSinhVien(mapAll, mapAllBefore, mapAllKeyId, sinhVienList);
        getTruongNhomPutMapSinhVien(mapAll, truongNhomList);
        getTruongNhomCuPutMapSinhVien(mapAll, truongNhomCuList);
    }

    public void addDataInMapTruongNhomCu(ConcurrentHashMap<String, SinhVien> mapAll, String dotDangKyId, String
            coSoId, String idChuNhiem) {
        List<SinhVien> truongNhomCuList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getTruongNhomCuPutMapSinhVien(mapAll, truongNhomCuList);
    }

    public void addDataInMapNhomDatn(ConcurrentHashMap<String, NhomDatn> mapAll, ConcurrentHashMap<String, NhomDatn> mapAllKeyId, ConcurrentHashMap<String, NhomDatn> mapAllBefore, String dotDangKyId, String
            coSoId, String idChuNhiem) {
        List<NhomDatn> nhomDatnList = cnNhomDatnRepository.getAllNhomDatnByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getALlPutMapNhomDatn(mapAll, mapAllKeyId, mapAllBefore, nhomDatnList);
    }

    public void addDataInMapGiangVien(ConcurrentHashMap<String, GiangVienHuongDan> mapAll, ConcurrentHashMap<String, GiangVienHuongDan> mapAllBefore, String
            dotDangKyId, String coSoId, String idChuNhiem) {
        List<GiangVienHuongDan> giangVienHuongDanList = cnGiangVienHuongDanReponsitory.getAllGiangVienByIdCoSoAndByIdDotDangKy(dotDangKyId, coSoId, idChuNhiem);
        getALlPutMapGiangVien(mapAll, mapAllBefore, giangVienHuongDanList);
    }

    public void addDataInMapGiangVienAndDanhSachNhom(ConcurrentHashMap<String, CnGvhdAndDanhSachNhomResponse> mapAll, String
            dotDangKyId, String coSoId) {
        List<CnGiangVienHuongDanDanhSachNhomResponse> gvhdDanhSachNhom = cnGiangVienHuongDanReponsitory.getGiangVienAndDanhSachNhomDangHuongDan(dotDangKyId, coSoId);
        getALlPutMapGiangVienAndDanhSachNhom(mapAll, gvhdDanhSachNhom);
    }

    public void getAllPutMap
            (ConcurrentHashMap<String, String> mapSimple, List<SimpleEntityProj> getAllSimpleEntityProjs) {
        for (SimpleEntityProj simpleEntityProj : getAllSimpleEntityProjs) {
            mapSimple.put(simpleEntityProj.getName(), simpleEntityProj.getId());
        }
    }

    public void addDataInMapChuyenNganh(ConcurrentHashMap<String, ChuyenNganh> mapAll, String idChuNhiem, String coSoId) {
        List<ChuyenNganh> chuyenNganhList = cnChuyenNganhRepository.getAllChuyenNganhEntityByIdChuNhiem(coSoId, idChuNhiem);
        getAllPutMapChuyenNganh(mapAll, chuyenNganhList);
    }

    public void getAllPutMapChuyenNganh
            (ConcurrentHashMap<String, ChuyenNganh> mapSimple, List<ChuyenNganh> chuyenNganhList) {
        for (ChuyenNganh chuyenNganh : chuyenNganhList) {
            mapSimple.put(chuyenNganh.getId(), chuyenNganh);
        }
    }

    public void getAllPutMapSinhVien
            (ConcurrentHashMap<String, SinhVien> mapSimple, ConcurrentHashMap<String, SinhVien> mapSimpleBefore, ConcurrentHashMap<String, SinhVien> mapSimpleKeyId, List<SinhVien> listSinhVien) {
        for (SinhVien xx : listSinhVien) {
            mapSimple.put(xx.getMaSinhVien(), xx);
            mapSimpleKeyId.put(xx.getId(), xx);
            SinhVien sinhVienNew = formUtils.convertToObject(SinhVien.class, xx);
            mapSimpleBefore.put(sinhVienNew.getMaSinhVien(), sinhVienNew);
        }
    }

    public void getTruongNhomPutMapSinhVien
            (ConcurrentHashMap<String, SinhVien> mapSimple, List<SinhVien> listTruongNhom) {
        for (SinhVien xx : listTruongNhom) {
            mapSimple.put(xx.getNhomId(), xx);
        }
    }

    public void getTruongNhomCuPutMapSinhVien
            (ConcurrentHashMap<String, SinhVien> mapSimple, List<SinhVien> listTruongNhom) {
        for (SinhVien xx : listTruongNhom) {
            mapSimple.put(xx.getMaSinhVien(), xx);
        }
    }

    public void getALlPutMapNhomDatn
            (ConcurrentHashMap<String, NhomDatn> mapSimple, ConcurrentHashMap<String, NhomDatn> mapSimplekeyId, ConcurrentHashMap<String, NhomDatn> mapSimpleBefore, List<NhomDatn> listNhomDatn) {
        for (NhomDatn xx : listNhomDatn) {
            mapSimple.put(xx.getMaNhom(), xx);
            mapSimplekeyId.put(xx.getId(), xx);
            NhomDatn nhomDatnNew = formUtils.convertToObject(NhomDatn.class, xx);
            mapSimpleBefore.put(nhomDatnNew.getId(), nhomDatnNew);
        }
    }

    public void getALlPutMapGiangVien
            (ConcurrentHashMap<String, GiangVienHuongDan> mapSimple, ConcurrentHashMap<String, GiangVienHuongDan> mapSimpleBefore, List<GiangVienHuongDan> listGiangVien) {
        for (GiangVienHuongDan xx : listGiangVien) {
            mapSimple.put(xx.getTenTaiKhoan().toLowerCase(), xx);
            GiangVienHuongDan giangVienHuongDanNew = formUtils.convertToObject(GiangVienHuongDan.class, xx);
            mapSimpleBefore.put(giangVienHuongDanNew.getId(), giangVienHuongDanNew);
        }
    }

    public void getALlPutMapGiangVienAndDanhSachNhom
            (ConcurrentHashMap<String, CnGvhdAndDanhSachNhomResponse> mapSimple, List<CnGiangVienHuongDanDanhSachNhomResponse> listGvhdDSN) {
        for (CnGiangVienHuongDanDanhSachNhomResponse xx : listGvhdDSN) {
            String danhSachNhom = xx.getDanhSachNhom();
            String[] nhomArray = new String[7];
            if (danhSachNhom != null && !danhSachNhom.isEmpty()) {
                nhomArray = danhSachNhom.split(", ");
            }
            List<String> danhSachNhomList = new ArrayList<>();
            Collections.addAll(danhSachNhomList, nhomArray);
            CnGvhdAndDanhSachNhomResponse cnGvhdAndDanhSachNhomResponse = new CnGvhdAndDanhSachNhomResponse();
            cnGvhdAndDanhSachNhomResponse.setTenTaiKhoan(xx.getTenTaiKhoan());
            cnGvhdAndDanhSachNhomResponse.setListMaNhomDangHuongDan(danhSachNhomList);
            if (danhSachNhomList != null && danhSachNhomList.size() > 0) {
                mapSimple.put(xx.getTenTaiKhoan().toLowerCase(), cnGvhdAndDanhSachNhomResponse);
            }
        }
    }

}
