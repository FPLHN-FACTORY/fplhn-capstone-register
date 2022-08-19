package com.fpolydatn.infrastructure.security;

import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
        switch (role.toLowerCase()) {
            case "gv": {
                if (Objects.isNull(gvhdRepository.findGiangVienByEmailAndCoSo(coSoID, email))) {
                    response.sendRedirect("/authentication/giang-vien");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case "sv": {
                if (Objects.isNull(sinhVienRepository.findSinhVienByEmailAndCoSo(coSoID, email))) {
                    response.sendRedirect("/authentication/sinh-vien");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case "cb": {
                if (Objects.isNull(canBoRepository.findCanBoByEmailAndCoSo(coSoID, email))) {
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
