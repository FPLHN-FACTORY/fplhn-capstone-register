package com.fpolydatn.core.common;

import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.EmailSender;
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

    @Scheduled(fixedDelayString = "${config.fixedDelayMillis}")
    public void daiLyChecking() throws InterruptedException {
        if (statusSchedule) {
            List<String> listNhomKhongDuDieuKien = nhomDatnRepository.listNhomKhongDuDieuKien(convertDateToTimeStampSecond(),
                    timestampResetNhomDatn);
            if (listNhomKhongDuDieuKien != null) {
                List<SinhVien> listSinhVien = new ArrayList<>();
                List<SinhVien> listSinhVienByNhomId = sinhVienRepository.getListSinhVienByNhomId(convertDateToTimeStampSecond(),
                        timestampResetNhomDatn);
                for (SinhVien yy : listSinhVienByNhomId) {
                    yy.setMonDatnId(null);
                    yy.setNhomId(null);
                    listSinhVien.add(yy);
                }
                sinhVienRepository.saveAll(listSinhVien);
                nhomDatnRepository.deleteAllById(listNhomKhongDuDieuKien);
                Integer soSinhVien = listSinhVien.size();
                if (soSinhVien > 0) {
                    List<String> listEmail = new ArrayList<>();
                    for (SinhVien sinhVien : listSinhVien) {
                        listEmail.add(sinhVien.getEmail());
                        if (listEmail.size() == 450) {
                            sendEmail(listEmail);
                            listEmail.clear();
                        }
                    }
                    if (!listEmail.isEmpty()) {
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
                new String[]{"Lý do: Nhóm của bạn không đủ số lượng thành viên trong thời gian quy định kể từ khi thành lập"});
    }

}