package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private CommonHelper commonHelper;

    public void processOAuthPostLogin(HttpServletResponse response,
                                      String coSoID, String email, String role) throws IOException {

        String dotDangKyID = commonHelper.getDotDangKyHienTai();

        SimpleEntityProj account = null;
        switch (role.toLowerCase()) {
            case ActorConstant.GIANG_VIEN: {
                account = gvhdRepository.findSimpleEntityByTenGiangVienHuongDan(coSoID, email, dotDangKyID);
                if (Objects.isNull(account)) {
                    response.sendRedirect("/authentication/" + ActorConstant.GIANG_VIEN);
                } else {
                    response.sendRedirect("/giang-vien/nhom-datn");
                }
                break;
            }
            case ActorConstant.SINH_VIEN: {
                account = sinhVienRepository.findSimpleEntityByTenSinhVien(coSoID, email, dotDangKyID);
                if (Objects.isNull(account)) {
                    response.sendRedirect("/authentication/" + ActorConstant.SINH_VIEN);
                } else {
                    response.sendRedirect("/sinh-vien");
                }
                break;
            }
            case ActorConstant.CHU_NHIEM: {
                account = canBoRepository.findSimpleEntityByTenCanBo(coSoID,
                        email, VaiTro.CHU_NHIEM_BO_MON.ordinal());
                if (Objects.isNull(account)) {
                    response.sendRedirect("/authentication/" + ActorConstant.CHU_NHIEM);
                } else {
                    response.sendRedirect("/chu-nhiem/nhom-datn");
                }
                break;
            }
            case ActorConstant.DAO_TAO: {
                account = canBoRepository.findSimpleEntityByTenCanBo(coSoID,
                        email, VaiTro.DAO_TAO.ordinal());
                if (Objects.isNull(account)) {
                    response.sendRedirect("/authentication/" + ActorConstant.DAO_TAO);
                } else {
                    response.sendRedirect("/dao-tao/sinh-vien");
                }
                break;
            }
            default: {
                response.sendRedirect("/");
            }
        }

        if (Objects.nonNull(account)) {
            session.setAttribute(SessionConstant.USER, account);
        } else {
            session.setAttribute("messageError", "Tài khoản không được phép đăng nhập vào hệ thống");
        }
    }
}
