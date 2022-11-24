package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
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
import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.EmailSender;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.NhomDatnHelper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
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

    @Autowired
    private NhomDatnHelper nhomDatnHelper;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    private SvGiangVienHuongDanRepository svGiangVienHuongDanRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public List<SvDetailNhomDatnResponse> getDetailNhomDatnById(String id, String coSoId, String dotDangKyId) {
        return svNhomDatnRepository.getDetailNhomDatnById(id, coSoId, dotDangKyId);
    }

    @Override
    public Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser, String dotDangKyId) {
        String idTruongNhom = svNhomDatnRepository.getIdTruongNhom(idNhomDatn, idCoSo, dotDangKyId);
        return idUser.equals(idTruongNhom);
    }

    @Override
    public PageableObject<SvNhomDatnResponse> findAllNhomDatn(final SvFindNhomDatnRequest request, String idUser) {
        Optional<SinhVien> sv = svSinhVienRepository.findById(idUser);
        Optional<MonDatn> monDatn = svMonDatnRepository.findById(sv.get().getMonChuongTrinhId());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<SvNhomDatnResponse> res = svNhomDatnRepository.findAllNhomDatn(pageable, request, monDatn.get().getNhomMonDatnId());
        return new PageableObject(res);
    }

    @Override
    public List<SvShowSinhVienTheoNhomResponse> showDanhSachSinhVien(String id, String coSoId, String dotDangKyId) {
        List<SvShowSinhVienTheoNhomResponse> res = svNhomDatnRepository.getAllSinhVienByNhomDatnId(id, coSoId, dotDangKyId);
        return res;
    }

    @Override
    @Synchronized
    public boolean addSinhVienTheoNhom(final SvAddSinhVienRequest req) {
        boolean check = mocThoiGianCommon.checkMocThoiGianSinhVien();
        if (!check) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(req.getIdNhom());
        if (!nhomDatn.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }

        String matKhau = nhomDatn.get().getMatKhau();
        if (!(matKhau == null)) {
            if (!matKhau.trim().equals(req.getInputPassword().trim())) {
                throw new RestApiException(Message.MAT_KHAU_KHONG_CHINH_XAC);
            }
        }

        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(req.getIdUser());
        if (sinhVien.get().getThoiDiemRoiNhomGanNhat() != null // Có dấu vết rời nhóm
                && // Chưa rời nhóm đủ 12 tiếng
                Calendar.getInstance().getTimeInMillis() - Constants.MUOI_HAI_TIENG
                        < sinhVien.get().getThoiDiemRoiNhomGanNhat()) {
            throw new RestApiException(Message.THOI_GIAN_ROI_NHOM_CHUA_DU_12_TIENG);
        }

        int soLuongThanhVien = svSinhVienRepository.getSoLuongThanhVienById(req.getIdNhom(),
                req.getCoSoId(), req.getDotDangKyId());
        if (soLuongThanhVien == Constants.SO_THANH_VIEN_TOI_DA) {
            throw new RestApiException(Message.NHOM_DA_DU_THANH_VIEN);
        }

        Optional<SinhVien> truongNhom = svSinhVienRepository.findById(nhomDatn.get().getTruongNhomId());
        sinhVien.get().setMonDatnId(truongNhom.get().getMonDatnId());
        sinhVien.get().setNhomId(nhomDatn.get().getId());
        svSinhVienRepository.save(sinhVien.get());
        return true;
    }

    @Override
    @Synchronized
    public NhomDatn createNhomDatn(@Valid SvCreateNhomDatnRequest svNhomDatnRequest, String idUser) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(idUser);
        if (!mocThoiGianCommon.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        if (sinhVien.get().getThoiDiemRoiNhomGanNhat() != null) {
            if (Calendar.getInstance().getTimeInMillis() - Constants.MUOI_HAI_TIENG < sinhVien.get().getThoiDiemRoiNhomGanNhat()) {
                throw new RestApiException(Message.THOI_GIAN_ROI_NHOM_CHUA_DU_12_TIENG);
            }
        }
        String giangVienId = svNhomDatnRequest.getGiangVienId();
        if (giangVienId != null) {
            Optional<GiangVienHuongDan> giangVienHuongDan = svGiangVienHuongDanRepository.findById(giangVienId);
            if (!giangVienHuongDan.isPresent()) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
            }
        }
        if (svNhomDatnRequest.getGiangVienId() != null) {
            if (commonHelper.getSoNhomGiangVienDangHuongDan(svNhomDatnRequest.getGiangVienId()) >= 7) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_ALREADY_HAVE_ENOUGH_GROUPS);
            }
        }
        Optional<MonDatn> monDatn = svMonDatnRepository.findById(svNhomDatnRequest.getIdMonDatn());
        if (!monDatn.isPresent()) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        NhomDatn nhomDatn = new NhomDatn();
        nhomDatn.setGiangVienId(svNhomDatnRequest.getGiangVienId());
        String tenDeTai = svNhomDatnRequest.getTenDeTai();
        if (tenDeTai == null) {
            nhomDatn.setTenDeTai(null);
        } else {
            if ("".equals(tenDeTai.trim())) {
                nhomDatn.setTenDeTai(null);
            } else {
                nhomDatn.setTenDeTai(tenDeTai);
            }
        }
        String tenNhom = svNhomDatnRequest.getTenNhom();
        if (tenNhom == null) {
            nhomDatn.setTenNhom(null);
        } else {
            if ("".equals(tenDeTai.trim())) {
                nhomDatn.setTenNhom(null);
            } else {
                nhomDatn.setTenNhom(tenNhom);
            }
        }
        nhomDatn.setMoTa(svNhomDatnRequest.getMoTa());
        nhomDatn.setDotDangKyId(svNhomDatnRequest.getDotDangKyId());
        nhomDatn.setCoSoId(svNhomDatnRequest.getCoSoId());
        if (svNhomDatnRequest.getMatKhau()) {
            nhomDatn.setMatKhau(String.valueOf((int) (Math.random() * 100000000)));
        } else {
            nhomDatn.setMatKhau(null);
        }
        nhomDatn.setTruongNhomId(idUser);
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        String maNhom = nhomDatnHelper.genMaNhomTuDong();
        if (maNhom == null) {
            throw new RestApiException(Message.TAO_NHOM_THAT_BAI);
        }
        nhomDatn.setMaNhom(maNhom);
        nhomDatn = svNhomDatnRepository.save(nhomDatn);
        sinhVien.get().setNhomId(nhomDatn.getId());
        sinhVien.get().setMonDatnId(svNhomDatnRequest.getIdMonDatn());
        svSinhVienRepository.save(sinhVien.get());
        if (nhomDatn.getMatKhau() != null) {
            String[] email = new String[]{sinhVien.get().getEmail()};
            String[] message = new String[]{"Mật khẩu nhóm DATN của bạn là: " + nhomDatn.getMatKhau()};
            emailSender.sendEmail(email, "[FCR] Thông báo mật khẩu nhóm",
                    "Tạo nhóm thành công", message);
        }
        return nhomDatn;
    }

    @Override
    @Synchronized
    public Boolean updateTenDeTai(SvUpdateTenDeTaiRequest svUpdateTenDeTaiRequest) {
        if (!mocThoiGianCommon.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(svUpdateTenDeTaiRequest.getIdUser());
        if (!sinhVien.isPresent()) {
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
        String tenDeTai = svUpdateTenDeTaiRequest.getTenDeTai();
        if (tenDeTai == null) {
            nhomDatn.get().setTenDeTai(null);
        } else {
            if ("".equals(tenDeTai.trim())) {
                nhomDatn.get().setTenDeTai(null);
            } else {
                nhomDatn.get().setTenDeTai(tenDeTai);
            }
        }
        svNhomDatnRepository.save(nhomDatn.get());
        return true;
    }

    @Override
    @Synchronized
    public Boolean roiNhom(String idUser, String coSoId, String dotDangKyId) {
        if (!mocThoiGianCommon.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(idUser);
        Date thoiGianRoiNhom = new Date();
        if (!sinhVien.isPresent()) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        Optional<NhomDatn> nhomDatn = svNhomDatnRepository.findById(sinhVien.get().getNhomId());
        if (!nhomDatn.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_CO_NHOM);
        }
        if (idUser.equals(nhomDatn.get().getTruongNhomId())) {
            Integer soThanhVien = svSinhVienRepository.getSoLuongThanhVienById(nhomDatn.get().getId(),
                    coSoId, dotDangKyId);
            if (soThanhVien > 1) {
                throw new RestApiException(Message.ROI_NHOM_KHI_LA_TRUONG_NHOM);
            } else {
                svNhomDatnRepository.delete(nhomDatn.get());
            }
        }
        sinhVien.get().setNhomId(null);
        sinhVien.get().setThoiDiemRoiNhomGanNhat(Calendar.getInstance().getTimeInMillis());
        sinhVien.get().setMonDatnId(null);
        svSinhVienRepository.save(sinhVien.get());
        return true;
    }

    @Override
    @Synchronized
    public Boolean chuyenQuyenTruongNhom(String idUser, String idTruongNhomMoi) {
        if (!mocThoiGianCommon.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        Optional<SinhVien> truongNhom = svSinhVienRepository.findById(idUser);
        if (!truongNhom.isPresent()) {
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
        if (!truongNhomMoi.isPresent()) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        if (truongNhom.get().getId().equals(idTruongNhomMoi)) {
            throw new RestApiException(Message.SINH_VIEN_DANG_LA_TRUONG_NHOM);
        }
        if (!truongNhom.get().getNhomId().equals(truongNhomMoi.get().getNhomId())) {
            throw new RestApiException(Message.TRUONG_NHOM_MOI_KHONG_CUNG_NHOM);
        }
        nhomDatn.get().setTruongNhomId(truongNhomMoi.get().getId());
        String[] email = new String[]{truongNhomMoi.get().getEmail()};
        String[] message = null;
        if (nhomDatn.get().getMatKhau() == null) {
            message = new String[]{"Bạn đã trở thành trưởng nhóm " + nhomDatn.get().getMaNhom()};
        } else {
            message = new String[]{"Bạn đã trở thành trưởng nhóm" + nhomDatn.get().getMaNhom(),
                    "Mật khẩu nhóm DATN của bạn là: " + nhomDatn.get().getMatKhau()};
        }
        emailSender.sendEmail(email, "[FCR] Thông báo mật khẩu nhóm",
                "Chuyển quyền trưởng nhóm",
                message);
        svNhomDatnRepository.save(nhomDatn.get());
        return true;
    }

    @Override
    @Synchronized
    public Boolean chonGvhd(String idUser, String idGiangVien, String coSoId, String dotDangKyId) {
        if (!mocThoiGianCommon.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        Optional<GiangVienHuongDan> giangVienHuongDan = svGiangVienHuongDanRepository.findById(idGiangVien);
        if (!giangVienHuongDan.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        NhomDatn nhomDatn = svNhomDatnRepository.getNhomDatnByTruongNhomId(idUser, coSoId, dotDangKyId);
        if (nhomDatn == null) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatn.getTrangThai().ordinal() != 0) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatn.setGiangVienId(idGiangVien);
        svNhomDatnRepository.save(nhomDatn);
        return true;
    }

    @Override
    public Boolean checkThoiGianRoiNhom(String idUser) {
        Optional<SinhVien> sinhVien = svSinhVienRepository.findById(idUser);
        if (sinhVien.get().getThoiDiemRoiNhomGanNhat() != null) {
            if (Calendar.getInstance().getTimeInMillis() - Constants.MUOI_HAI_TIENG < sinhVien.get().getThoiDiemRoiNhomGanNhat()) {
                return false;
            }
        }
        return true;
    }

}
