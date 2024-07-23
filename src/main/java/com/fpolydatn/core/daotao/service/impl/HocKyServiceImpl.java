package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.model.request.CreateHocKyRequest;
import com.fpolydatn.core.daotao.model.request.FindHocKyRequest;
import com.fpolydatn.core.daotao.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.admin.repository.AdDotDangKyRepository;
import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.core.daotao.model.response.HocKyResponse;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.core.daotao.service.HocKyService;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author vinhnv
 */
@Service
@Validated
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private AdHocKyRepository hocKyRepository;

    @Autowired
    private AdDotDangKyRepository dotDangKyRepository;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    @Override
    public PageableObject<HocKyResponse> search(final FindHocKyRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<HocKyResponse> res = hocKyRepository.findByName(request, pageable);
        return new PageableObject(res);
    }

    @Override
    @Transactional
    public HocKy create(@Valid final CreateHocKyRequest request) {
        HocKy hocKy = new HocKy();
        hocKy.setTenHocKy(request.getTenHocKy().trim());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayBatDau = null;
        Date ngayKetThuc = null;
        try {
            ngayBatDau = sdf.parse(request.getNgayBatDau());
            ngayKetThuc = sdf.parse(request.getNgayKetThuc());
        } catch (ParseException e) {
            throw new RestApiException(Message.INVALID_DATE);
        }
        Long ngayBatDauLong = ngayBatDau.getTime();
        Long ngayKetThucLong = ngayKetThuc.getTime();
        if(ngayKetThucLong < ngayBatDauLong) {
            throw new RestApiException(Message.INVALID_NGAY_KET_THUC);
        }
        List<HocKy> listHocKy = hocKyRepository.findAll();
        listHocKy.forEach(item -> {
            if (ngayBatDauLong < item.getNgayKetThuc()
                    && ngayKetThucLong > item.getNgayBatDau()) {
                throw new RestApiException(Message.HOC_KY_OVERLAP);
            }
        });
        hocKy.setNgayBatDau(ngayBatDauLong);
        hocKy.setNgayKetThuc(ngayKetThucLong);
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Học kỳ '" + hocKy.getTenHocKy() + "' đã được tạo",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return hocKyRepository.save(hocKy);
    }

    @Override
    @Transactional
    public Boolean delete(final String id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        DotDangKy dotDangKy = dotDangKyRepository.findByHocKyId(hocKyCheck.get().getId());
        if (dotDangKy != null) {
            throw new RestApiException(Message.TON_TAI_DOT_DANG_KY);
        }
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Học kỳ '" + hocKyCheck.get().getTenHocKy() + "' đã bị xóa",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        hocKyRepository.delete(hocKyCheck.get());
        return true;
    }

    @Override
    @Transactional
    public HocKy update(@Valid final UpdateHocKyRequest request) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(request.getId());
        String tenCu = hocKyCheck.get().getTenHocKy();
        Long ngayBatDauCu = hocKyCheck.get().getNgayBatDau();
        Long ngayKetThucCu = hocKyCheck.get().getNgayKetThuc();
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }

        HocKy hocKy = hocKyCheck.get();
        hocKy.setTenHocKy(request.getTenHocKy().trim());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayBatDau = null;
        Date ngayKetThuc = null;
        try {
            ngayBatDau = sdf.parse(request.getNgayBatDau());
            ngayKetThuc = sdf.parse(request.getNgayKetThuc());

        } catch (ParseException e) {
            throw new RestApiException(Message.INVALID_DATE);
        }
        Long ngayBatDauLong = ngayBatDau.getTime();
        Long ngayKetThucLong = ngayKetThuc.getTime();
        if(ngayKetThucLong < ngayBatDauLong) {
            throw new RestApiException(Message.INVALID_NGAY_KET_THUC);
        }
        List<HocKy> listHocKy = hocKyRepository.findAll();
        for (HocKy item :listHocKy) {
            if (item.getId().equals(request.getId())) {
                continue;
            }
            if (ngayBatDauLong < item.getNgayKetThuc()
                    && ngayKetThucLong > item.getNgayBatDau()) {
                throw new RestApiException(Message.HOC_KY_OVERLAP);
            }
        }
        hocKy.setNgayBatDau(ngayBatDauLong);
        hocKy.setNgayKetThuc(ngayKetThucLong);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngayBatDauCuFormatted = dateFormat.format(new Date(ngayBatDauCu));
        String ngayKetThucCuFormatted = dateFormat.format(new Date(ngayKetThucCu));
        String ngayBatDauMoiFormatted = dateFormat.format(new Date(hocKy.getNgayBatDau()));
        String ngayKetThucMoiFormatted = dateFormat.format(new Date(hocKy.getNgayKetThuc()));

        StringBuilder logMessage = new StringBuilder();
        if (!(hocKy.getTenHocKy()).equals(tenCu)) {
            logMessage.append("Đào tạo đã sửa tên học kỳ từ '").append(tenCu)
                    .append("' thành '").append(hocKy.getTenHocKy()).append("'. ");
        }
        if (!(hocKy.getNgayBatDau()).equals(ngayBatDauCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu học kỳ '").append(hocKy.getTenHocKy()).append("' từ ngày '")
                    .append(ngayBatDauCuFormatted).append("' thành '").append(ngayBatDauMoiFormatted).append("'. ");
        }
        if (!(hocKy.getNgayKetThuc()).equals(ngayKetThucCu)) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu học kỳ '").append(hocKy.getTenHocKy()).append("' từ ngày '")
                    .append(ngayKetThucCuFormatted).append("' thành '").append(ngayKetThucMoiFormatted).append("'. ");
        }
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject(logMessage.toString(),
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return hocKyRepository.save(hocKy);
    }

    @Override
    public HocKy findById(String id) {
        Optional<HocKy> hocKyCheck = hocKyRepository.findById(id);
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
        return hocKyCheck.get();
    }

    @Override
    public long getSizeListHocKy(String tenHocKy) {
        return hocKyRepository.countByName(tenHocKy);
    }

    @Override
    public List<HocKy> getAll() {
        return hocKyRepository.findAll();
    }

}
