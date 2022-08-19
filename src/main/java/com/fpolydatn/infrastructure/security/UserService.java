package com.fpolydatn.infrastructure.security;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private GiangVienHuongDanRepository gvhdRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private CanBoRepository canBoRepository;

    public void processOAuthPostLogin(HttpServletResponse response,
                                      String coSoID, String email, String role) throws IOException {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues();

        switch (role.toLowerCase()) {
            case "gv": {
                GiangVienHuongDan gvhd = new GiangVienHuongDan();
                gvhd.setCoSoId(coSoID);
                gvhd.setEmailFpt(email);

                if (!gvhdRepository.findBy(Example.of(gvhd, matcher), q -> q.exists())) {
                    response.sendRedirect("/authentication/giang-vien");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case "sv": {
                SinhVien sv = new SinhVien();
                sv.setCoSoId(coSoID);
                sv.setEmail(email);

                if (!sinhVienRepository.findBy(Example.of(sv, matcher), q -> q.exists())) {
                    response.sendRedirect("/authentication/sinh-vien");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case "cb": {
                CanBo cb = new CanBo();
                cb.setCoSoId(coSoID);
                cb.setEmailFpt(email);

                if (!canBoRepository.findBy(Example.of(cb, matcher), q -> q.exists())) {
                    response.sendRedirect("/authentication/can-bo");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            default: {
                response.sendRedirect("/");
            }
        }
    }
}
