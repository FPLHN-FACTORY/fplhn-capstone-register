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
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
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

    @Override
    public PageableObject<DtDotDangKyResponse> findByCoSo(DtFindDotDangKyRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtDotDangKyResponse> response = dtDotDangKyRepository.findByCoSo(request, pageable);
        return new PageableObject(response);
    }

    @Override
    public DotDangKy create(@Valid DtCreateDotDangKyRequest createDotDangKyRequest) {
        Optional<HocKy> hocKyCheck = dtHocKyRepository.findById(createDotDangKyRequest.getHocKyId());
        if (!hocKyCheck.isPresent()) {
            throw new RestApiException(Message.HOC_KY_NOT_EXIST);
        }
//        if (createDotDangKyRequest.getNgayKetThuc() < createDotDangKyRequest.getNgayBatDau()) {
//            throw new RestApiException((Message.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC));
//        }
        List<DotDangKy> listDotDangKy = dtDotDangKyRepository.findAll();
        for (DotDangKy dotDangKy : listDotDangKy) {
            if (createDotDangKyRequest.getNgayBatDau() < dotDangKy.getNgayKetThuc()
                    && createDotDangKyRequest.getNgayKetThuc() > dotDangKy.getNgayBatDau()) {
                throw new RestApiException(Message.DOT_DANG_KY_OVERLAP);
            }
        }
        DotDangKy dotDangKy = formUtils.convertToObject(DotDangKy.class, createDotDangKyRequest);
        return dtDotDangKyRepository.save(dotDangKy);
    }

    @Override
    public DotDangKy update(DtUpdateDotDangKyRequest dtUpdateDotDangKyRequest) {
        Optional<DotDangKy> dotDangKyCheck = dtDotDangKyRepository.findById(dtUpdateDotDangKyRequest.getId());
        if (!dotDangKyCheck.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
//        if (dtUpdateDotDangKyRequest.getNgayKetThuc() < dtUpdateDotDangKyRequest.getNgayBatDau()) {
//            throw new RestApiException((Message.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC));
//        }
        List<DotDangKy> listDotDangKy = dtDotDangKyRepository.findAll();
        for (DotDangKy dotDangKy : listDotDangKy) {
            if (dotDangKy.getId().equals(dtUpdateDotDangKyRequest.getId())) {
                continue;
            }
            if (dtUpdateDotDangKyRequest.getNgayBatDau() < dotDangKy.getNgayKetThuc()
                    && dtUpdateDotDangKyRequest.getNgayKetThuc() > dotDangKy.getNgayBatDau()) {
                throw new RestApiException(Message.DOT_DANG_KY_OVERLAP);
            }
        }
        DotDangKy dotDangKy = dotDangKyCheck.get();
        dotDangKy.setNgayBatDau(dtUpdateDotDangKyRequest.getNgayBatDau());
        dotDangKy.setNgayKetThuc(dtUpdateDotDangKyRequest.getNgayKetThuc());
        return dtDotDangKyRepository.save(dotDangKy);
    }

    @Override
    public DotDangKy findById(String id) {
        Optional<DotDangKy> dotDangKyCheck = dtDotDangKyRepository.findById(id);
        if (Objects.isNull(dotDangKyCheck)) {
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
        DtDetailDotDangKyResponse dtDotDangKyResponse = dtDotDangKyRepository.detailDotDangKy(id, coSoId);
        if (dtDotDangKyResponse == null) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        return dtDotDangKyResponse;
    }

    @Override
    public DotDangKy checkDateDotDangKy(DtBaseDotDangKyRequest request, String id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        Long ngayBatDau = null;
        Long ngayKetThuc = null;
        Long hanSinhVien = null;
        Long hanGiangVien = null;
        Long hanChuNhiemBoMon = null;
        try {
            ngayBatDau = sdf.parse(request.getNgayBatDau()).getTime();
            ngayKetThuc = sdf.parse(request.getNgayKetThuc()).getTime();
            hanSinhVien = sdf.parse(request.getHanSinhVien()).getTime();
            hanGiangVien = sdf.parse(request.getHanGiangVien()).getTime();
            hanChuNhiemBoMon = sdf.parse(request.getHanChuNhiemBoMon()).getTime();
        } catch (ParseException e) {
            throw new RestApiException(Message.INVALID_DATE);
        }
        if (ngayKetThuc < ngayBatDau) {
            throw new RestApiException((Message.INVALID_NGAY_KET_THUC));
        }
        List<DotDangKy> listDotDangKy = dtDotDangKyRepository.findAll();
        for (DotDangKy dotDangKy : listDotDangKy) {
            if (id != null) {
                if (dotDangKy.getId().equals(id)) {
                    continue;
                }
            }
            if (ngayBatDau < dotDangKy.getNgayKetThuc()
                    && ngayKetThuc > dotDangKy.getNgayBatDau()) {
                throw new RestApiException(Message.DOT_DANG_KY_OVERLAP);
            }
        }
        if (hanSinhVien <= ngayBatDau || hanSinhVien >= ngayKetThuc) {
            throw new RestApiException(Message.INVALID_HAN_SINH_VIEN);
        }
        if (hanGiangVien <= ngayBatDau || hanSinhVien >= ngayKetThuc || hanGiangVien < hanSinhVien) {
            throw new RestApiException(Message.INVALID_HAN_GIANG_VIEN);
        }
        if (hanChuNhiemBoMon <= ngayBatDau || hanChuNhiemBoMon >= ngayKetThuc || hanChuNhiemBoMon < hanGiangVien || hanChuNhiemBoMon < hanSinhVien) {
            throw new RestApiException(Message.INVALID_HAN_CHU_NHIEM_BO_MON);
        }
        DotDangKy result = new DotDangKy();
        result.setNgayBatDau(ngayBatDau);
        result.setNgayKetThuc(ngayKetThuc);
        result.setHanSinhVien(hanSinhVien);
        result.setHanGiangVien(hanGiangVien);
        result.setHanChuNhiemBoMon(hanChuNhiemBoMon);
        return result;
    }

}