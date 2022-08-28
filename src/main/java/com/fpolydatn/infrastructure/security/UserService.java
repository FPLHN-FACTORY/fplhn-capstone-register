package com.fpolydatn.infrastructure.security;

import com.fpolydatn.infrastructure.constant.ActorConstant;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

/**
 * @author HangNT
 */
@Service
public class UserService {

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

    public void processOAuthPostLogin(HttpServletResponse response,
                                      String coSoID, String email, String role) throws IOException {

        String dotDangKyID = dotDangKyRepository.findDotDangKyID(convertDateToTimeStampSecond());

        switch (role.toLowerCase()) {
            case ActorConstant.GIANG_VIEN: {
                if (Objects.isNull(gvhdRepository.findSimpleEntityByTenGiangVienHuongDan(coSoID, email, dotDangKyID))) {
                    response.sendRedirect("/authentication/giang-vien");
                } else {
                    response.sendRedirect("/giang-vien/nhom-datn");
                }
                break;
            }
            case ActorConstant.SINH_VIEN: {
                if (Objects.isNull(sinhVienRepository.findSimpleEntityByTenSinhVien(coSoID, email, dotDangKyID))) {
                    response.sendRedirect("/authentication/sinh-vien");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case ActorConstant.CHU_NHIEM: {
                if (Objects.isNull(canBoRepository.findSimpleEntityByTenCanBo(coSoID, email, 2))) {
                    response.sendRedirect("/authentication/chu-nhiem");
                } else {
                    response.sendRedirect("/dao-tao/can-bo");
                }
                break;
            }
            case ActorConstant.DAO_TAO: {
                if (Objects.isNull(canBoRepository.findSimpleEntityByTenCanBo(coSoID, email, 1))) {
                    response.sendRedirect("/authentication/dao-tao");
                } else {
                    response.sendRedirect("/dao-tao/can-bo");
                }
                break;
            }
            default: {
                response.sendRedirect("/");
            }
        }
    }
}
