package com.fpolydatn.infrastructure.security;

import com.fpolydatn.model.domain.CanBo;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.model.domain.GiangVienHuongDan;
import com.fpolydatn.model.domain.SinhVien;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.CoSoRepository;
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
    private GiangVienHuongDanRepository giangVienHuongDanRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private CanBoRepository canBoRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    public void processOAuthPostLogin(HttpServletResponse response, String coSoID, String username, String role) throws IOException {
        CoSo coSo = coSoRepository.findCoSoByID(coSoID);
        if (Objects.isNull(coSo)) {
            response.sendRedirect("/");
        }
        if (role.equalsIgnoreCase("gv")) {
            GiangVienHuongDan giangVienHuongDan = giangVienHuongDanRepository.findGiangVienByEmailAndCoSo(coSoID, username);
            if (Objects.isNull(giangVienHuongDan)) {
                response.sendRedirect("/gv-chon-co-so");
            } else {
                response.sendRedirect("/admin/hoc-ky");
            }
        }
        if (role.equalsIgnoreCase("sv")) {
            SinhVien sinhVien = sinhVienRepository.findSinhVienByEmailAndCoSo(coSoID, username);
            if (Objects.isNull(sinhVien)) {
                response.sendRedirect("/sv-chon-co-so");
            } else {
                response.sendRedirect("/admin/hoc-ky");
            }
        }
        if (role.equalsIgnoreCase("cb")) {
            CanBo canBo = canBoRepository.findCanBoByEmailAndCoSo(coSoID, username);
            if (Objects.isNull(canBo)) {
                response.sendRedirect("/cb-chon-co-so");
            } else {
                response.sendRedirect("/admin/hoc-ky");
            }
        }
    }
}
