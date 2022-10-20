package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
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
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author SonPT
 */

@Service
public class DtDotDangKyServiceImpl implements DtDotDangKyService {

    @Autowired
    private DtDotDangKyRepository dtDotDangKyRepository;

    @Autowired
    private DtHocKyRepository dtHocKyRepository;

    private FormUtils formUtils = new FormUtils();

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
        if (createDotDangKyRequest.getNgayKetThuc() < createDotDangKyRequest.getNgayBatDau()) {
            throw new RestApiException((Message.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC));
        }
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
    public DotDangKy update(@Valid DtUpdateDotDangKyRequest dtUpdateDotDangKyRequest) {
        Optional<DotDangKy> dotDangKyCheck = dtDotDangKyRepository.findById(dtUpdateDotDangKyRequest.getId());
        if (!dotDangKyCheck.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        if (dtUpdateDotDangKyRequest.getNgayKetThuc() < dtUpdateDotDangKyRequest.getNgayBatDau()) {
            throw new RestApiException((Message.NGAY_BAT_DAU_BEFORE_NGAY_KET_THUC));
        }
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
        dotDangKy.setHanSinhVien(dtUpdateDotDangKyRequest.getHanSinhVien());
        dotDangKy.setHanGiangVien(dtUpdateDotDangKyRequest.getHanGiangVien());
        dotDangKy.setHanChuNhiemBoMon(dtUpdateDotDangKyRequest.getHanChuNhiemBoMon());
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

}
