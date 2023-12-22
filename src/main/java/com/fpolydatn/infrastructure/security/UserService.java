package com.fpolydatn.infrastructure.security;

import com.fpolydatn.core.common.base.SimpleUserCurrent;
import com.fpolydatn.infrastructure.constant.ActorConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import com.fpolydatn.core.common.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author HangNT
 */
@Service
public class UserService {

    @Autowired
    private HttpSession session;

    @Autowired
    @Qualifier(GiangVienHuongDanRepository.NAME)
    private GiangVienHuongDanRepository gvhdRepository;

    @Autowired
    @Qualifier(SinhVienRepository.NAME)
    private SinhVienRepository sinhVienRepository;

    @Autowired
    @Qualifier(CanBoRepository.NAME)
    private CanBoRepository canBoRepository;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Value("${spring.mail.username}")
    private String mailUsername;

    public void processOAuthPostLogin(HttpServletResponse response,
                                      String coSoID, String email, String name, String role) throws IOException {
        String dotDangKyID = commonHelper.getDotDangKyHienTai();
        if (dotDangKyID != null) {
            session.setAttribute(SessionConstant.TEN_DOT_DANG_KY, dotDangKyRepository.findById(dotDangKyID).get().getTenDotDangKy());
        }
        SimpleEntityProj account = null;
        Boolean checkAccountAdmin = email.equals(mailUsername);
        switch (role.toLowerCase()) {
            case ActorConstant.GIANG_VIEN: {
                account = gvhdRepository.findSimpleEntityByTenGiangVienHuongDan(coSoID, email, dotDangKyID);
                if (Objects.isNull(account)) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/giang-vien/nhom-datn");
                }
                break;
            }
            case ActorConstant.SINH_VIEN: {
                account = sinhVienRepository.findSimpleEntityByTenSinhVien(coSoID, email, dotDangKyID,
                        TrangThaiSinhVien.DU_DIEU_KIEN.ordinal());
                if (Objects.isNull(account)) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/sinh-vien");
                }
                break;
            }
            case ActorConstant.CHU_NHIEM: {
                account = canBoRepository.findSimpleEntityByTenCanBo(coSoID,
                        email, VaiTro.CHU_NHIEM_BO_MON.ordinal());
                if (Objects.isNull(account)) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/chu-nhiem/nhom-datn");
                }
                break;
            }
            case ActorConstant.DAO_TAO: {
                account = canBoRepository.findSimpleEntityByTenCanBo(coSoID,
                        email, VaiTro.DAO_TAO.ordinal());
                if (Objects.isNull(account)) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/dao-tao/sinh-vien");
                }
                break;
            }
            case ActorConstant.ADMIN: {
                if (checkAccountAdmin) {
                    response.sendRedirect("/admin/bit-dev/home");
                } else {
                    response.sendRedirect("/admin/bit-dev");
                }
                break;
            }
            default: {
                response.sendRedirect("/");
            }
        }

        if (role.equals(ActorConstant.ADMIN)) {
            if (checkAccountAdmin) {
                SimpleUserCurrent simpleUserCurrent = new SimpleUserCurrent();
                simpleUserCurrent.setName(name);
                session.setAttribute(SessionConstant.USER, simpleUserCurrent);
            } else {
                session.setAttribute("messageError", "Tài khoản không được phép đăng nhập vào hệ thống");
            }
        } else {
            if (Objects.nonNull(account)) {
                session.setAttribute(SessionConstant.USER, account);
            } else {
                session.setAttribute("messageError", "Tài khoản không được phép đăng nhập vào hệ thống");
            }
        }
    }
}
