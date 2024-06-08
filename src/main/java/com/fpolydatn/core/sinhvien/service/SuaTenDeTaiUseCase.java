package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public final class SuaTenDeTaiUseCase implements IUseCase<SvUpdateTenDeTaiRequest, NhomDatn> {

    private final NhomDatnRepository repository;

    @Autowired
    public SuaTenDeTaiUseCase(NhomDatnRepository nhomDatnRepository) {
        this.repository = nhomDatnRepository;
    }

    @Override
    public NhomDatn execute(SvUpdateTenDeTaiRequest request) {
        if ("".equals(request.getTenDeTai())) {
            throw new RestApiException(Message.DONT_UPDATE_TEN_DE_TAI);
        }

        NhomDatn nhomDatn = repository.findById(request.getNhomDatnId()).orElse(null);
        if (nhomDatn == null) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!request.getIdUser().equals(nhomDatn.getTruongNhomId())) {
            throw new RestApiException(Message.KHONG_PHAI_TRUONG_NHOM);
        }
        if (!nhomDuocPhepSuaDeTai(nhomDatn.getTrangThai())) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatn.setTenDeTai(request.getTenDeTai());
        return repository.save(nhomDatn);
    }

    private boolean nhomDuocPhepSuaDeTai(TrangThaiNhom trangThaiNhom) {
        return trangThaiNhom != TrangThaiNhom.CNBM_CHOT && trangThaiNhom != TrangThaiNhom.GVHD_CHOT;
    }
}
