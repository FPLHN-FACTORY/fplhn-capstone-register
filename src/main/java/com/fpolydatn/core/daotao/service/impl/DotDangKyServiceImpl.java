package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.daotao.model.request.CreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DotDangKyResponse;
import com.fpolydatn.core.daotao.service.DotDangKyService;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DotDangKyServiceImpl implements DotDangKyService {

    @Autowired
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public DotDangKy deleteDotDangKy(UUID id) {
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(id);
        if (Objects.isNull(dotDangKy)) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        dotDangKyRepository.delete(dotDangKy.get());
        return dotDangKy.get();
    }

    @Override
    public List<DotDangKy> getListDotDangKy(UUID coSoId, int offset, int size) {
        Optional<CoSo> coSo = coSoRepository.findById(coSoId);
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        String id = String.valueOf(coSoId);
        return dotDangKyRepository.getListDotDangKy(id, offset, size);
    }

    @Override
    public DotDangKy addNew(@Valid CreateDotDangKyRequest dotDangKyRequest) {
        DotDangKy dotDangKy = new DotDangKy();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dotDangKy.setTenDotDangKy(dotDangKyRequest.getTenDotDangKy());
        try {
            dotDangKy.setNgayBatDau(sdf.parse(dotDangKyRequest.getNgayBatDau()).getTime());
            dotDangKy.setNgayKetThuc(sdf.parse(dotDangKyRequest.getNgayKetThuc()).getTime());
        } catch (ParseException e) {
            System.out.println("acxzz");
            throw new RuntimeException(e);
        }
        dotDangKy.setHocKyId(dotDangKyRequest.getHocKyId());
        dotDangKy.setCoSoId(dotDangKyRequest.getCoSoId());
        return dotDangKyRepository.save(dotDangKy);
    }



    @Override
    public List<DotDangKyResponse> findDotDangKyByCoSo(UUID coSoId, int offset, int size) {
        String id = String.valueOf(coSoId);
        return dotDangKyRepository.findDotDangKyByCoSo(id, offset, size);
    }

    @Override
    public DotDangKy findById(UUID id) {
        Optional<DotDangKy> dotDangKyCheck = dotDangKyRepository.findById(id);
        if (Objects.isNull(dotDangKyCheck)) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        return dotDangKyCheck.get();
    }

    @Override
    public DotDangKy update(UUID id,@Valid UpdateDotDangKyRequest request) {
        Optional<DotDangKy> dotDangKyCheck = dotDangKyRepository.findById(id);
        if (!dotDangKyCheck.isPresent()){
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DotDangKy dotDangKy = dotDangKyCheck.get();
        dotDangKy.setTenDotDangKy(request.getTenDotDangKy());
        try {
            dotDangKy.setNgayBatDau(sdf.parse(request.getNgayBatDau()).getTime());
            dotDangKy.setNgayKetThuc(sdf.parse(request.getNgayKetThuc()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dotDangKyRepository.save(dotDangKy);
    }

    @Override
    public Long countDotDangKyByCoSo(UUID coSoId) {
        String id = String.valueOf(coSoId);
        return dotDangKyRepository.countDotDangKyByCoSo(id);
    }

    @Override
    public List<DotDangKy> findAllByCoSo(UUID coSoId) {
        String id = String.valueOf(coSoId);
        return dotDangKyRepository.findAllByCoSo(id);
    }
}
