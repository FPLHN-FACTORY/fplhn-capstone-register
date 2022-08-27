package com.fpolydatn.infrastructure.validation;

import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nguyenvv4
 */
@Service
public class ValidationUtils {

    @Autowired
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    private GiangVienHuongDanRepository giangVienHuongDanRepository;

    public Boolean isMaximumGroup(String giangVienId) {
        int maximum = giangVienHuongDanRepository.findById(giangVienId).get().getSoNhomHuongDanToiDa();
        int count = nhomDatnRepository.countByGiangVienId(giangVienId);
        if (count >= maximum) {
            return false;
        }
        return true;
    }
}
