package com.fpolydatn.core.giangvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.repository.GvNhomDatnRepository;
import com.fpolydatn.core.giangvien.repository.GvSinhVienRepository;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * @author thangdt
 */

@Service
@Validated
public class GvNhomDatnServiceImpl implements GvNhomDatnService {

    @Autowired
    private GvNhomDatnRepository nhomDatnRepository;

    @Autowired
    private GvSinhVienRepository gvSinhVienRepository;

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    @Override
    public PageableObject<GvNhomDatnResponse> fillAllNhomDatn(final GvFindNhomDatnRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GvNhomDatnResponse> responses = nhomDatnRepository.fillAllNhomDatn(request, pageable);
        return new PageableObject(responses);
    }

    @Override
    public NhomDatn findById(String maNhom) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(maNhom);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return nhomDatnCheck.get();
    }

    @Override
    public NhomDatn updateTenDeTai(@Valid final GvUpdateTenDeTaiRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() == TrangThaiNhom.MOI_THANH_LAP && nhomDatnCheck.get().getTrangThai() == TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN && nhomDatnCheck.get().getTrangThai() == TrangThaiNhom.CNBM_CHOT) {
            throw new RestApiException(Message.DONT_UPDATE_TEN_DE_TAI);
        }
        String tenDeTaiMoi = request.getTenDeTai();
        String tenDeTaiCu = nhomDatnCheck.get().getTenDeTai();
        nhomDatnCheck.get().setTenDeTai(request.getTenDeTai());
        SinhVien sinhVienFind = gvSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        ChuyenNganh chuyenNganhFind = chuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Giảng viên đã đổi tên đề tài nhóm '" + nhomDatnCheck.get().getMaNhom() + "' từ '" + tenDeTaiCu + "' sang '"
                                    + tenDeTaiMoi + "'",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
            loggerObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Giảng viên đã đổi tên đề tài nhóm '" + nhomDatnCheck.get().getMaNhom() + "' từ '" + tenDeTaiCu + "' sang '"
                                    + tenDeTaiMoi + "'",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(chuyenNganhFind.getId());
            loggerObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public NhomDatn pheDuyetNhomDatn(GvChotNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN && nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.MOI_THANH_LAP) {
            throw new RestApiException(Message.DONT_PHE_DUYET_NHOM_DATN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.GIANG_VIEN_DA_LIEN_HE);
        SinhVien sinhVienFind = gvSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        ChuyenNganh chuyenNganhFind = chuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Giảng viên đã cập nhật trạng thái của nhóm " + nhomDatnCheck.get().getMaNhom() + " từ 'Mới thành lập'" + " thành 'Giảng viên đã liên hệ'.",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
            loggerObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Giảng viên đã cập nhật trạng thái của nhóm " + nhomDatnCheck.get().getMaNhom() + " từ 'Mới thành lập'" + " thành 'Giảng viên đã liên hệ'.",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(chuyenNganhFind.getId());
            loggerObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public NhomDatn chotDeTaiNhomDatn(GvChotNhomDatnRequest request) {
        Optional<NhomDatn> nhomDatnCheck = nhomDatnRepository.findById(request.getMaNhom());
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.GIANG_VIEN_DA_LIEN_HE && nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.CAN_SUA) {
            throw new RestApiException(Message.DONT_CHOT_DE_TAI_NHOM_DATN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.GVHD_CHOT);
        SinhVien sinhVienFind = gvSinhVienRepository.findById(nhomDatnCheck.get().getTruongNhomId()).get();
        ChuyenNganh chuyenNganhFind = chuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Giảng viên đã cập nhật trạng thái của nhóm " + nhomDatnCheck.get().getMaNhom() + " từ 'Giảng viên đã liên hệ'" + " thành 'Giảng viên đã chốt đề tài'.",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
            loggerObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Giảng viên đã cập nhật trạng thái của nhóm " + nhomDatnCheck.get().getMaNhom() + " từ 'Giảng viên đã liên hệ'" + " thành 'Giảng viên đã chốt đề tài'.",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(nhomDatnCheck.get().getMaNhom());
            loggerObject.setIdChuyenNganh(chuyenNganhFind.getId());
            loggerObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return nhomDatnRepository.save(nhomDatnCheck.get());
    }

    @Override
    public boolean mocThoiGianGiangVienHuongDan() {
        return mocThoiGianCommon.checkMocThoiGianGiangVien();
    }

}
