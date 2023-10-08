package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.repository.CnDotDangKyRepository;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CnSinhVienTheoNhomServiceImpl implements CnSinhVienTheoNhomService {

    @Autowired
    private CnSinhVienRepository cnSinhVienTheoNhomRepository;

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    private CnDotDangKyRepository cnDotDangKyRepository;

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private CnMonDatnRepository cnMonDatnRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Override
    public PageableObject<CnSinhVienTheoNhomResponse> searchByIdNhom(final CnFindSinhVienTheoNhomRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoNhomResponse> res = cnSinhVienTheoNhomRepository.searchSinhVien(request, pageable);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String idNhom, String maSinhViens) {
        List<LoggerObject> loggerObjectList = new ArrayList<>();
        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
        String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        String listMaSinhVien[] = maSinhViens.split("-");
        int soSinhVienDangLuaChon = listMaSinhVien.length;
        int soThanhVienTrongNhom = cnNhomDatnRepository.getSoLuongThanhVienNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idTruongNhom = cnNhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(idTruongNhom, commonHelper.getDotDangKyHienTai());
        String idNhomMonDatn = cnMonDatnRepository.findIdNhomMonDatn(idMonDatn);
        if ((soThanhVienTrongNhom + soSinhVienDangLuaChon) > 7) {
            throw new RestApiException(Message.VUOT_QUA_SO_LUONG_CHO_PHEP);
        }
        for (String maSinhVien : listMaSinhVien) {
            String idMonChuongTrinh = cnSinhVienTheoNhomRepository.findIdMonChuongTrinh(maSinhVien, commonHelper.getDotDangKyHienTai());
            String check = cnMonDatnRepository.checkMonChuongTrinh(idNhomMonDatn, idMonChuongTrinh);
            if (check == null) {
                throw new RestApiException(Message.MON_KHONG_TUONG_DUONG);
            }
            String idSinhVien = cnSinhVienTheoNhomRepository.getIdSinhVienByMaSinhVien(maSinhVien, commonHelper.getDotDangKyHienTai());
            cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom, maSinhVien, idMonDatn, commonHelper.getDotDangKyHienTai());
            try {
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Chủ nhiệm bộ môn đã thêm sinh viên '" + maSinhVien + "' vào nhóm " + nhomDatnFind.get().getMaNhom(),
                                fileSinhVienCsv, "Thành công");
                loggerObjectList.add(loggerObjectActive);

                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Chủ nhiệm bộ môn đã thêm sinh viên '" + maSinhVien + "' vào nhóm " + nhomDatnFind.get().getMaNhom(),
                                pathFile, "Thành công");
                loggerObject.setTenNhom(nhomDatnFind.get().getMaNhom());
                loggerObjectList.add(loggerObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loggerObjectList.forEach(el -> {
            logService.sendLogMessage(el);
        });
        return true;
    }

    @Override
    public Integer getSizeListSinhVien(String idNhom, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            return null;
        }
        return cnSinhVienTheoNhomRepository.countListSinhVien(idNhom, coSoId, commonHelper.getDotDangKyHienTai());
    }

    @Override
    public boolean deleteFromIdSinhVien(String idNhom, String maSinhVien, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        cnSinhVienTheoNhomRepository.deleteSinhVienOutGroup(maSinhVien, commonHelper.getDotDangKyHienTai());
        if (getSizeListSinhVien(idNhom, coSoId) == 0) {
            cnNhomDatnRepository.deleteById(idNhom);
        }
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã xóa sinh viên '" + maSinhVien + "' ra khỏi nhóm " + nhomDatnFind.get().getMaNhom(),
                            fileSinhVienCsv, "Thành công");
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã xóa sinh viên '" + maSinhVien + "' ra khỏi nhóm " + nhomDatnFind.get().getMaNhom(),
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean addGvhd(String tenTaiKhoan, String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        String idGiangVien = cnGiangVienHuongDanReponsitory.getIdFromGiangVienId(tenTaiKhoan, commonHelper.getDotDangKyHienTai());
        if (idGiangVien == null) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        if (cnNhomDatnRepository.getSoNhomGiangVienHuongDan(idGiangVien, commonHelper.getDotDangKyHienTai()) ==
                commonHelper.getSoNhomGiangVienDangHuongDan(idGiangVien)) {
            throw new RestApiException(Message.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM);
        }
        String idTruongNhom = cnNhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(idTruongNhom, commonHelper.getDotDangKyHienTai());
        String check = cnGiangVienHuongDanReponsitory.checkIdGiangVienAndMonDatn(idGiangVien, idMonDatn, commonHelper.getDotDangKyHienTai());
        if (check == null) {
            throw new RestApiException(Message.GIANG_VIEN_KHONG_DAY_MON_NAY);
        }
        cnNhomDatnRepository.addGvhd(idGiangVien, idNhom, commonHelper.getDotDangKyHienTai());
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi giảng viên của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + tenTaiKhoan,
                            fileSinhVienCsv, "Thành công");
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi giảng viên của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + tenTaiKhoan,
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean changeCaptain(String idCaptain, String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        Optional<SinhVien> sinhVienFind = cnSinhVienTheoNhomRepository.findById(idCaptain);
        cnNhomDatnRepository.changeCaptainByTruongNhomId(idCaptain, idNhom, commonHelper.getDotDangKyHienTai());
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + sinhVienFind.get().getTenSinhVien() + "-" + sinhVienFind.get().getMaSinhVien(),
                            fileSinhVienCsv, "Thành công");
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + sinhVienFind.get().getTenSinhVien() + "-" + sinhVienFind.get().getMaSinhVien(),
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean changeCaptainToOther(String maSinhVien, String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        String idCapTainOld = cnSinhVienTheoNhomRepository.getIdByMaSinhVienAndIdNhom(maSinhVien, idNhom, commonHelper.getDotDangKyHienTai());
        String idCapTainNew = cnSinhVienTheoNhomRepository.getListSinhVienNotCaptain(idCapTainOld, idNhom, commonHelper.getDotDangKyHienTai()).get(0).getId();
        cnNhomDatnRepository.changeCaptainByTruongNhomId(idCapTainNew, idNhom, commonHelper.getDotDangKyHienTai());
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + maSinhVien,
                            fileSinhVienCsv, "Thành công");
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + maSinhVien,
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public SinhVien getOldCaptain(String idGroup) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idGroup);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            return null;
        }
        String idOldCaptain = cnNhomDatnRepository.findIDTruongNhom(idGroup, commonHelper.getDotDangKyHienTai());
        Optional<SinhVien> captain = cnSinhVienTheoNhomRepository.findById(idOldCaptain);
        return captain.get();
    }

    @Override
    public List<SinhVien> getStudentNotCaptain(String idCaptain, String idGroup) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idGroup);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            return null;
        }
        List<SinhVien> listStudentNotCaptain = cnSinhVienTheoNhomRepository.getListSinhVienNotCaptain(idCaptain, idGroup, commonHelper.getDotDangKyHienTai());
        return listStudentNotCaptain;
    }

    @Override
    public List<DotDangKy> getListDotDangKy() {
        return cnDotDangKyRepository.findAll();
    }

    @Override
    public List<CnSinhVienSearchResponse> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req, String idChuNhiem) {
        String idNhom = req.getIdNhom();
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            return null;
        }
        Optional<NhomDatn> nhomDatn = cnNhomDatnRepository.findById(idNhom);
        Optional<SinhVien> truongNhom = cnSinhVienTheoNhomRepository.findById(nhomDatn.get().getTruongNhomId());
        Optional<MonDatn> nhomMonDatn = cnMonDatnRepository.findById(truongNhom.get().getMonDatnId());
        req.setNhomMonDatnId(nhomMonDatn.get().getNhomMonDatnId());
        return cnSinhVienTheoNhomRepository.getListSinhVienNoGroup(req, idChuNhiem);
    }

    @Override
    public NhomDatn doiTenDeTai(String idNhom, String tenDeTai) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        if (tenDeTai.trim().length() > 255) {
            throw new RestApiException(Message.DO_DAI_KHONG_QUA_255_KY_TU);
        }
        nhomDatnFind.get().setTenDeTai(tenDeTai);
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi tên đề tài của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + tenDeTai,
                            fileSinhVienCsv, "Thành công");
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã đổi tên đề tài của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + tenDeTai,
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return cnNhomDatnRepository.save(nhomDatnFind.get());
    }

    public Boolean checkDotDangKy(String dotDangKyId) {
        Optional<DotDangKy> dotDangKyFind = dotDangKyRepository.findById(dotDangKyId);
        if (!dotDangKyFind.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime >= dotDangKyFind.get().getNgayBatDau() && currentTime <= dotDangKyFind.get().getNgayKetThuc()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkDotDangKyHienTai(String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return checkDotDangKy(nhomDatnFind.get().getDotDangKyId());
    }

}