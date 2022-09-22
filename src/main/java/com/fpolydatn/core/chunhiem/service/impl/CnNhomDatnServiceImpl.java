package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
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
 * @author thangncph26123
 */
@Service
public class CnNhomDatnServiceImpl implements CnNhomDatnService {

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private CnSinhVienRepository cnSinhVienRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<CnNhomDatnResponse> search(CnFindNhomDatnRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnNhomDatnResponse> res = cnNhomDatnRepository.findNhomDatn(request, pageable);
        return new PageableObject(res);
    }

    @Override
    public boolean chotDeTai(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CNBM_CHOT);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        return true;
    }

    @Override
    public boolean tuChoiChotDeTai(String id, String nhanXet) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CAN_SUA);
        nhomDatnCheck.get().setNhanXet(nhanXet);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        return true;
    }

    @Override
    public Boolean delete(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        cnNhomDatnRepository.delete(nhomDatnCheck.get());
        List<SinhVien> listSinhVien = cnSinhVienRepository.getAllSinhVien(id);
        for (SinhVien xx : listSinhVien) {
            cnSinhVienRepository.deleteSinhVienOutGroup(xx.getMaSinhVien());
        }
        return true;
    }

    @Override
    public NhomDatn findById(String id) {
        Optional<NhomDatn> nhomdatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomdatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return nhomdatnCheck.get();
    }

    @Override
    public boolean create(@Valid final CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        String idGiangVien = null;
        if (cnCreateNhomDatnRequest.getMaGiangVien() != null && cnCreateNhomDatnRequest.getMaGiangVien() != "") {
            idGiangVien = cnNhomDatnRepository.getIdByTenTaiKhoanGvhd(cnCreateNhomDatnRequest.getMaGiangVien());
            if (idGiangVien == "" || idGiangVien == null) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_EXIST);
            }
        }
        String idTruongNhomCheck = cnNhomDatnRepository.getSinhVienByMaSinhVien(cnCreateNhomDatnRequest.getMaSinhVien());
        if (idTruongNhomCheck == null) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        String idTruongNhom = cnNhomDatnRepository.getSinhVienByMaSinhVienAndMaNhom(cnCreateNhomDatnRequest.getMaSinhVien());
        if (idTruongNhom == null) {
            throw new RestApiException(Message.STUDENT_HAVE_GROUP);
        }
        NhomDatn nhomDatn = new NhomDatn();
        nhomDatn.setTruongNhomId(idTruongNhom);
        nhomDatn.setGiangVienId(idGiangVien);
        nhomDatn.setMaNhom(cnCreateNhomDatnRequest.getMaNhom());
        nhomDatn.setMatKhau(cnCreateNhomDatnRequest.getMatKhau());
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        nhomDatn.setCoSoId(cnCreateNhomDatnRequest.getCoSoId());
        nhomDatn.setDotDangKyId(cnCreateNhomDatnRequest.getDotDangKyId());
        nhomDatn.setId(cnNhomDatnRepository.save(nhomDatn).getId());
        cnNhomDatnRepository.setNhomForTruongNhom(cnNhomDatnRepository.save(nhomDatn).getId(), idTruongNhom);
        return true;
    }

}
