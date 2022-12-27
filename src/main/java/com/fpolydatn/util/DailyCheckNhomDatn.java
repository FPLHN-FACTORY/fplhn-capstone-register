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

    @Autowired
    private EmailSender emailSender;

    @Scheduled(fixedDelay = 3600000)
    public void daiLyChecking() throws InterruptedException {
        List<String> listNhomKhongDuDieuKien = nhomDatnRepository.listNhomKhongDuDieuKien(convertDateToTimeStampSecond(),
                Constants.MUOI_HAI_TIENG);
        if (listNhomKhongDuDieuKien != null) {
            List<SinhVien> listSinhVien = new ArrayList<>();
            for (String xx : listNhomKhongDuDieuKien) {
                List<SinhVien> listSinhVienByNhomId = sinhVienRepository.getListSinhVienByNhomId(xx);
                for (SinhVien yy : listSinhVienByNhomId){
                    yy.setMonDatnId(null);
                    yy.setNhomId(null);
                    listSinhVien.add(yy);
                }
            }
            Integer soSinhVien = listSinhVien.size();
            if (soSinhVien > 0) {
                String[] arrEmail = new String[soSinhVien];
                for (int i = 0; i < soSinhVien; i++) {
                    arrEmail[i] = listSinhVien.get(i).getEmail();
                }
                emailSender.sendEmail(arrEmail, "[FCR] Thông báo xóa nhóm không đủ điều kiện",
                        "Nhóm của bạn đã bị xóa",
                        new String[]{"Lý do: Nhóm của bạn không đủ số lượng tối thiểu 4 " +
                                "thành viên sau 12 tiếng kể từ khi được thành lập"});
            }
            sinhVienRepository.saveAll(listSinhVien);
            nhomDatnRepository.deleteAllById(listNhomKhongDuDieuKien);
        }
    }

}