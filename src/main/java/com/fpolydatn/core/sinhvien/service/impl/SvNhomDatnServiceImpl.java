package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.FindSvNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hoangnt
 */
@Service
public class SvNhomDatnServiceImpl implements SvNhomDatnService {

    @Autowired
    private SvNhomDatnRepository svNhomDatnRepository;

    @Override
    public List<SvNhomDatnResponse> getNhomDatnById(String id) {
        return svNhomDatnRepository.getNhomDatnById(id);
    }

    @Override
    public Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser) {
        String idTruongNhom = svNhomDatnRepository.getIdTruongNhom(idNhomDatn, idCoSo);
        if (idUser.equals(idTruongNhom)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean guiYeuCauPheDuyet(String idNhomDatn, String idUser) {
        if (svNhomDatnRepository.getTrangThai(idNhomDatn) == 1 ||
                svNhomDatnRepository.getTrangThai(idNhomDatn) == 3
        ) {
            Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(idNhomDatn);
            if (!nhomDatn.isPresent()){
                throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
            }
            if (!nhomDatn.get().getTruongNhomId().equals(idUser)) {
                throw new RestApiException(Message.KHONG_PHAI_TRUONG_NHOM);
            }
            nhomDatn.get().setTrangThai(TrangThaiNhom.SAN_SANG_PHE_DUYET);
            svNhomDatnRepository.save(nhomDatn.get());
            return true;
        }
        throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
    }

    @Override
    public PageableObject<SvSinhVienNhomDatnResponse> findAllNhomDatn(final FindSvNhomDatnRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<SvSinhVienNhomDatnResponse> res = svNhomDatnRepository.findAllNhomDatn(pageable, request);
        return new PageableObject(res);
    }

}
