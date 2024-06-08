package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenNhomRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.repository.SvGiangVienHuongDanRepository;
import com.fpolydatn.core.sinhvien.repository.SvMonDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.RabbitLogger;
import com.fpolydatn.core.common.log.LogObject;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.core.common.CommonHelper;
import com.fpolydatn.infrastructure.EmailSender;
import com.fpolydatn.core.common.MocThoiGianHelper;
import com.fpolydatn.core.common.NhomDatnHelper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * @author hoangnt
 */
@Service
@Validated
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
    private MocThoiGianHelper mocThoiGianHelper;

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private SvGiangVienHuongDanRepository svGiangVienHuongDanRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private RabbitLogger rabbitLogger;

    @Autowired
    private FpolyDatnSession fpolyDatnSession;

    @Override
    public Boolean checkTruongNhom(String idNhomDatn, String idCoSo, String idUser, String dotDangKyId) {
        String idTruongNhom = svNhomDatnRepository.getIdTruongNhom(idNhomDatn, idCoSo, dotDangKyId);
        return idUser.equals(idTruongNhom);
    }

    @Override
    @Synchronized
    public Boolean chuyenQuyenTruongNhom(String idUser, String idTruongNhomMoi) {
        if (!mocThoiGianHelper.checkMocThoiGianSinhVien()) {
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
        emailSender.sendEmail(email, "[FCR] Thông báo chuyển quyền trưởng nhóm",
                "Chuyển quyền trưởng nhóm",
                message);
        svNhomDatnRepository.save(nhomDatn.get());
        ChuyenNganh chuyenNganhFind = chuyenNganhRepository.findById(truongNhom.get().getChuyenNganhId()).get();
        try {
            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LogObject logObject = writerFileCSV.
                    createLoggerObject("Sinh viên '" + truongNhom.get().getMaSinhVien()
                                    + "' đã đổi quyền trưởng nhóm cho sinh viên '" + truongNhomMoi.get().getMaSinhVien() + "'",
                            pathFile, "Thành công");
            logObject.setTenNhom(nhomDatn.get().getMaNhom());
            logObject.setIdChuyenNganh(chuyenNganhFind.getId());
            logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
            rabbitLogger.log(logObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    @Synchronized
    public String chonGvhd(String idUser, String idGiangVien, String coSoId, String dotDangKyId) {
        if (!mocThoiGianHelper.checkMocThoiGianSinhVien()) {
            throw new RestApiException(Message.HET_HAN_QUYEN_SINH_VIEN);
        }
        Optional<GiangVienHuongDan> giangVienHuongDan = svGiangVienHuongDanRepository.findById(idGiangVien);
        if (!giangVienHuongDan.isPresent()) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        Integer countSoNhomDangHuongDan = svGiangVienHuongDanRepository.countSoNhomGiangVienDangHuongDan(idGiangVien, coSoId, dotDangKyId);
        if (countSoNhomDangHuongDan >= giangVienHuongDan.get().getSoNhomHuongDanToiDa()) {
            throw new RestApiException(Message.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM);
        }
        NhomDatn nhomDatn = svNhomDatnRepository.getNhomDatnByTruongNhomId(idUser, coSoId, dotDangKyId);
        if (nhomDatn == null) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatn.getTrangThai().ordinal() != 0) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        String giangVienCu = nhomDatn.getGiangVienId();
        String giangVienMoi = idGiangVien;
        nhomDatn.setGiangVienId(idGiangVien);
        svNhomDatnRepository.save(nhomDatn);
        SinhVien sinhVienFind = svSinhVienRepository.findById(idUser).get();
        ChuyenNganh chuyenNganhFind = chuyenNganhRepository.findById(sinhVienFind.getChuyenNganhId()).get();
        if (giangVienCu == null && giangVienMoi != null) {
            try {
                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Sinh viên " + sinhVienFind.getTenSinhVien() + "-" + sinhVienFind.getMaSinhVien()
                                        + " đã chọn '" + giangVienHuongDan.get().getTenTaiKhoan() + "-" + giangVienHuongDan.get().getTenGvhd() + "'"
                                        + " làm giảng viên hướng dẫn của nhóm (Nhóm'" + nhomDatn.getMaNhom() + "').",
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatn.getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.log(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (giangVienCu != null && giangVienMoi != null && !giangVienCu.equals(giangVienMoi)) {
            Optional<GiangVienHuongDan> giangVienHuongDanCu = svGiangVienHuongDanRepository.findById(giangVienCu);
            try {
                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
                LogObject logObject = writerFileCSV.
                        createLoggerObject("Sinh viên " + sinhVienFind.getTenSinhVien() + "-" + sinhVienFind.getMaSinhVien()
                                        + " đã đổi giảng viên hướng dẫn của nhóm (Nhóm'" + nhomDatn.getMaNhom() + "') từ '"
                                        + giangVienHuongDanCu.get().getTenTaiKhoan() + "-" + giangVienHuongDanCu.get().getTenGvhd() + "' thành "
                                        + giangVienHuongDan.get().getTenTaiKhoan() + "-" + giangVienHuongDanCu.get().getTenGvhd() + ".",
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatn.getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                rabbitLogger.log(logObject);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return giangVienHuongDan.get().getTenGvhd() + " - " + giangVienHuongDan.get().getTenTaiKhoan();
    }

}
