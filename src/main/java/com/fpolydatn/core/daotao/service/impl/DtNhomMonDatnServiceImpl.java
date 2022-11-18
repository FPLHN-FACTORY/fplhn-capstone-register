package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailNhomMonResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtNhomMonDatnRepository;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DtNhomMonDatnServiceImpl implements DtNhomMonDatnService {
    @Autowired
    private DtNhomMonDatnRepository dtNhomMonDatnRepository;

    @Autowired
    private DtChuyenNganhRepository dtChuyenNganhRepository;

    @Override
    public PageableObject<DtNhomMonDatnResponse> findAllByCoSo(DtFindNhomMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtNhomMonDatnResponse> res = dtNhomMonDatnRepository.findAllByCoSo(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(String id, String coSoId) {
        Optional<MonDatn> nhomMonDatn = dtNhomMonDatnRepository.findById(id);
        if (!nhomMonDatn.isPresent()) {
            throw new RestApiException(Message.NHOM_MON_DATN_NOT_EXIST);
        }
        List<DtDetailNhomMonResponse> listMonDatn = dtNhomMonDatnRepository.findMonById(id, coSoId);
        if (listMonDatn.size() != 0) {
            throw new RestApiException(Message.NHOM_MON_DANG_TON_TAI_MON);
        }
        dtNhomMonDatnRepository.delete(nhomMonDatn.get());
        return true;
    }

    @Override
    public Boolean update(@Valid DtUpdateNhomMonDatnRequest command) {
        Optional<MonDatn> nhomMonDatnFind = dtNhomMonDatnRepository.findById(command.getId());
        if (Objects.isNull(nhomMonDatnFind)) {
            throw new RestApiException(Message.NHOM_MON_DATN_NOT_EXIST);
        }
        String maNhomMon = command.getMaNhomMon().trim().toUpperCase();
        MonDatn monDatnCheck = dtNhomMonDatnRepository.findByMaNhomMon(maNhomMon, command.getCoSoId());
        if (monDatnCheck != null) {
            if (!monDatnCheck.getId().equals(command.getId())) {
                throw new RestApiException(Message.MA_NHOM_MON_DATN_ALREADY_EXIST);
            }
        }
        Optional<ChuyenNganh> chuyenNganh = dtChuyenNganhRepository.findById(command.getChuyenNganhId());
        if (!chuyenNganh.isPresent()) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        Optional<MonDatn> nhomMonDatn = dtNhomMonDatnRepository.findById(command.getId());
        nhomMonDatn.get().setMaMon(maNhomMon);
        nhomMonDatn.get().setChuyenNganhId(command.getChuyenNganhId());
        nhomMonDatn.get().setTenMonDatn(command.getTenNhomMon());
        dtNhomMonDatnRepository.save(nhomMonDatn.get());
        return true;
    }

    @Override
    public Boolean add(@Valid DtCreateNhomMonDatnRequest command) {
        String maNhomMon = command.getMaNhomMon().toUpperCase().trim();
        MonDatn monDatnCheck = dtNhomMonDatnRepository.findByMaNhomMon(maNhomMon, command.getCoSoId());
        if (monDatnCheck != null) {
            throw new RestApiException(Message.MA_NHOM_MON_DATN_ALREADY_EXIST);
        }
        Optional<ChuyenNganh> chuyenNganh = dtChuyenNganhRepository.findById(command.getChuyenNganhId());
        if (!chuyenNganh.isPresent()) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        MonDatn nhoMonDatn = new MonDatn();
        nhoMonDatn.setMaMon(maNhomMon);
        nhoMonDatn.setTenMonDatn(command.getTenNhomMon());
        nhoMonDatn.setChuyenNganhId(command.getChuyenNganhId());
        nhoMonDatn.setCoSoId(command.getCoSoId());
        dtNhomMonDatnRepository.save(nhoMonDatn);
        return true;
    }

    @Override
    public MonDatn findById(String id) {
        Optional<MonDatn> monDatn = dtNhomMonDatnRepository.findById(id);
        if (Objects.isNull(monDatn)) {
            throw new RestApiException(Message.NHOM_MON_DATN_NOT_EXIST);
        }
        return monDatn.get();
    }

    @Override
    public List<DtDetailNhomMonResponse> detail(String id, String coSoId) {
        return dtNhomMonDatnRepository.findMonById(id, coSoId);
    }
}
