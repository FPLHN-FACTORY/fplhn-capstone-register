package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.model.request.BaseHocKyRequest;
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
        return new PageableObject<>(res);
    }

    @Override
    @Transactional
    public HocKy create(@Valid final CreateHocKyRequest request) {
        HocKy hocKy = createHocKy(request);
        checkOverlap(hocKy);
        return saveHocKy(hocKy, "Học kỳ '" + hocKy.getTenHocKy() + "' đã được tạo");
    }

    private HocKy createHocKy(CreateHocKyRequest request) {
        HocKy hocKy = new HocKy();
        hocKy.setTenHocKy(request.getTenHocKy().trim());
        setHocKyDates(hocKy, request);
        return hocKy;
    }

    private void setHocKyDates(HocKy hocKy, BaseHocKyRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            hocKy.setNgayBatDau(sdf.parse(request.getNgayBatDau()).getTime());
            hocKy.setNgayKetThuc(sdf.parse(request.getNgayKetThuc()).getTime());
        } catch (ParseException e) {
            throw new RestApiException(Message.INVALID_DATE);
        }
    }

    private void checkOverlap(HocKy hocKy) {
        List<HocKy> allHocKies = hocKyRepository.findAll();
        for (HocKy item : allHocKies) {
            if (isHocKyDatesOverlapping(hocKy, item)) {
                throw new RestApiException(Message.HOC_KY_OVERLAP);
            }
        }
    }

    private boolean isHocKyDatesOverlapping(HocKy hocKy, HocKy otherHocKy) {
        return (hocKy.getNgayBatDau() < otherHocKy.getNgayKetThuc()
                && hocKy.getNgayKetThuc() > otherHocKy.getNgayBatDau());
    }

    private HocKy saveHocKy(HocKy hocKy, String logMessage) {
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject(logMessage, pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return hocKyRepository.save(hocKy);
    }

    @Override
    @Transactional
    public Boolean delete(final String id) {
        HocKy hocKy = findById(id);
        DotDangKy dotDangKy = dotDangKyRepository.findByHocKyId(hocKy.getId());
        if (dotDangKy != null) {
            throw new RestApiException(Message.TON_TAI_DOT_DANG_KY);
        }
        deleteHocKy(hocKy, "Học kỳ '" + hocKy.getTenHocKy() + "' đã bị xóa");
        return true;
    }

    private void deleteHocKy(HocKy hocKy, String logMessage) {
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi("");
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject(logMessage, pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        hocKyRepository.delete(hocKy);
    }

    @Override
    @Transactional
    public HocKy update(@Valid final UpdateHocKyRequest request) {
        HocKy hocKy = findById(request.getId());
        updateHocKyFields(hocKy, request);
        checkOverlap(hocKy);
        String logMessage = createUpdateLogMessage(hocKy);
        return saveHocKy(hocKy, logMessage);
    }

    private void updateHocKyFields(HocKy hocKy, UpdateHocKyRequest request) {
        hocKy.setTenHocKy(request.getTenHocKy().trim());
        setHocKyDates(hocKy, request);
    }

    private String createUpdateLogMessage(HocKy hocKy) {
        StringBuilder logMessage = new StringBuilder();
        HocKy originalHocKy = hocKyRepository.findById(hocKy.getId()).get();
        if (!hocKy.getTenHocKy().equals(originalHocKy.getTenHocKy())) {
            logMessage.append("Đào tạo đã sửa tên học kỳ từ '").append(originalHocKy.getTenHocKy())
                    .append("' thành '").append(hocKy.getTenHocKy()).append("'. ");
        }
        if (hocKy.getNgayBatDau() != originalHocKy.getNgayBatDau()) {
            logMessage.append("Đào tạo đã sửa ngày bắt đầu học kỳ '").append(hocKy.getTenHocKy())
                    .append("' từ ngày '").append(formatDate(originalHocKy.getNgayBatDau()))
                    .append("' thành '").append(formatDate(hocKy.getNgayBatDau())).append("'. ");
        }
        if (hocKy.getNgayKetThuc() != originalHocKy.getNgayKetThuc()) {
            logMessage.append("Đào tạo đã sửa ngày kết thúc học kỳ '").append(hocKy.getTenHocKy())
                    .append("' từ ngày '").append(formatDate(originalHocKy.getNgayKetThuc()))
                    .append("' thành '").append(formatDate(hocKy.getNgayKetThuc())).append("'. ");
        }
        return logMessage.toString();
    }

    private String formatDate(Long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date(date));
    }

    @Override
    public HocKy findById(String id) {
        return hocKyRepository.findById(id).orElseThrow(() -> new RestApiException(Message.HOC_KY_NOT_EXIST));
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