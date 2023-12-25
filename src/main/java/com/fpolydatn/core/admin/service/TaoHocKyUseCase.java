package com.fpolydatn.core.admin.service;

import com.fpolydatn.core.admin.model.request.CreateHocKyRequest;
import com.fpolydatn.core.admin.repository.AdHocKyRepository;
import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : PhongTT35
 **/
@Service
public final class TaoHocKyUseCase implements IUseCase<CreateHocKyRequest, HocKy> {

    private final AdHocKyRepository repository;

    @Autowired
    public TaoHocKyUseCase(AdHocKyRepository repository) {
        this.repository = repository;
    }

    @Override
    public HocKy execute(@Valid final CreateHocKyRequest request) {
        HocKy hocKy = new HocKy();
        hocKy.setTenHocKy(request.getTenHocKy().trim());
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
        List<HocKy> listHocKy = repository.findAll();
        listHocKy.forEach(item -> {
            if (ngayBatDauLong < item.getNgayKetThuc()
                    && ngayKetThucLong > item.getNgayBatDau()) {
                throw new RestApiException(Message.HOC_KY_OVERLAP);
            }
        });
        hocKy.setNgayBatDau(ngayBatDauLong);
        hocKy.setNgayKetThuc(ngayKetThucLong);

        return repository.save(hocKy);
    }
}
