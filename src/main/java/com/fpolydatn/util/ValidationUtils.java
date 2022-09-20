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
        int soNhomHD;
        if(giangVienHuongDanRepository.getSoNhomGiangVienDangHuongDan(id) == null){
            soNhomHD = 0;
        }else{
            soNhomHD = Integer.parseInt(giangVienHuongDanRepository.getSoNhomGiangVienDangHuongDan(id));
        }
        return soNhomHD;
    }

}
