package com.fpolydatn.infrastructure.security;

import com.fpolydatn.model.entity.CoSoEntity;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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

    @Autowired
    private CoSoRepository coSoRepository;

    public void processOAuthPostLogin(HttpServletResponse response,
                                      String coSoID, String email, String role) throws IOException {
        Optional<CoSoEntity> coSo = coSoRepository.findById(coSoID);
        if (!coSo.isPresent()) {
            response.sendRedirect("/");
        }
        switch (role.toLowerCase()) {
            case "gv": {
                if (gvhdRepository.existsByEmailFptAndCoSoId(coSoID, email)) {
                    response.sendRedirect("/gv-chon-co-so");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case "sv": {
                if (sinhVienRepository.existsByEmailAndCoSoId(coSoID, email)) {
                    response.sendRedirect("/sv-chon-co-so");
                } else {
                    response.sendRedirect("/admin/hoc-ky");
                }
                break;
            }
            case "cb": {
                if (canBoRepository.existsByEmailFptAndCoSoId(coSoID, email)) {
                    response.sendRedirect("/cb-chon-co-so");
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
