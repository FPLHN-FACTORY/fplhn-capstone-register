package com.fpolydatn.util;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;

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
        return giangVienHuongDanRepository.getSoNhomGiangVienDangHuongDan(id);
    }

    public String getDotDangKyHienTai() {
        String coSoId = session.getCoSoId();
        return dotDangKyRepository.findDotDangKyID(coSoId, convertDateToTimeStampSecond());
    }

    public Boolean isDotDangKyDangDienRa(String dotDangKyId) {
        Optional<DotDangKy> dotDangKyFind = dotDangKyRepository.findById(dotDangKyId);
        if (!dotDangKyFind.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime >= dotDangKyFind.get().getNgayBatDau() && currentTime <= dotDangKyFind.get().getNgayKetThuc()) {
            return false;
        }
        return true;
    }
}
