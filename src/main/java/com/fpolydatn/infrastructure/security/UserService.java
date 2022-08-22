package com.fpolydatn.infrastructure.security;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ActorConstant;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

//    public void processOAuthPostLogin(HttpServletResponse response,
//                                      String coSoID, String email, String role) throws IOException {
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues();
//
//        switch (role.toLowerCase()) {
//            case ActorConstant.GIANG_VIEN: {
//                GiangVienHuongDan gvhd = new GiangVienHuongDan();
//                gvhd.setCoSoId(coSoID);
//                gvhd.setEmailFpt(email);
//
//                if (!gvhdRepository.findBy(Example.of(gvhd, matcher), q -> q.exists())) {
//                    response.sendRedirect("/authentication/giang-vien");
//                } else {
//                    response.sendRedirect("/admin/hoc-ky");
//                }
//                break;
//            }
//            case ActorConstant.SINH_VIEN: {
//                SinhVien sv = new SinhVien();
//                sv.setCoSoId(coSoID);
//                sv.setEmail(email);
//
//                if (!sinhVienRepository.findBy(Example.of(sv, matcher), q -> q.exists())) {
//                    response.sendRedirect("/authentication/sinh-vien");
//                } else {
//                    response.sendRedirect("/admin/hoc-ky");
//                }
//                break;
//            }
//            case ActorConstant.CHU_NHIEM: {
//                CanBo cb = new CanBo();
//                cb.setCoSoId(coSoID);
//                cb.setEmailFpt(email);
//                cb.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
//
//                if (!canBoRepository.findBy(Example.of(cb, matcher), q -> q.exists())) {
//                    response.sendRedirect("/authentication/can-bo");
//                } else {
//                    response.sendRedirect("/admin/hoc-ky");
//                }
//                break;
//            }
//            case ActorConstant.DAO_TAO: {
//                CanBo cb = new CanBo();
//                cb.setCoSoId(coSoID);
//                cb.setEmailFpt(email);
//                cb.setVaiTro(VaiTro.DAO_TAO);
//
//                if (!canBoRepository.findBy(Example.of(cb, matcher), q -> q.exists())) {
//                    response.sendRedirect("/authentication/can-bo");
//                } else {
//                    response.sendRedirect("/admin/hoc-ky");
//                }
//                break;
//            }
//            default: {
//                response.sendRedirect("/");
//            }
//        }
//    }
}
