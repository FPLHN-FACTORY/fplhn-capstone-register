package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.AddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
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
    public List<SvDetailNhomDatnResponse> getNhomDatnById(String id) {
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
    public PageableObject<SvNhomDatnResponse> findAllNhomDatn(final SvFindNhomDatnRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<SvNhomDatnResponse> res = svNhomDatnRepository.findAllNhomDatn(pageable, request);
        return new PageableObject(res);
    }

    @Override
    public List<SvShowSinhVienTheoNhomResponse> showDanhSachSinhVien(String id) {
        List<SvShowSinhVienTheoNhomResponse> res = svNhomDatnRepository.showDanhSachSinhVien(id);
        return res;
    }

    @Override
    public boolean addSinhVienTheoNhom(final AddSinhVienRequest req) {
        String matKhau = svNhomDatnRepository.getMatKhauById(req.getIdNhom());
        int soLuongThanhVien = svNhomDatnRepository.getSoLuongThanhVienById(req.getIdNhom());
        if (soLuongThanhVien == 7){
            throw new RestApiException(Message.NHOM_DA_DU_THANH_VIEN);
        }
        if (!(matKhau == null)) {
            if (!matKhau.trim().equals(req.getInputPassword().trim())){
                throw new RestApiException(Message.MAT_KHAU_KHONG_CHINH_XAC);
            }
        }
        svNhomDatnRepository.addSinhVienInGroup(req);
        return true;
    }

}
