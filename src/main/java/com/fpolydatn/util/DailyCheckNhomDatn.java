package com.fpolydatn.util;

import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.repository.NhomDatnRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

/**
 * @author thangncph26123
 */

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

    @Value("${config.statusSchedule}")
    private Boolean statusSchedule;

    @Value("${config.timestampResetNhomDatn}")
    private Long timestampResetNhomDatn;

    @Value("${config.hoursResetNhomDatn}")
    private Long hoursResetNhomDatn;

    @Scheduled(fixedDelayString = "${config.fixedDelayMillis}")
    public void daiLyChecking() throws InterruptedException {
        if (statusSchedule) {
            List<String> listNhomKhongDuDieuKien = nhomDatnRepository.listNhomKhongDuDieuKien(convertDateToTimeStampSecond(),
                    timestampResetNhomDatn);
            if (listNhomKhongDuDieuKien != null) {
                List<SinhVien> listSinhVien = new ArrayList<>();
                for (String xx : listNhomKhongDuDieuKien) {
                    List<SinhVien> listSinhVienByNhomId = sinhVienRepository.getListSinhVienByNhomId(xx);
                    for (SinhVien yy : listSinhVienByNhomId) {
                        yy.setMonDatnId(null);
                        yy.setNhomId(null);
                        listSinhVien.add(yy);
                    }
                }
                sinhVienRepository.saveAll(listSinhVien);
                nhomDatnRepository.deleteAllById(listNhomKhongDuDieuKien);
                Integer soSinhVien = listSinhVien.size();
                if (soSinhVien > 0) {
                    List<String> listEmail = new ArrayList<>();
                    for (int i = 0; i < soSinhVien; i++) {
                        listEmail.add(listSinhVien.get(i).getEmail());
                        if (listEmail.size() == 450) {
                            sendEmail(listEmail);
                            listEmail.clear();
                        }
                    }
                    if (listEmail.size() > 0) {
                        sendEmail(listEmail);
                    }
                }
            }
        }
    }

    public void sendEmail(List<String> listEmail) {
        String[] emailArray = new String[listEmail.size()];
        for (int j = 0; j < listEmail.size(); j++) {
            emailArray[j] = listEmail.get(j);
        }
        emailSender.sendEmail(emailArray, "[FCR] Thông báo xóa nhóm không đủ điều kiện",
                "Nhóm của bạn đã bị xóa",
                new String[]{"Lý do: Nhóm của bạn không đủ số lượng tối thiểu 4 " +
                        "thành viên sau " + hoursResetNhomDatn + " tiếng kể từ khi được thành lập"});
    }

}