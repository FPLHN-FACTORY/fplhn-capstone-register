package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateDotDangKyRequest;
import com.fpolydatn.infrastructure.response.DotDangKyResponse;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.model.DotDangKy;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.service.DotDangKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


/**
 * @author thepvph20110
 */

@Service
public class DotDangKyServiceImpl implements DotDangKyService {

    @Autowired
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    @Override
    public DotDangKyResponse createDotDangKy(CreateDotDangKyRequest dotDangKyRequest) {

        Optional<CoSo> coSo = coSoRepository.findById(dotDangKyRequest.getCoSoId());
        Regex(dotDangKyRequest, coSo);
        DotDangKy dotDangKy = convertToDotDangKy(dotDangKyRequest);
        DotDangKy dotDangKyNew = dotDangKyRepository.save(dotDangKy);
        DotDangKyResponse dotDangKyResponse = new DotDangKyResponse(dotDangKyNew.getDotDangKyId(), dotDangKyNew.getTenDotDangKy(), dotDangKyNew.getNgayBatDau(), dotDangKyNew.getNgayKetThuc(), dotDangKyNew.getHocKyId(), coSo.get());
        return dotDangKyResponse;
    }

    @Override
    public DotDangKyResponse updateDotDangKy(CreateDotDangKyRequest dotDangKyRequest, UUID id) {
        Optional<CoSo> coSo = coSoRepository.findById(dotDangKyRequest.getCoSoId());
        Regex(dotDangKyRequest, coSo);
        DotDangKy dotDangKy = convertToDotDangKy(dotDangKyRequest);
        dotDangKy.setDotDangKyId(id);
        DotDangKy dotDangKyNew = dotDangKyRepository.save(dotDangKy);
        if (Objects.isNull(dotDangKyNew)) {
            throw new RestApiException(StatusCode.ID_NOT_EXISTS);
        }
        DotDangKyResponse dotDangKyResponse = new DotDangKyResponse(dotDangKyNew.getDotDangKyId(), dotDangKyNew.getTenDotDangKy(), dotDangKyNew.getNgayBatDau(), dotDangKyNew.getNgayKetThuc(), dotDangKyNew.getHocKyId(), coSo.get());
        return dotDangKyResponse;
    }

    private DotDangKy convertToDotDangKy(CreateDotDangKyRequest dotDangKyRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DotDangKy dotDangKy = new DotDangKy();
        try {
            Date ngayBatDau = sdf.parse(dotDangKyRequest.getNgayBatDau());
            Date ngayKetThuc = sdf.parse(dotDangKyRequest.getNgayKetThuc());
            dotDangKy.setTenDotDangKy(dotDangKyRequest.getTenDotDangKy());
            dotDangKy.setNgayBatDau(ngayBatDau);
            dotDangKy.setNgayKetThuc(ngayKetThuc);
            dotDangKy.setHocKyId(dotDangKyRequest.getHocKyId());
            dotDangKy.setCoSoId(dotDangKyRequest.getCoSoId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return dotDangKy;
    }

    private void Regex(CreateDotDangKyRequest dotDangKyRequest, Optional<CoSo> coSo) {

        if (Objects.isNull(dotDangKyRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getTenDotDangKy())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getNgayBatDau())) {
            throw new RestApiException(StatusCode.NGAY_BAT_DAU_EMPTY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getNgayKetThuc())) {
            throw new RestApiException(StatusCode.NGAY_KET_THUC_EMPTY);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date ngayBatDau = sdf.parse(dotDangKyRequest.getNgayBatDau());
            Date ngayKetThuc = sdf.parse(dotDangKyRequest.getNgayKetThuc());
            if (ngayBatDau.after(ngayKetThuc)) {
                throw new RestApiException(StatusCode.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (ObjectUtils.isEmpty(dotDangKyRequest.getHocKyId())) {
            throw new RestApiException(StatusCode.ID_HOC_KY_EMPLY);
        }
        if (ObjectUtils.isEmpty(dotDangKyRequest.getCoSoId())) {
            throw new RestApiException(StatusCode.ID_CO_SO_EMPLY);
        }
        if (!coSo.isPresent()) {
            throw new RestApiException(StatusCode.ID_COSO_NOT_EXIST);
        }
    }

}
