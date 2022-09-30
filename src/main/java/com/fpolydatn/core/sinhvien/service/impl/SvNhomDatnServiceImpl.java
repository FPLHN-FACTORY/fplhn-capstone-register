package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.repository.SvGiangVienHuongDanRepository;
import com.fpolydatn.core.sinhvien.repository.SvMonDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.util.GenMaNhomTuDong;
import com.fpolydatn.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author hoangnt
 */
@Service
public class SvNhomDatnServiceImpl implements SvNhomDatnService {

    @Autowired
    private SvNhomDatnRepository svNhomDatnRepository;

    @Autowired
    private SvMonDatnRepository svMonDatnRepository;

    @Autowired
    private SvSinhVienRepository svSinhVienRepository;

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private GenMaNhomTuDong genMaNhomTuDong;

    @Autowired
    private ValidationUtils validationUtils;

    @Autowired
    private SvGiangVienHuongDanRepository svGiangVienHuongDanRepository;

    @Override
    public List<SvDetailNhomDatnResponse> getDetailNhomDatnById(String id) {
        return svNhomDatnRepository.getDetailNhomDatnById(id);
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
    public boolean addSinhVienTheoNhom(final SvAddSinhVienRequest req) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(req.getIdUser());
        Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(req.getIdNhom());
        if (!nhomDatn.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        Optional<SinhVien> truongNhom = svSinhVienRepository.findById(nhomDatn.get().getTruongNhomId());
        if (!sinhVien.get().getMonDatnId().equals(truongNhom.get().getMonDatnId())) {
            sinhVien.get().setMonDatnId(truongNhom.get().getMonDatnId());
        }
        String matKhau = svNhomDatnRepository.getMatKhauById(req.getIdNhom());
        int soLuongThanhVien = svSinhVienRepository.getSoLuongThanhVienById(req.getIdNhom());
        if (soLuongThanhVien == 7){
            throw new RestApiException(Message.NHOM_DA_DU_THANH_VIEN);
        }
        if (!(matKhau == null)) {
            if (!matKhau.trim().equals(req.getInputPassword().trim())){
                throw new RestApiException(Message.MAT_KHAU_KHONG_CHINH_XAC);
            }
        }
        sinhVien.get().setNhomId(nhomDatn.get().getId());
        svSinhVienRepository.save(sinhVien.get());
        return true;
    }

    @Override
    public NhomDatn createNhomDatn(@Valid SvCreateNhomDatnRequest svNhomDatnRequest, String idUser) {
        String giangVienId = svNhomDatnRequest.getGiangVienId();
        String dotDangKyId = "";
        if (giangVienId != null) {
            Optional<GiangVienHuongDan> giangVienHuongDan = svGiangVienHuongDanRepository.findById(giangVienId);
            if (!giangVienHuongDan.isPresent()) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
            }
        }
        if (svNhomDatnRequest.getGiangVienId() != null) {
            if (validationUtils.soNhomGiangVienDangHuongDan(svNhomDatnRequest.getGiangVienId(), dotDangKyId) >= 7) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_ALREADY_HAVE_ENOUGH_GROUPS);
            }
        }
        Optional<MonDatn> monDatn = svMonDatnRepository.findById(svNhomDatnRequest.getIdMonDatn());
        if (!monDatn.isPresent()) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(idUser);
        if (!sinhVien.get().getMonDatnId().equals(svNhomDatnRequest.getIdMonDatn())){
            sinhVien.get().setMonDatnId(svNhomDatnRequest.getIdMonDatn());
        }
        NhomDatn nhomDatn = formUtils.convertToObject(NhomDatn.class, svNhomDatnRequest);
        nhomDatn.setTruongNhomId(idUser);
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        nhomDatn.setMaNhom(genMaNhomTuDong.genMaNhomTuDong());
        nhomDatn = svNhomDatnRepository.save(nhomDatn);
        sinhVien.get().setNhomId(nhomDatn.getId());
        svSinhVienRepository.save(sinhVien.get());
        return nhomDatn;
    }

    @Override
    public Boolean updateTenDeTai(SvUpdateTenDeTaiRequest svUpdateTenDeTaiRequest) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(svUpdateTenDeTaiRequest.getIdUser());
        if(!sinhVien.isPresent()){
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(sinhVien.get().getNhomId());
        if (!nhomDatn.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_CO_NHOM);
        }

        if (!svUpdateTenDeTaiRequest.getIdUser().equals(nhomDatn.get().getTruongNhomId())) {
            throw new RestApiException(Message.KHONG_PHAI_TRUONG_NHOM);
        }
        if (nhomDatn.get().getTrangThai().ordinal() == 4 || nhomDatn.get().getTrangThai().ordinal() == 5) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatn.get().setTenDeTai(svUpdateTenDeTaiRequest.getTenDeTai());
        svNhomDatnRepository.save(nhomDatn.get());
        return true;
    }

    @Override
    public Boolean roiNhom(String idUser) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(idUser);
        if(!sinhVien.isPresent()){
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(sinhVien.get().getNhomId());
        if (!nhomDatn.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_CO_NHOM);
        }
        Integer soThanhVien = svSinhVienRepository.getSoLuongThanhVienById(nhomDatn.get().getId());
        if (soThanhVien != 1) {
            if (idUser.equals(nhomDatn.get().getTruongNhomId())) {
                throw new RestApiException(Message.ROI_NHOM_KHI_LA_TRUONG_NHOM);
            }
        } else {
            svNhomDatnRepository.delete(nhomDatn.get());
        }
        sinhVien.get().setNhomId(null);
        svSinhVienRepository.save(sinhVien.get());
        return true;
    }

    @Override
    public Boolean chuyenQuyenTruongNhom(String idUser, String idTruongNhomMoi) {
        Optional<SinhVien> truongNhom = svSinhVienRepository.findById(idUser);
        if(!truongNhom.isPresent()){
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(truongNhom.get().getNhomId());
        if (!nhomDatn.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_CO_NHOM);
        }
        if (!nhomDatn.get().getTruongNhomId().equals(truongNhom.get().getId())) {
            throw new RestApiException(Message.KHONG_PHAI_TRUONG_NHOM);
        }
        Optional<SinhVien> truongNhomMoi = svSinhVienRepository.findById(idTruongNhomMoi);
        if(!truongNhomMoi.isPresent()){
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        if (truongNhom.get().getId().equals(idTruongNhomMoi)) {
            throw new RestApiException(Message.SINH_VIEN_DANG_LA_TRUONG_NHOM);
        }
        if (!truongNhom.get().getNhomId().equals(truongNhomMoi.get().getNhomId())) {
            throw new RestApiException(Message.TRUONG_NHOM_MOI_KHONG_CUNG_NHOM);
        }
        nhomDatn.get().setTruongNhomId(truongNhomMoi.get().getId());
        svNhomDatnRepository.save(nhomDatn.get());
        return true;
    }

    @Override
    public Boolean chonGvhd(String idUser, String idGiangVien) {
        Optional<GiangVienHuongDan> giangVienHuongDan = svGiangVienHuongDanRepository.findById(idGiangVien);
        if (!giangVienHuongDan.isPresent()){
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        NhomDatn nhomDatn = svNhomDatnRepository.getNhomDatnByTruongNhomId(idUser);
        if (nhomDatn == null) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatn.getTrangThai().ordinal() != 0){
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatn.setGiangVienId(idGiangVien);
        svNhomDatnRepository.save(nhomDatn);
        return true;
    }
}
