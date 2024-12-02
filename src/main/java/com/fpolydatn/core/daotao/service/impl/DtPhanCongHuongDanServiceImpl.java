package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.core.daotao.repository.DtGiangVienHDRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.repository.DtPhanCongHuongDanRepository;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.entity.PhanCongHuongDanId;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author huynq
 */
@Service
@Validated
public class DtPhanCongHuongDanServiceImpl implements DtPhanCongHuongDanService {

    @Autowired
    private DtPhanCongHuongDanRepository phanCongHuongDanRepository;

//    @Autowired
//    private DtMonDatnRepository monDatnRepository;
//
//    @Autowired
//    private DtGiangVienHDRepository giangVienHDRepository;
//
//    @Autowired
//    private WriterFileCSV writerFileCSV;
//
//    @Autowired
//    private LogService logService;

    @Override
    public PageableObject<DtPhanCongHuongDanResponse> search(String id, DtFindPhanCongHuongDanRequest req, int page) {
        return new PageableObject<>(
                phanCongHuongDanRepository.getGiangVienHuongDan(id, req, PageRequest.of(page, req.getSize()))
        );
    }

    @Override
    public DtPhanCongHuongDanMonDatnResponse getMon(String id) {
        return phanCongHuongDanRepository.getMon(id);
    }

    @Override
    public PhanCongHuongDan findById(String idGV, String idMon) {
        PhanCongHuongDanId phanCongHuongDanId = new PhanCongHuongDanId();
        phanCongHuongDanId.setMonDatnId(idMon);
        phanCongHuongDanId.setGiangVienId(idGV);
        return phanCongHuongDanRepository.findById(phanCongHuongDanId)
                .orElseThrow(() -> new RestApiException(Message.PHAN_CONG_HUONG_DAN_NOT_EXIST));
    }

    @Override
    @Transactional
    public Boolean delete(String idGV, String idMon) {
        PhanCongHuongDan phanCongHuongDan = findById(idGV, idMon);
        if (Objects.isNull(phanCongHuongDan)) {
            throw new RestApiException(Message.PHAN_CONG_HUONG_DAN_NOT_EXIST);
        }

//        try {
//            String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) +
//                    writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) +
//                    writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_PHAN_CONG_HUONG_DAN);
//
//            Optional<MonDatn> monDatn = monDatnRepository.findById(idMon);
//            Optional<GiangVienHuongDan> giangVienHuongDan = giangVienHDRepository.findById(idGV);
//
//            LoggerObject loggerObjectActive = writerFileCSV.createLoggerObject(
//                    "Giảng viên: '" + giangVienHuongDan.get().getTenTaiKhoan() + "' đã bị xóa khỏi môn '" +
//                            monDatn.get().getTenMonDatn() + " - " + monDatn.get().getMaMon() + "'.",
//                    pathFile, "Thành công");
//
//            logService.sendLogMessage(loggerObjectActive);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }

        phanCongHuongDanRepository.delete(phanCongHuongDan);
        return true;
    }

    @Override
    public PageableObject<DtPhanCongHuongDanResponse> getPhanCongGiaoVien(String coSoId, String maMonId, final DtFindPhanCongHuongDanRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtPhanCongHuongDanResponse> res = phanCongHuongDanRepository.getPhanCongGiaoVien(coSoId, maMonId, req, pageable);
        return new PageableObject<>(res);
    }

    @Override
    @Transient
    @Transactional
    public List<PhanCongHuongDan> addAll(List<PhanCongHuongDan> list) {
        List<PhanCongHuongDan> phanCongHuongDans = phanCongHuongDanRepository.saveAll(list);

//    String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) +
//                        writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) +
//                        writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_PHAN_CONG_HUONG_DAN);
//
//    StringBuffer content = new StringBuffer();
//    phanCongHuongDans.parallelStream().forEach(el -> {
//        Optional<MonDatn> monDatn = monDatnRepository.findById(el.getMonDatnId());
//        Optional<GiangVienHuongDan> giangVienHuongDan = giangVienHDRepository.findById(el.getGiangVienId());
//        content.append("Giảng viên: '" + giangVienHuongDan.get().getTenTaiKhoan() + "' đã được phân công vào môn '" + monDatn.get().getTenMonDatn() + " - " + monDatn.get().getMaMon() + "'. ");
//    });
//
//    LoggerObject loggerObjectActive = writerFileCSV.createLoggerObject(content.toString(), pathFile, "Thành công");
//    logService.sendLogMessage(loggerObjectActive);

        return phanCongHuongDans;
    }

    @Override
    public PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatn(String coSoId, String id, DtFindPhanMonGiangVienHuongDanRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        return new PageableObject<>(phanCongHuongDanRepository.getMonDatn(coSoId, id, req, pageable));
    }

    @Override
    public DtGiangVienHDResponse getGiangVienHuongDan(String id, String coSoId) {
        return phanCongHuongDanRepository.getGiangVienHD(id, coSoId);
    }

    @Override
    public PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatnList(String coSoId, String giangVienHuongDanId, DtFindPhanMonGiangVienHuongDanRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        return new PageableObject<>(phanCongHuongDanRepository.getPhanCongMonDatn(coSoId, giangVienHuongDanId, req, pageable));
    }

}
