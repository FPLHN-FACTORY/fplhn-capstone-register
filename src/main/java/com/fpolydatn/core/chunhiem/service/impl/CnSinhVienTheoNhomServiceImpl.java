package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnLoggerResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.repository.CnChuyenNganhRepository;
import com.fpolydatn.core.chunhiem.repository.CnDotDangKyRepository;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.RabbitLogger;
import com.fpolydatn.core.common.log.LogObject;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.core.common.CommonHelper;
import com.fpolydatn.core.common.MocThoiGianCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Validated
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
    private RabbitLogger rabbitLogger;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CnChuyenNganhRepository cnChuyenNganhRepository;

    @Override
    public PageableObject<CnSinhVienTheoNhomResponse> searchByIdNhom(final CnFindSinhVienTheoNhomRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoNhomResponse> res = cnSinhVienTheoNhomRepository.searchSinhVien(request, pageable);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String idNhom, String maSinhViens) {
        List<LogObject> logObjectList = new ArrayList<>();
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
        int soThanhVienTrongNhom = cnNhomDatnRepository.getSoLuongThanhVienNhom(idNhom, commonHelper.getIdDotDangKyHienTai());
        String idTruongNhom = cnNhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getIdDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(idTruongNhom, commonHelper.getIdDotDangKyHienTai());
        String idNhomMonDatn = cnMonDatnRepository.findIdNhomMonDatn(idMonDatn);
        if ((soThanhVienTrongNhom + soSinhVienDangLuaChon) > 7) {
            throw new RestApiException(Message.VUOT_QUA_SO_LUONG_CHO_PHEP);
        }
        List<CnLoggerResponse> loggerResponseList = new ArrayList<>();

        for (String maSinhVien : listMaSinhVien) {
            String idMonChuongTrinh = cnSinhVienTheoNhomRepository.findIdMonChuongTrinh(maSinhVien, commonHelper.getIdDotDangKyHienTai());
            String check = cnMonDatnRepository.checkMonChuongTrinh(idNhomMonDatn, idMonChuongTrinh);
            if (check == null) {
                throw new RestApiException(Message.MON_KHONG_TUONG_DUONG);
            }

            cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom, maSinhVien, idMonDatn, commonHelper.getIdDotDangKyHienTai());
            CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
            cnLoggerResponse.setMaNhom(nhomDatnFind.get().getMaNhom());
            cnLoggerResponse.setValue("" + maSinhVien + ", ");
            loggerResponseList.add(cnLoggerResponse);

        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Đã thêm những sinh viên sau vào nhóm: '");
        loggerResponseList.forEach(logger -> {
            stringBuffer.append(logger.getValue());
        });
        SinhVien sinhVienFind = cnSinhVienTheoNhomRepository.getIdSinhVienByMaSinhVien(listMaSinhVien[0], commonHelper.getIdDotDangKyHienTai());
        ChuyenNganh chuyenNganhFind = cnChuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        try {
            LogObject logObjectActive = writerFileCSV.
                    createLoggerObject(stringBuffer.toString(),
                            fileSinhVienCsv, "Thành công");
            logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
            logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logObjectList.add(logObjectActive);

            LogObject logObject = writerFileCSV.
                    createLoggerObject(stringBuffer.toString(),
                            pathFile, "Thành công");
            logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logObject.setIdChuyenNganh(chuyenNganhFind.getId());
            logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logObjectList.add(logObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logObjectList.forEach(el -> {
            rabbitLogger.sendLogMessage(el);
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
        return cnSinhVienTheoNhomRepository.countListSinhVien(idNhom, coSoId, commonHelper.getIdDotDangKyHienTai());
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
        Integer soThanhVien = getSizeListSinhVien(idNhom, coSoId);
        if(soThanhVien <= 4) {
            throw new RestApiException(Message.NHOM_CON_4_THANH_VIEN_NEU_XOA_NHOM_SE_THANH_NHOM_KHONG_HOP_LE);
        }
        cnSinhVienTheoNhomRepository.deleteSinhVienOutGroup(maSinhVien, commonHelper.getIdDotDangKyHienTai());
        if (soThanhVien == 0) {
            cnNhomDatnRepository.deleteById(idNhom);
        }
        SinhVien sinhVienFind = cnSinhVienTheoNhomRepository.getIdSinhVienByMaSinhVien(maSinhVien, commonHelper.getIdDotDangKyHienTai());
        ChuyenNganh chuyenNganhFind = cnChuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LogObject logObjectActive = writerFileCSV.
                    createLoggerObject("Đã xóa sinh viên '" + sinhVienFind.getMaSinhVien() + "-" + sinhVienFind.getTenSinhVien() + "' ra khỏi nhóm " + nhomDatnFind.get().getMaNhom(),
                            fileSinhVienCsv, "Thành công");
            logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
            logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            rabbitLogger.sendLogMessage(logObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LogObject logObject = writerFileCSV.
                    createLoggerObject("Đã xóa sinh viên '" + sinhVienFind.getMaSinhVien() + "-" + sinhVienFind.getTenSinhVien() + "' ra khỏi nhóm " + nhomDatnFind.get().getMaNhom(),
                            pathFile, "Thành công");
            logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logObject.setIdChuyenNganh(chuyenNganhFind.getId());
            logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            rabbitLogger.sendLogMessage(logObject);
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
        Optional<GiangVienHuongDan> giangVienHuongDanCu = null;
        if (nhomDatnFind.get().getGiangVienId() != null) {
            giangVienHuongDanCu = cnGiangVienHuongDanReponsitory.findById(nhomDatnFind.get().getGiangVienId());
        }
        String idGiangVien = cnGiangVienHuongDanReponsitory.getIdFromGiangVienId(tenTaiKhoan, commonHelper.getIdDotDangKyHienTai());
        if (idGiangVien == null) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        Optional<GiangVienHuongDan> giangVienHuongDanMoi = cnGiangVienHuongDanReponsitory.findById(idGiangVien);
        if (cnNhomDatnRepository.getSoNhomGiangVienHuongDan(idGiangVien, commonHelper.getIdDotDangKyHienTai()) ==
                commonHelper.getSoNhomGiangVienDangHuongDan(idGiangVien)) {
            throw new RestApiException(Message.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM);
        }
        String idTruongNhom = cnNhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getIdDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(idTruongNhom, commonHelper.getIdDotDangKyHienTai());
        String check = cnGiangVienHuongDanReponsitory.checkIdGiangVienAndMonDatn(idGiangVien, idMonDatn, commonHelper.getIdDotDangKyHienTai());
        if (check == null) {
            throw new RestApiException(Message.GIANG_VIEN_KHONG_DAY_MON_NAY);
        }
        cnNhomDatnRepository.addGvhd(idGiangVien, idNhom, commonHelper.getIdDotDangKyHienTai());
        SinhVien sinhVienFind = cnSinhVienTheoNhomRepository.findById(nhomDatnFind.get().getTruongNhomId()).get();
        ChuyenNganh chuyenNganhFind = cnChuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        if (giangVienHuongDanCu == null && giangVienHuongDanMoi.isPresent()) {
            try {
                String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
                LogObject logObjectActive = writerFileCSV.
                        createLoggerObject("Đã phân công giảng viên cho nhóm '" + nhomDatnFind.get().getMaNhom() + "' là: " + giangVienHuongDanMoi.get().getTenTaiKhoan() + " - " + giangVienHuongDanMoi.get().getTenGvhd(),
                                fileSinhVienCsv, "Thành công");
                logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
                logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Đã phân công giảng viên cho nhóm '" + nhomDatnFind.get().getMaNhom() + "' là: " + giangVienHuongDanMoi.get().getTenTaiKhoan() + " - " + giangVienHuongDanMoi.get().getTenGvhd(),
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (giangVienHuongDanCu != null && giangVienHuongDanCu.isPresent() && giangVienHuongDanMoi.isPresent() && !giangVienHuongDanCu.get().getId().equals(giangVienHuongDanMoi.get().getId())) {
            try {
                String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
                LogObject logObjectActive = writerFileCSV.
                        createLoggerObject("Đã đổi giảng viên cho nhóm '" + nhomDatnFind.get().getMaNhom() + "' từ " + giangVienHuongDanCu.get().getTenTaiKhoan() + " - " + giangVienHuongDanCu.get().getTenGvhd() + " sang " + giangVienHuongDanMoi.get().getTenTaiKhoan() + " - " + giangVienHuongDanMoi.get().getTenGvhd(),
                                fileSinhVienCsv, "Thành công");
                logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
                logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Đã đổi giảng viên cho nhóm '" + nhomDatnFind.get().getMaNhom() + "' từ " + giangVienHuongDanCu.get().getTenTaiKhoan() + " - " + giangVienHuongDanCu.get().getTenGvhd() + " sang " + giangVienHuongDanMoi.get().getTenTaiKhoan() + " - " + giangVienHuongDanMoi.get().getTenGvhd(),
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
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
        Optional<SinhVien> truongNhomMoi = cnSinhVienTheoNhomRepository.findById(idCaptain);
        Optional<SinhVien> truongNhomCu = cnSinhVienTheoNhomRepository.findById(nhomDatnFind.get().getTruongNhomId());
        cnNhomDatnRepository.changeCaptainByTruongNhomId(idCaptain, idNhom, commonHelper.getIdDotDangKyHienTai());
        ChuyenNganh chuyenNganhFind = cnChuyenNganhRepository.findById(truongNhomMoi.get().getChuyenNganhId()).get();

        if (!truongNhomMoi.get().getId().equals(truongNhomCu.get().getId())) {
            try {
                String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
                LogObject logObjectActive = writerFileCSV.
                        createLoggerObject("Đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + " từ '" + truongNhomCu.get().getTenSinhVien() + "-" + truongNhomCu.get().getMaSinhVien() + "' thành " + truongNhomMoi.get().getTenSinhVien() + "-" + truongNhomMoi.get().getTenSinhVien(),
                                fileSinhVienCsv, "Thành công");
                logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
                logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + " từ '" + truongNhomCu.get().getTenSinhVien() + "-" + truongNhomCu.get().getMaSinhVien() + "' thành " + truongNhomMoi.get().getTenSinhVien() + "-" + truongNhomMoi.get().getTenSinhVien(),
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
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
        String idCapTainOld = cnSinhVienTheoNhomRepository.getIdByMaSinhVienAndIdNhom(maSinhVien, idNhom, commonHelper.getIdDotDangKyHienTai());
        String idCapTainNew = cnSinhVienTheoNhomRepository.getListSinhVienNotCaptain(idCapTainOld, idNhom, commonHelper.getIdDotDangKyHienTai()).get(0).getId();

        SinhVien truongNhomCu = cnSinhVienTheoNhomRepository.findById(idCapTainOld).get();
        SinhVien truongNhomMoi = cnSinhVienTheoNhomRepository.findById(idCapTainNew).get();
        ChuyenNganh chuyenNganhFind = cnChuyenNganhRepository.findById(truongNhomMoi.getChuyenNganhId()).get();
        cnNhomDatnRepository.changeCaptainByTruongNhomId(idCapTainNew, idNhom, commonHelper.getIdDotDangKyHienTai());
        try {
            String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            LogObject logObjectActive = writerFileCSV.
                    createLoggerObject("Đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + " từ '" + truongNhomCu.getTenSinhVien() + "-" + truongNhomCu.getMaSinhVien() + "' thành " + truongNhomMoi.getTenSinhVien() + "-" + truongNhomMoi.getTenSinhVien(),
                            fileSinhVienCsv, "Thành công");
            logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
            logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            rabbitLogger.sendLogMessage(logObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LogObject logObject = writerFileCSV.
                    createLoggerObject("Đã đổi trưởng nhóm của nhóm '" + nhomDatnFind.get().getMaNhom() + " từ '" + truongNhomCu.getTenSinhVien() + "-" + truongNhomCu.getMaSinhVien() + "' thành " + truongNhomMoi.getTenSinhVien() + "-" + truongNhomMoi.getTenSinhVien(),
                            pathFile, "Thành công");
            logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
            logObject.setIdChuyenNganh(chuyenNganhFind.getId());
            logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            rabbitLogger.sendLogMessage(logObject);
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
        String idOldCaptain = cnNhomDatnRepository.findIDTruongNhom(idGroup, commonHelper.getIdDotDangKyHienTai());
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
        List<SinhVien> listStudentNotCaptain = cnSinhVienTheoNhomRepository.getListSinhVienNotCaptain(idCaptain, idGroup, commonHelper.getIdDotDangKyHienTai());
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
        String tenDeTaiCu = nhomDatnFind.get().getTenDeTai();
        if (!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        if (tenDeTai.trim().length() > 255) {
            throw new RestApiException(Message.DO_DAI_KHONG_QUA_255_KY_TU);
        }
        nhomDatnFind.get().setTenDeTai(tenDeTai);
        SinhVien sinhVienFind = cnSinhVienTheoNhomRepository.findById(nhomDatnFind.get().getTruongNhomId()).get();
        ChuyenNganh chuyenNganhFind = cnChuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        if (tenDeTaiCu == null && tenDeTai != null) {
            try {
                String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
                LogObject logObjectActive = writerFileCSV.
                        createLoggerObject("Đã đặt tên đề tài của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + tenDeTai,
                                fileSinhVienCsv, "Thành công");
                logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
                logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Đã đặt tên đề tài của nhóm '" + nhomDatnFind.get().getMaNhom() + "' thành " + tenDeTai,
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (tenDeTaiCu != null && tenDeTai != null && !tenDeTai.equals(tenDeTaiCu)) {
            try {
                String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
                LogObject logObjectActive = writerFileCSV.
                        createLoggerObject("Đã đổi tên đề tài của nhóm '" + nhomDatnFind.get().getMaNhom() + "' từ " + tenDeTaiCu + " thành " + tenDeTai,
                                fileSinhVienCsv, "Thành công");
                logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
                logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObjectActive);

                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Đã đổi tên đề tài của nhóm '" + nhomDatnFind.get().getMaNhom() + "' từ " + tenDeTaiCu + " thành " + tenDeTai,
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatnFind.get().getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.sendLogMessage(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
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