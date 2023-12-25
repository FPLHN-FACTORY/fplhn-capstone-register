package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.common.IUseCase;
import com.fpolydatn.core.common.MocThoiGianHelper;
import com.fpolydatn.core.common.NhomDatnHelper;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.ThamGiaNhomDatnRequest;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.entity.*;
import com.fpolydatn.infrastructure.EmailSender;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.MonDatnRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public class TaoNhomDatnUseCase implements IUseCase<SvCreateNhomDatnRequest, NhomDatn> {

    private final SvNhomDatnRepository repository;
    private final GiangVienHuongDanRepository gvhdRepository;
    private final SinhVienRepository sinhVienRepository;
    private final MonDatnRepository monDatnRepository;
    private final MocThoiGianHelper mocThoiGianHelper;
    private final EmailSender emailSender;
    private final NhomDatnHelper nhomDatnHelper;
    private final ThamGiaNhomDatnUseCase thamGiaNhomDatnUseCase;

    @Autowired
    public TaoNhomDatnUseCase(SvNhomDatnRepository repository,
                              @Qualifier(GiangVienHuongDanRepository.NAME)
                                      GiangVienHuongDanRepository gvhdRepository,
                              @Qualifier(MonDatnRepository.NAME)
                                          MonDatnRepository monDatnRepository,
                              @Qualifier(SinhVienRepository.NAME)
                                          SinhVienRepository sinhVienRepository,
                              ThamGiaNhomDatnUseCase thamGiaNhomDatnUseCase,
                              EmailSender emailSender,
                              NhomDatnHelper nhomDatnHelper,
                              MocThoiGianHelper mocThoiGianHelper) {
        this.repository = repository;
        this.gvhdRepository = gvhdRepository;
        this.monDatnRepository = monDatnRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.thamGiaNhomDatnUseCase = thamGiaNhomDatnUseCase;
        this.emailSender = emailSender;
        this.nhomDatnHelper = nhomDatnHelper;
        this.mocThoiGianHelper = mocThoiGianHelper;
    }

    @Override
    @Transactional
    public NhomDatn execute(SvCreateNhomDatnRequest request) {
        kiemTra(request);

        NhomDatn nhomDatn = new NhomDatn();
        if (request.getLaNhomBiMat()) {
            nhomDatn.setMatKhau(String.valueOf((int) (Math.random() * 100000000)));
        }
        nhomDatn.setDotDangKyId(request.getDotDangKyId());
        nhomDatn.setCoSoId(request.getCoSoId());
        nhomDatn.setTruongNhomId(request.getNguoiTaoId());
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        String maNhom = nhomDatnHelper.genMaNhomTuDong();
        if (maNhom == null) {
            throw new RestApiException(Message.TAO_NHOM_THAT_BAI);
        }
        nhomDatn.setMaNhom(maNhom);
        nhomDatn = repository.save(nhomDatn);

        ThamGiaNhomDatnRequest thamGiaNhomDatnRequest = new ThamGiaNhomDatnRequest();
        thamGiaNhomDatnRequest.setSinhVienId(request.getNguoiTaoId());
        thamGiaNhomDatnRequest.setMonDatnId(request.getIdMonDatn());
        thamGiaNhomDatnRequest.setNhomDatnId(nhomDatn.getId());
        thamGiaNhomDatnUseCase.execute(thamGiaNhomDatnRequest);

//        if (nhomDatn.getMatKhau() != null) {
//            String[] emails = new String[]{sinhVien.get().getEmail()};
//            String[] message = new String[]{"Mật khẩu nhóm DATN của bạn là: " + nhomDatn.getMatKhau()};
//            emailSender.sendEmail(emails, "[FCR] Thông báo mật khẩu nhóm",
//                    "Tạo nhóm thành công", message);
//        }

        return nhomDatn;
    }

    private void kiemTra(SvCreateNhomDatnRequest request) {
        kiemTraThoiHanTaoNhom();
        kiemTraDieuKienSinhVian(request);
        kiemTraGiangVienLuaChon(request);
        kiemTraMonDatn(request);
    }

    private void kiemTraThoiHanTaoNhom() {
        if (!mocThoiGianHelper.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
    }
    private void kiemTraDieuKienSinhVian(SvCreateNhomDatnRequest request) {
        String sinhVienTaoId = request.getNguoiTaoId();
        SinhVien sinhVienTao = sinhVienRepository.findById(sinhVienTaoId).orElse(null);
        if (sinhVienTao == null) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }
        if (sinhVienTao.getNhomId() != null) {
            throw new RestApiException(Message.SINH_VIEN_DA_CO_NHOM);
        }
    }
    private void kiemTraGiangVienLuaChon(SvCreateNhomDatnRequest request) {
        String giangVienId = request.getGiangVienId();
        if (giangVienId != null && giangVienId.trim().length() != 0) {
            GiangVienHuongDan gvhd = gvhdRepository.findById(giangVienId).orElse(null);
            if (gvhd == null) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
            }
            int soNhomGvDangHuongDan = gvhdRepository.countSoNhomGiangVienDangHuongDan(giangVienId);
            int soNhomHuongDanToiDa = gvhd.getSoNhomHuongDanToiDa();
            if (soNhomGvDangHuongDan >= soNhomHuongDanToiDa) {
                throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_ALREADY_HAVE_ENOUGH_GROUPS);
            }
        }
    }
    private void kiemTraMonDatn(SvCreateNhomDatnRequest request) {
        String monDatnId = request.getIdMonDatn();
        if (!monDatnRepository.existsById(monDatnId)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }

        // Cần kiểm tra môn đăng ký có tương đương với môn chương trình không??
    }
}
