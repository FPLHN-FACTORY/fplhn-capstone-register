package com.fpolydatn.core.admin.service;

import com.fpolydatn.core.admin.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author : PhongTT35
 **/
@Service
public class SuaHocKyUseCase implements IUseCase<UpdateHocKyRequest, HocKy> {

    private final AdHocKyRepository repository;

    @Autowired
    public SuaHocKyUseCase(AdHocKyRepository repository) {
        this.repository = repository;
    }

    @Override
    public HocKy execute(final UpdateHocKyRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayBatDau, ngayKetThuc;
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

        Optional<HocKy> hocKyCheck = repository.findById(request.getId());
        if (hocKyCheck.isEmpty()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }

        Long soHocKyTrongKhoangThoiGian = repository.countHocKyInPeriod(ngayBatDauLong, ngayKetThucLong);
        if (soHocKyTrongKhoangThoiGian > 1) { // Không tính cái đang có
            throw new RestApiException(Message.HOC_KY_OVERLAP);
        }

        HocKy hocKy = hocKyCheck.get();
        hocKy.setTenHocKy(request.getTenHocKy().trim());
        hocKy.setNgayBatDau(ngayBatDauLong);
        hocKy.setNgayKetThuc(ngayKetThucLong);
        return repository.save(hocKy);
    }
}
