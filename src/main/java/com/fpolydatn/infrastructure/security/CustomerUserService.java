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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class CustomerUserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String email) {
        String dotDangKyID = commonHelper.getDotDangKyHienTai();
        String role = (String) session.getAttribute(SessionConstant.ROLE);
        String coSoID = (String) session.getAttribute(SessionConstant.CO_SO_ID);
        System.out.println(coSoID.toLowerCase());
        SimpleEntityProj account = null;

        switch (role.toLowerCase()) {
//            case ActorConstant.GIANG_VIEN: {
//                account = gvhdRepository.findSimpleEntityByTenGiangVienHuongDan(coSoID, email, dotDangKyID);
//                break;
//            }
//            case ActorConstant.SINH_VIEN: {
//                account = sinhVienRepository.findSimpleEntityByTenSinhVien(coSoID, email, dotDangKyID);
//                break;
//            }
            case ActorConstant.CHU_NHIEM: {
                account = canBoRepository.findSimpleEntityByTenCanBo(coSoID,
                        email, 1);
                break;
            }
//            case ActorConstant.DAO_TAO: {
//                account = canBoRepository.findSimpleEntityByTenCanBo(coSoID,
//                        email, 0);
//                break;
//            }
        }

        if (Objects.nonNull(account)) {
            session.setAttribute(SessionConstant.USER, account);
        } else {
            session.setAttribute("messageError", "Tài khoản không được phép đăng nhập vào hệ thống");
        }
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(account);
    }
}