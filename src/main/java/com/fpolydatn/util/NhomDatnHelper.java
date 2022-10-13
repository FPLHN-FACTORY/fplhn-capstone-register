package com.fpolydatn.util;

import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.NhomDatnRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author hungpv
 */
@Component
public class NhomDatnHelper {
    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Synchronized
    public String genMaNhomTuDong() {
        String text = "HN_";
        String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
        int count = Integer.parseInt(nhomDatnRepository.genMaNhomTuDong(dotDangKyHienTai));
        count++;
        return text + count;
    }
}
