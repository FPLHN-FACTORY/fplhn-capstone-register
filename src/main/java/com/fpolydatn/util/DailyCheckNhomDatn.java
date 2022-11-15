package com.fpolydatn.util;

/**
 * @author thangncph26123
 */
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.repository.NhomDatnRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

import java.util.ArrayList;
import java.util.List;

@Component
public class DailyCheckNhomDatn {

    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    @Autowired
    @Qualifier(SinhVienRepository.NAME)
    private SinhVienRepository sinhVienRepository;

    @Scheduled(fixedDelay = 3600000)
    public void daiLyChecking() throws InterruptedException {
        List<String> listNhomKhongDuDieuKien = nhomDatnRepository.listNhomKhongDuDieuKien(convertDateToTimeStampSecond(),
                Constants.MUOI_HAI_TIENG);
        if (listNhomKhongDuDieuKien != null) {
            List<SinhVien> listSinhVien = new ArrayList<>();
            for (String xx : listNhomKhongDuDieuKien) {
                List<SinhVien> listSinhVienByNhomId = sinhVienRepository.getListSinhVienByNhomId(xx);
                listSinhVienByNhomId.parallelStream().forEach(sinhVien -> {
                    sinhVien.setMonDatnId(null);
                    sinhVien.setNhomId(null);
                    listSinhVien.add(sinhVien);
                });
            }
            sinhVienRepository.saveAll(listSinhVien);
            nhomDatnRepository.deleteAllById(listNhomKhongDuDieuKien);
        }
    }

}