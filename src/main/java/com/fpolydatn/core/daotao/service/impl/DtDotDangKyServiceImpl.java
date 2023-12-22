package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtBaseDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtFindDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailDotDangKyResponse;
import com.fpolydatn.core.daotao.model.response.DtDotDangKyResponse;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.repository.DtHocKyRepository;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.core.common.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author SonPT
 */

@Validated
@Service
public class DtDotDangKyServiceImpl implements DtDotDangKyService {

    @Autowired
    private DtDotDangKyRepository dtDotDangKyRepository;

    @Autowired
    private DtHocKyRepository dtHocKyRepository;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    @Override
    public PageableObject<DtDotDangKyResponse> findByCoSo(DtFindDotDangKyRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtDotDangKyResponse> response = dtDotDangKyRepository.findByCoSo(request, new Date().getTime(), pageable);
        return new PageableObject(response);
    }

    @Override
    public DotDangKy create(@Valid final DtCreateDotDangKyRequest createDotDangKyRequest) {
        DtBaseDotDangKyRequest request = new DtCreateDotDangKyRequest();
        request.setNgayBatDau(createDotDangKyRequest.getNgayBatDau());
        request.setNgayKetThuc(createDotDangKyRequest.getNgayKetThuc());
        request.setHanSinhVien(createDotDangKyRequest.getHanSinhVien());
        request.setHanGiangVien(createDotDangKyRequest.getHanGiangVien());
        request.setHanChuNhiemBoMon(createDotDangKyRequest.getHanChuNhiemBoMon());
        Optional<HocKy> hocKyCheck = dtHocKyRepository.findById(createDotDangKyRequest.getHocKyId());
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }

        DotDangKy result = checkDateDotDangKy(request, null, createDotDangKyRequest.getCoSoId());
        DotDangKy dotDangKy = new DotDangKy();
        dotDangKy.setTenDotDangKy(createDotDangKyRequest.getTenDotDangKy());
        dotDangKy.setNgayBatDau(result.getNgayBatDau());
        dotDangKy.setNgayKetThuc(result.getNgayKetThuc());
        dotDangKy.setHanSinhVien(result.getHanSinhVien());
        dotDangKy.setHanGiangVien(result.getHanGiangVien());
        dotDangKy.setHanChuNhiemBoMon(result.getHanChuNhiemBoMon());
        dotDangKy.setCoSoId(createDotDangKyRequest.getCoSoId());
        dotDangKy.setHocKyId(createDotDangKyRequest.getHocKyId());

        try {
            // gen file tự động
            writerFileCSV.genFolderLogger(dotDangKy.getTenDotDangKy());
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKy.getTenDotDangKy());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Đợt đăng ký '" + dotDangKy.getTenDotDangKy() + "' đã được tạo",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dtDotDangKyRepository.save(dotDangKy);
    }

    @Override
    public DotDangKy update(@Valid final DtUpdateDotDangKyRequest dtUpdateDotDangKyRequest) {
        Optional<DotDangKy> dotDangKyCheck = dtDotDangKyRepository.findById(dtUpdateDotDangKyRequest.getId());
        String tenDotCu = dotDangKyCheck.get().getTenDotDangKy();
        Long ngayBatDauCu = dotDangKyCheck.get().getNgayBatDau();
        Long ngayKetThucCu = dotDangKyCheck.get().getNgayKetThuc();
        Long hanSvCu = dotDangKyCheck.get().getHanSinhVien();
        Long hanGvCu = dotDangKyCheck.get().getHanGiangVien();
        Long hanCnCu = dotDangKyCheck.get().getHanChuNhiemBoMon();
        if (!dotDangKyCheck.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        DtBaseDotDangKyRequest request = new DtCreateDotDangKyRequest();
        request.setNgayBatDau(dtUpdateDotDangKyRequest.getNgayBatDau());
        request.setNgayKetThuc(dtUpdateDotDangKyRequest.getNgayKetThuc());
        request.setHanSinhVien(dtUpdateDotDangKyRequest.getHanSinhVien());
        request.setHanGiangVien(dtUpdateDotDangKyRequest.getHanGiangVien());
        request.setHanChuNhiemBoMon(dtUpdateDotDangKyRequest.getHanChuNhiemBoMon());

        DotDangKy result = checkDateDotDangKy(request, dtUpdateDotDangKyRequest.getId(), dtUpdateDotDangKyRequest.getCoSoId());
        DotDangKy dotDangKy = dotDangKyCheck.get();
        dotDangKy.setTenDotDangKy(dtUpdateDotDangKyRequest.getTenDotDangKy());
        dotDangKy.setNgayBatDau(result.getNgayBatDau());
        dotDangKy.setNgayKetThuc(result.getNgayKetThuc());
        dotDangKy.setHanSinhVien(result.getHanSinhVien());
        dotDangKy.setHanGiangVien(result.getHanGiangVien());
        dotDangKy.setHanChuNhiemBoMon(result.getHanChuNhiemBoMon());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngayBatDauCuFormatted = dateFormat.format(new Date(ngayBatDauCu));
        String ngayKetThucCuFormatted = dateFormat.format(new Date(ngayKetThucCu));
        String hanSvCuFormatted = dateFormat.format(new Date(hanSvCu));
        String hanGvCuFormatted = dateFormat.format(new Date(hanGvCu));
        String hanCnCuFormatted = dateFormat.format(new Date(hanCnCu));
        String ngayBatDauMoiFormatted = dateFormat.format(new Date(dotDangKy.getNgayBatDau()));
        String ngayKetThucMoiFormatted = dateFormat.format(new Date(dotDangKy.getNgayKetThuc()));
        String hanSvMoiFormatted = dateFormat.format(new Date(dotDangKy.getHanSinhVien()));
        String hanGvMoiFormatted = dateFormat.format(new Date(dotDangKy.getHanGiangVien()));
        String hanCnMoiFormatted = dateFormat.format(new Date(dotDangKy.getHanChuNhiemBoMon()));
        StringBuilder logMessage = new StringBuilder();
        if (!(dotDangKy.getTenDotDangKy()).equals(tenDotCu)) {
            logMessage.append("Đào tạo đã sửa tên đợt đăng ký từ '").append(tenDotCu)
                    .append("' thành '").append(dotDangKy.getTenDotDangKy()).append("'. ");
        }
        if (!(dotDangKy.getNgayBatDau()).equals(ngayBatDauCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu đợt đăng ký '").append(dotDangKy.getTenDotDangKy()).append("' từ ngày '")
                    .append(ngayBatDauCuFormatted).append("' thành '").append(ngayBatDauMoiFormatted).append("'. ");
        }
        if (!(dotDangKy.getNgayKetThuc()).equals(ngayKetThucCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu đợt đăng ký '").append(dotDangKy.getTenDotDangKy()).append("' từ ngày '")
                    .append(ngayKetThucCuFormatted).append("' thành '").append(ngayKetThucMoiFormatted).append("'. ");
        }
        if (!(dotDangKy.getHanSinhVien()).equals(hanSvCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu đợt đăng ký '").append(dotDangKy.getTenDotDangKy()).append("' từ ngày '")
                    .append(hanSvCuFormatted).append("' thành '").append(hanSvMoiFormatted).append("'. ");
        }
        if (!(dotDangKy.getHanGiangVien()).equals(hanGvCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu đợt đăng ký '").append(dotDangKy.getTenDotDangKy()).append("' từ ngày '")
                    .append(hanGvCuFormatted).append("' thành '").append(hanGvMoiFormatted).append("'. ");
        }
        if (!(dotDangKy.getHanChuNhiemBoMon()).equals(hanCnCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu đợt đăng ký '").append(dotDangKy.getTenDotDangKy()).append("' từ ngày '")
                    .append(hanCnCuFormatted).append("' thành '").append(hanCnMoiFormatted).append("'. ");
        }
        try {
            writerFileCSV.updateFolder(writerFileCSV.covertFileName(dotDangKyCheck.get().getTenDotDangKy()), writerFileCSV.covertFileName(dtUpdateDotDangKyRequest.getTenDotDangKy()));
            // ghi log vào trong file chức năng từng màn
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dtUpdateDotDangKyRequest.getTenDotDangKy());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject(logMessage.toString(),
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dtDotDangKyRepository.save(dotDangKy);
    }

    @Override
    public DotDangKy findById(String id) {
        Optional<DotDangKy> dotDangKyCheck = dtDotDangKyRepository.findById(id);
        if (dotDangKyCheck.isEmpty()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        return dotDangKyCheck.get();
    }

    @Override
    public List<SimpleEntityProj> findAllSimpleEntity(String coSoId) {
        return dtDotDangKyRepository.findAllSimpleEntity(coSoId);
    }

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSo(String coSoId) {
        return dtDotDangKyRepository.findAllSimpleEntity(coSoId);
    }

    @Override
    public List<DotDangKy> getAllByCoSo(String coSoId) {
        return dtDotDangKyRepository.getAllByCoSo(coSoId);
    }

    @Override
    public DtDetailDotDangKyResponse detail(String id, String coSoId) {
        DtDetailDotDangKyResponse dtDotDangKyResponse = dtDotDangKyRepository.detailDotDangKy(id, new Date().getTime(), coSoId);
        if (dtDotDangKyResponse == null) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        return dtDotDangKyResponse;
    }

    @Override
    public DotDangKy checkDateDotDangKy(DtBaseDotDangKyRequest request, String id, String coSoId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        Date ngayBatDau = null;
        Date ngayKetThuc = null;
        Date hanSinhVien = null;
        Date hanGiangVien = null;
        Date hanChuNhiemBoMon = null;
        try {
            ngayBatDau = sdf.parse(request.getNgayBatDau());
            ngayKetThuc = sdf.parse(request.getNgayKetThuc());
            hanSinhVien = sdf.parse(request.getHanSinhVien());
            hanGiangVien = sdf.parse(request.getHanGiangVien());
            hanChuNhiemBoMon = sdf.parse(request.getHanChuNhiemBoMon());
        } catch (ParseException e) {
            throw new RestApiException(Message.INVALID_DATE);
        }
        Long ngayBatDauLong = ngayBatDau.getTime();
        Long ngayKetThucLong = ngayKetThuc.getTime();
        Long hanSinhVienLong = hanSinhVien.getTime();
        Long hanGiangVienLong = hanGiangVien.getTime();
        Long hanChuNhiemBoMonLong = hanChuNhiemBoMon.getTime();
        if (ngayKetThucLong < ngayBatDauLong) {
            throw new RestApiException((Message.INVALID_NGAY_KET_THUC));
        }
        List<DotDangKy> listDotDangKy = dtDotDangKyRepository.getAllByCoSo(coSoId);
        for (DotDangKy dotDangKy : listDotDangKy) {
            if (id != null) {
                if (dotDangKy.getId().equals(id)) {
                    continue;
                }
            }
            if (ngayBatDauLong < dotDangKy.getNgayKetThuc()
                    && ngayKetThucLong > dotDangKy.getNgayBatDau()) {
                throw new RestApiException(Message.DOT_DANG_KY_OVERLAP);
            }
        }
        if (hanSinhVienLong <= ngayBatDauLong || hanSinhVienLong >= ngayKetThucLong) {
            throw new RestApiException(Message.INVALID_HAN_SINH_VIEN);
        }
        if (hanGiangVienLong <= ngayBatDauLong || hanSinhVienLong >= ngayKetThucLong || hanGiangVienLong < hanSinhVienLong) {
            throw new RestApiException(Message.INVALID_HAN_GIANG_VIEN);
        }
        if (hanChuNhiemBoMonLong <= ngayBatDauLong || hanChuNhiemBoMonLong >= ngayKetThucLong
                || hanChuNhiemBoMonLong < hanGiangVienLong || hanChuNhiemBoMonLong < hanSinhVienLong) {
            throw new RestApiException(Message.INVALID_HAN_CHU_NHIEM_BO_MON);
        }
        DotDangKy result = new DotDangKy();
        result.setNgayBatDau(ngayBatDauLong);
        result.setNgayKetThuc(ngayKetThucLong);
        result.setHanSinhVien(hanSinhVienLong);
        result.setHanGiangVien(hanGiangVienLong);
        result.setHanChuNhiemBoMon(hanChuNhiemBoMonLong);
        return result;
    }

    @Override
    public Boolean createFileLog(String tenDotDangKy) {
        if (tenDotDangKy != null && tenDotDangKy.trim().length() != 0) {
            String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(tenDotDangKy);
            File checkFile = new File(pathFile);
            if (checkFile.exists()) {
                throw new RestApiException(Message.FILE_LOG_IS_EXIST);
            }
            writerFileCSV.genFolderLogger(tenDotDangKy);
            return true;
        }
        return false;
    }

//    @Override
//    public File downloadFileLog(String tenDotDangKy) throws ZipException {
//        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(tenDotDangKy);
//        return writerFileCSV.downloadFileLog(pathFile);
//    }

}