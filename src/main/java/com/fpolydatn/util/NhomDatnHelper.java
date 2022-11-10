package com.fpolydatn.util;

import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.NhomDatnRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author hungpv
 */
@Component
public class NhomDatnHelper {
    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private FpolyDatnSession session;

    @Synchronized
    public String genMaNhomTuDong() {
        String coSoId = session.getCoSoId();
        String text = nhomDatnRepository.getMaCoSo(coSoId);
        System.out.println(text);
        String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
        int count = Integer.parseInt(nhomDatnRepository.genMaNhomTuDong(dotDangKyHienTai));
        count++;
        return text + "_" + count;
    }
}
