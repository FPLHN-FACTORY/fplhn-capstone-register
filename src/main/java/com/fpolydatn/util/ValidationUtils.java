package com.fpolydatn.util;

import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author thangncph26123
 */
@Component
public class ValidationUtils {

    @Autowired
    @Qualifier(GiangVienHuongDanRepository.NAME)
    private GiangVienHuongDanRepository giangVienHuongDanRepository;

    public int soNhomGiangVienDangHuongDan(String id){
        return giangVienHuongDanRepository.getSoNhomGiangVienDangHuongDan(id);
    }

}
