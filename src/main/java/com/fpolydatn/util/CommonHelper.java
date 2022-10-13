package com.fpolydatn.util;

import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

/**
 * @author thangncph26123
 */
@Component
public class CommonHelper {

    @Autowired
    private FpolyDatnSession session;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    @Qualifier(GiangVienHuongDanRepository.NAME)
    private GiangVienHuongDanRepository giangVienHuongDanRepository;

    public int getSoNhomGiangVienDangHuongDan(String id){
        String dotDangKyHienTai = getDotDangKyHienTai();
        return giangVienHuongDanRepository.getSoNhomGiangVienDangHuongDan(id, dotDangKyHienTai);
    }

    public String getDotDangKyHienTai() {
        String coSoId = session.getCoSoId();
        return dotDangKyRepository.findDotDangKyID(coSoId, convertDateToTimeStampSecond());
    }
}
