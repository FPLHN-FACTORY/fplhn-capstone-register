package com.fpolydatn.util;

import com.fpolydatn.repository.NhomDatnRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author hungpv
 */
@Component
public class GenMaNhomTuDong {
    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Synchronized
    public String genMaNhomTuDong() {
        String text = "HN_";
        int count = Integer.parseInt(nhomDatnRepository.genMaNhomTuDong());
        count++;
        return text + count;
    }
}
