package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnTruongNhomRandomResponse;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.NhomDatnHelper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CnSinhVienTheoDotServiceImpl implements CnSinhVienTheoDotService {

    @Autowired
    private CnSinhVienRepository cnSinhVienTheoNhomRepository;

    @Autowired
    private CnNhomDatnRepository nhomDatnRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private NhomDatnHelper nhomDatnHelper;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Override
    public PageableObject<CnSinhVienTheoDotResponse> searchByIdDotDangKy(final CnFindSinhVienTheoDotRequest request) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (request.getIdDotDangKy() == null) {
            if (dotDangKyId != null) {
                request.setIdDotDangKy(dotDangKyId);
            } else {
                request.setIdDotDangKy("");
            }
        } else if (request.getIdDotDangKy().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                request.setIdDotDangKy(dotDangKyId);
            } else {
                request.setIdDotDangKy("");
            }
        }
        System.out.println(request.getIdDotDangKy() + " aaaaaaaaaaaaaaa");
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoDotResponse> res = cnSinhVienTheoNhomRepository.searchSinhVien(request, pageable);
        System.out.println(res);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String maNhom, String maSinhVien) {
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.HET_HAN_QUYEN_CHU_NHIEM_BO_MON);
        }
        String idNhom = nhomDatnRepository.getIdByMaNhom(maNhom, commonHelper.getDotDangKyHienTai());
        if (idNhom == null) {
            throw new RestApiException(Message.GROUP_NO_EXIST);
        }
        int numberOfGroup = nhomDatnRepository.getSoLuongThanhVienNhom(idNhom, commonHelper.getDotDangKyHienTai());
        if (numberOfGroup >= 7) {
            throw new RestApiException(Message.OVERLOADED_GROUP);
        }
        String captainId = nhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(captainId, commonHelper.getDotDangKyHienTai());
        Optional<SinhVien> captain = cnSinhVienTheoNhomRepository.findById(captainId);
        cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom, maSinhVien, idMonDatn, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(String tenSinhVien, String coSoId) {
        return cnSinhVienTheoNhomRepository.getSinhVienByTenSinhVien(tenSinhVien, coSoId, commonHelper.getDotDangKyHienTai());
    }

    @Synchronized
    public List<NhomDatn> randomNhomDatn(List<SinhVien> listSinhVien, Integer number) {
        Integer sizeListSinhVien = listSinhVien.size();
        Integer soNhomRandom = (int) sizeListSinhVien / number;
        if (soNhomRandom < 1) {
//            throw new RestApiException(Message.KHONG_DU_SINH_VIEN_DE_TAO_NHOM);
            return null;
        }
        List<NhomDatn> listNhomDatnRadom = new ArrayList<>();
        List<CnTruongNhomRandomResponse> listTruongNhomRandom = new ArrayList<>();
        List<Integer> listIndexTruongNhom = new ArrayList<>();

        String maNhom = nhomDatnHelper.genMaNhomTuDong();
        if (maNhom == null) {
            throw new RestApiException(Message.TAO_NHOM_THAT_BAI);
        }
        String array[] = maNhom.split("_");
        Long count = Long.parseLong(array[1]);

        Random random = new Random();
        List<Integer> availableIndexesTruongNhom = new ArrayList<>();

        for (int i = 0; i < sizeListSinhVien; i++) {
            availableIndexesTruongNhom.add(i);
        }

        // Tạo nhóm và chọn trưởng nhóm ngẫu nhiên, sau đó lưu lại thông tin của nhóm với trưởng nhóm
        for (int i = 0; i < soNhomRandom; i++) {
            // random ngẫu nhiên index trưởng nhóm
            if (availableIndexesTruongNhom.isEmpty()) {
                break;
            }
            int randomIndex = random.nextInt(availableIndexesTruongNhom.size());
            int actualIndex = availableIndexesTruongNhom.get(randomIndex);

            availableIndexesTruongNhom.remove(randomIndex);

            SinhVien truongNhom = listSinhVien.get(actualIndex);

            // tạo nhóm và set trưởng nhóm
            NhomDatn nhomDatnCreate = new NhomDatn();
            nhomDatnCreate.setMaNhom(array[0] + "_" + count++);
            nhomDatnCreate.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
            nhomDatnCreate.setCoSoId(listSinhVien.get(0).getCoSoId());
            nhomDatnCreate.setDotDangKyId(listSinhVien.get(0).getDotDangKyId());
            nhomDatnCreate.setTruongNhomId(truongNhom.getId());
            listNhomDatnRadom.add(nhomDatnCreate);

            // lưu lại thông tin trưởng nhóm đi kèm với nhóm
            CnTruongNhomRandomResponse cnTruongNhomRandomResponse = new CnTruongNhomRandomResponse();
            cnTruongNhomRandomResponse.setMaNhom(nhomDatnCreate.getMaNhom());
            cnTruongNhomRandomResponse.setIndex(actualIndex);
            listTruongNhomRandom.add(cnTruongNhomRandomResponse);
            listIndexTruongNhom.add(actualIndex);
        }
        List<NhomDatn> listNhomDatn = cnNhomDatnRepository.saveAll(listNhomDatnRadom);

        // set nhóm id cho trưởng nhóm dựa vào thông tin đã lưu ở trên
        for (NhomDatn nhomDatn : listNhomDatn) {
            for (CnTruongNhomRandomResponse cnTruongNhomRandomResponse : listTruongNhomRandom) {
                if (nhomDatn.getMaNhom().equals(cnTruongNhomRandomResponse.getMaNhom())) {
                    SinhVien truongNhomByMaNhom = listSinhVien.get(cnTruongNhomRandomResponse.getIndex());
                    truongNhomByMaNhom.setNhomId(nhomDatn.getId());
                    truongNhomByMaNhom.setMonDatnId(truongNhomByMaNhom.getMonChuongTrinhId());
                }
            }
        }

        // lấy ra index ko nằm trong listIndexTruongNhom
        List<Integer> availableIndexesSinhVien = new ArrayList<>();
        for (int i = 0; i < sizeListSinhVien; i++) {
            if (!listIndexTruongNhom.contains(i)) {
                availableIndexesSinhVien.add(i);
            }
        }

        // random sắp sinh viên vào nhóm và đủ number - 1 thành viên (vì nhóm đã có 1 thành viên là trưởng nhóm)
        for (NhomDatn nhomDatn : listNhomDatn) {
            for (int i = 0; i < number - 1; i++) {
                if (availableIndexesSinhVien.isEmpty()) {
                    break;
                }
                // ngẫu nhiên index trong availableIndexes
                int randomIndex = random.nextInt(availableIndexesSinhVien.size());
                int actualIndex = availableIndexesSinhVien.get(randomIndex);
                SinhVien sinhVien = listSinhVien.get(actualIndex);
                sinhVien.setNhomId(nhomDatn.getId());
                sinhVien.setMonDatnId(sinhVien.getMonChuongTrinhId());
                availableIndexesSinhVien.remove(randomIndex);
            }
        }

        cnSinhVienTheoNhomRepository.saveAll(listSinhVien);
        return listNhomDatn;
    }

    public Boolean randomNhomDatnByIdChuNhiem(String idChuNhiem, String coSoId, Integer number) {
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.HET_HAN_QUYEN_CHU_NHIEM_BO_MON);
        }
        try {
            String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
            List<String> monDatnByIdChuNhiem = cnSinhVienTheoNhomRepository.getAllMonDatnByIdChuNhiem(idChuNhiem, coSoId);

            for (String monDatnId : monDatnByIdChuNhiem) {
                List<SinhVien> listSinhVien = cnSinhVienTheoNhomRepository.getAllSinhVienChuaCoNhomByMonChuongTrinhId(monDatnId, coSoId, dotDangKyHienTai);
                randomNhomDatn(listSinhVien, number);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            throw new RestApiException("Lỗi hệ thống");
        }
        return true;
    }

}
