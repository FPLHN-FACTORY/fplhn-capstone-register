package com.fpolydatn.infrastructure.security;

import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.model.domain.GiangVienHuongDan;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
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
    private CoSoRepository coSoRepository;

    public void processOAuthPostLogin(HttpServletResponse response, String coSoID, String username) throws IOException {

        CoSo coSo = coSoRepository.findCoSoByID(coSoID);
        if (Objects.isNull(coSo)) {
            response.sendRedirect("/index");
        }
        GiangVienHuongDan existUser = giangVienHuongDanRepository.findGiangVienByEmailAndCoSo(coSoID, username);

        if (Objects.isNull(existUser)) {
            response.sendRedirect("/gv-chon-co-so");
        } else {
            response.sendRedirect("/admin/hoc-ky");
        }

    }
}
