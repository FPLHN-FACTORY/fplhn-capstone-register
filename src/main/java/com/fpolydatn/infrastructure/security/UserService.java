package com.fpolydatn.infrastructure.security;

import com.fpolydatn.model.domain.GiangVienHuongDan;
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
    private GiangVienHuongDanRepository repo;

    public void processOAuthPostLogin(HttpServletResponse response, String coSoID, String username) throws IOException {
        GiangVienHuongDan existUser = repo.findGiangVienByEmailAndCoSo(coSoID, username);

        if (Objects.isNull(existUser)) {
            response.sendRedirect("/index");
        }

    }
}
