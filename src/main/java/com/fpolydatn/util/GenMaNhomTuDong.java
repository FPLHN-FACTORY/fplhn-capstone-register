package com.fpolydatn.util;

import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.NhomDatnRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

/**
 * @author hungpv
 */
@Component
public class GenMaNhomTuDong {
    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Synchronized
    public String genMaNhomTuDong() {
        String text = "HN_";
        int count = Integer.parseInt(nhomDatnRepository.genMaNhomTuDong(dotDangKyRepository.findDotDangKyID(convertDateToTimeStampSecond())));
        count++;
        return text + count;
    }
}
