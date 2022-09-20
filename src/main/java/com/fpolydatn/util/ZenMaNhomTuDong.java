package com.fpolydatn.util;

import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author hungpv
 */
@Component
public class ZenMaNhomTuDong {
    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    public String zenMaNhomTuDong() {
        String text = "";
        int count = Integer.parseInt(nhomDatnRepository.zenMaNhomTuDong());
        count++;
        if (count < 10) {
            text = "HN000";
        } else if (count < 100) {
            text = "HN00";
        } else if (count < 1000) {
            text = "HN0";
        } else {
            text = "HN";
        }
        return text + count;
    }
}
