package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.excel.CnNhomTheoDotExport;
import com.fpolydatn.core.chunhiem.excel.CnNhomTheoDotImport;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnImport;
import com.fpolydatn.core.chunhiem.model.response.CnNhomTheoDotExcel;
import com.fpolydatn.core.chunhiem.model.response.CnNhomTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnTruongNhomRandomResponse;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.NhomDatnHelper;
import com.fpolydatn.util.WriterFileCSV;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    private CnSinhVienRepository cnSinhVienRepository;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    private ConcurrentHashMap<String, GiangVienHuongDan> mapGiangVien;

    private ConcurrentHashMap<String, GiangVienHuongDan> mapGiangVienKeyId;

    private ConcurrentHashMap<String, NhomDatn> mapNhomDatn;

    private ConcurrentHashMap<String, NhomDatn> mapNhomDatnBefore;

    private List<CnNhomTheoDotResponse> nhomList;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    FormUtils formUtils = new FormUtils();

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
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoDotResponse> res = cnSinhVienTheoNhomRepository.searchSinhVien(request, pageable);
        System.out.println(res);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String maNhom, String maSinhVien) {
        String idNhom = nhomDatnRepository.getIdByMaNhom(maNhom, commonHelper.getDotDangKyHienTai());
        if (idNhom == null) {
            throw new RestApiException(Message.GROUP_NO_EXIST);
        }
        if (!checkDotDangKyHienTai((idNhom))) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        int numberOfGroup = nhomDatnRepository.getSoLuongThanhVienNhom(idNhom, commonHelper.getDotDangKyHienTai());
        if (numberOfGroup >= 7) {
            throw new RestApiException(Message.OVERLOADED_GROUP);
        }
        String captainId = nhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(captainId, commonHelper.getDotDangKyHienTai());
        Optional<SinhVien> captain = cnSinhVienTheoNhomRepository.findById(captainId);
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã thêm sinh viên '" + maSinhVien + "' vào nhóm '" + maNhom + "'",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LoggerObject loggerObject = writerFileCSV.
                    createLoggerObject("Chủ nhiệm bộ môn đã thêm sinh viên '" + maSinhVien + "' vào nhóm '" + maNhom + "'",
                            pathFile, "Thành công");
            loggerObject.setTenNhom(maNhom);
            logService.sendLogMessage(loggerObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom, maSinhVien, idMonDatn, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(String tenSinhVien, String coSoId) {
        return cnSinhVienTheoNhomRepository.getSinhVienByTenSinhVien(tenSinhVien, coSoId, commonHelper.getDotDangKyHienTai());
    }

    @Synchronized
    public List<NhomDatn> randomNhomDatn(List<SinhVien> listSinhVien, Integer number) {
        List<LoggerObject> loggerObjectList = new ArrayList<>();
        String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
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
            String maNhomAuto = array[0] + "_" + count++;
            nhomDatnCreate.setMaNhom(maNhomAuto);
            nhomDatnCreate.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
            nhomDatnCreate.setCoSoId(listSinhVien.get(0).getCoSoId());
            nhomDatnCreate.setDotDangKyId(listSinhVien.get(0).getDotDangKyId());
            nhomDatnCreate.setTruongNhomId(truongNhom.getId());
            listNhomDatnRadom.add(nhomDatnCreate);
            try {
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Chủ nhiệm bộ môn đã tạo nhóm '" + maNhomAuto,
                                fileSinhVienCsv, "Thành công");
                loggerObjectList.add(loggerObjectActive);

                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Chủ nhiệm bộ môn đã tạo nhóm '" + maNhomAuto,
                                pathFile, "Thành công");
                loggerObject.setTenNhom(maNhomAuto);
                loggerObjectList.add(loggerObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã thêm và phân cho sinh viên '" + truongNhomByMaNhom.getTenSinhVien() + "-" + truongNhomByMaNhom.getMaSinhVien() + " làm trưởng nhóm của nhóm " + nhomDatn.getMaNhom(),
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã thêm và phân cho sinh viên '" + truongNhomByMaNhom.getTenSinhVien() + "-" + truongNhomByMaNhom.getMaSinhVien() + " làm trưởng nhóm của nhóm " + nhomDatn.getMaNhom(),
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(nhomDatn.getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                try {
                    LoggerObject loggerObjectActive = writerFileCSV.
                            createLoggerObject("Chủ nhiệm bộ môn đã thêm sinh viên '" + sinhVien.getTenSinhVien() + "-" + sinhVien.getMaSinhVien() + " vào nhóm " + nhomDatn.getMaNhom(),
                                    fileSinhVienCsv, "Thành công");
                    loggerObjectList.add(loggerObjectActive);

                    LoggerObject loggerObject = writerFileCSV.
                            createLoggerObject("Chủ nhiệm bộ môn đã thêm sinh viên '" + sinhVien.getTenSinhVien() + "-" + sinhVien.getMaSinhVien() + " vào nhóm " + nhomDatn.getMaNhom(),
                                    pathFile, "Thành công");
                    loggerObject.setTenNhom(nhomDatn.getMaNhom());
                    loggerObjectList.add(loggerObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        loggerObjectList.forEach(el -> {
            logService.sendLogMessage(el);
        });
        cnSinhVienTheoNhomRepository.saveAll(listSinhVien);
        return listNhomDatn;
    }

    public Boolean randomNhomDatnByIdChuNhiem(String idChuNhiem, String coSoId, Integer number) {
        try {
            String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
            if (dotDangKyHienTai == null) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
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

    @Override
    public void CnExportSapGvhd(HttpServletResponse response, CnFindSinhVienTheoDotRequest request, String idChuNhiem) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=NhomTheoDot" + currentDateTime + "." + request.getTypeExcel();
            response.setHeader(headerKey, headerValue);
            CnNhomTheoDotExport cnNhomTheoDotExport = new CnNhomTheoDotExport();
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
            if (!checkDotDangKy(request.getIdDotDangKy())) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
            List<CnNhomTheoDotExcel> list = cnSinhVienRepository.findNhomDatnExport(request, idChuNhiem);
            cnNhomTheoDotExport.export(response, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CnNhomDatnImport importSapGvhd(MultipartFile file, String dotDangKyId, String coSoId, String idChuNhiem) throws IOException {
        CnNhomDatnImport response = new CnNhomDatnImport();
        List<LoggerObject> loggerObjectList = new ArrayList<>();
        String fileSinhVienCsv = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
        try {
            if (!checkDotDangKy(dotDangKyId)) {
                throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
            }
            response.setStatus(true);
            CnNhomTheoDotImport cnNhomTheoDotImport = new CnNhomTheoDotImport();
            ConcurrentHashMap<String, NhomDatn> listNhomDatnUpdate = new ConcurrentHashMap<>();
            nhomList = new ArrayList<>();
            nhomList = cnNhomTheoDotImport.importData(file);
            mapGiangVien = new ConcurrentHashMap<>();
            mapGiangVienKeyId = new ConcurrentHashMap<>();
            mapNhomDatn = new ConcurrentHashMap<>();
            mapNhomDatnBefore = new ConcurrentHashMap<>();
            addDataInMapGiangVien(mapGiangVien, mapGiangVienKeyId, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapNhomDatn(mapNhomDatn, mapNhomDatnBefore, dotDangKyId, coSoId, idChuNhiem);
            nhomList.parallelStream().forEach(nhomDatn -> {
                GiangVienHuongDan giangVien = null;
                if (!nhomDatn.getGvhd().isEmpty()) {
                    giangVien = mapGiangVien.get(nhomDatn.getGvhd());
                    if (giangVien == null) {
                        response.setStatus(false);
                        response.setMessage("Giảng viên " + nhomDatn.getGvhd() + " không tồn tại");
                        return;
                    }
                }
                if (giangVien != null) {
                    if (countSoNhomGiangVienDangHuongDan(giangVien.getTenTaiKhoan().toLowerCase())) {
                        response.setStatus(false);
                        response.setMessage("Giảng viên " + giangVien.getTenTaiKhoan() + " đang hướng dẫn số nhóm nhiều hơn đăng ký");
                        return;
                    }
                }
                NhomDatn nhomDatnUpdate = new NhomDatn();
                if (!nhomDatn.getMaNhom().isEmpty()) {
                    nhomDatnUpdate = mapNhomDatn.get(nhomDatn.getMaNhom());
                    if (giangVien != null) {
                        nhomDatnUpdate.setGiangVienId(giangVien.getId());
                    } else {
                        nhomDatnUpdate.setGiangVienId(null);
                    }
                    if (nhomDatn.getTenDeTai().equals("")) {
                        nhomDatnUpdate.setTenDeTai(null);
                    } else {
                        nhomDatnUpdate.setTenDeTai(nhomDatn.getTenDeTai());
                    }
                    listNhomDatnUpdate.put(nhomDatnUpdate.getId(), nhomDatnUpdate);
                }
            });
            for (Map.Entry<String, NhomDatn> item : listNhomDatnUpdate.entrySet()) {
                String maGiangVienMoi = item.getValue().getGiangVienId();
                String maGiangVienCu = mapNhomDatnBefore.get(item.getValue().getId()).getGiangVienId();
                if (maGiangVienCu == null && maGiangVienMoi != null) {
                    GiangVienHuongDan giangVienMoi = mapGiangVienKeyId.get(maGiangVienMoi);
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã phân cho giảng viên '" + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan() + "' hướng dẫn nhóm " + item.getValue().getMaNhom(),
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã phân cho giảng viên '" + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan() + "' hướng dẫn nhóm " + item.getValue().getMaNhom(),
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(item.getValue().getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (maGiangVienCu != null && maGiangVienMoi == null) {
                    GiangVienHuongDan giangVienCu = mapGiangVienKeyId.get(maGiangVienCu);
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã xóa giảng viên '" + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + "' ra khỏi nhóm " + item.getValue().getMaNhom(),
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã xóa giảng viên '" + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + "' ra khỏi nhóm " + item.getValue().getMaNhom(),
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(item.getValue().getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (maGiangVienCu != null && maGiangVienMoi != null && !maGiangVienCu.equals(maGiangVienMoi)) {
                    GiangVienHuongDan giangVienCu = mapGiangVienKeyId.get(maGiangVienCu);
                    GiangVienHuongDan giangVienMoi = mapGiangVienKeyId.get(maGiangVienMoi);
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã chuyển giảng viên của nhóm '" + item.getValue().getMaNhom() + "' từ " + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + " sang " + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan(),
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã chuyển giảng viên của nhóm '" + item.getValue().getMaNhom() + "' từ " + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + " sang " + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan(),
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(item.getValue().getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String tenDetaiMoi = item.getValue().getTenDeTai();
                String tenDetaiCu = mapNhomDatnBefore.get(item.getValue().getId()).getTenDeTai();
                if (tenDetaiCu != null && tenDetaiMoi != null && !tenDetaiCu.trim().equals(tenDetaiMoi.trim())) {
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã đổi tên đề tài của nhóm '" + item.getValue().getMaNhom() + "' từ '" + tenDetaiCu + "' sang '" + tenDetaiMoi + "'",
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã đổi tên đề tài của nhóm '" + item.getValue().getMaNhom() + "' từ '" + tenDetaiCu + "' sang '" + tenDetaiMoi + "'",
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(item.getValue().getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (tenDetaiCu == null && tenDetaiMoi != null) {
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã đặt đề tài của nhóm '" + item.getValue().getMaNhom() + "' là: " + tenDetaiMoi,
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã đặt đề tài của nhóm '" + item.getValue().getMaNhom() + "' là: " + tenDetaiMoi,
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(item.getValue().getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (tenDetaiCu != null && tenDetaiMoi == null) {
                    try {
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã xóa tên đề tài của nhóm '" + item.getValue().getMaNhom() + "'",
                                        fileSinhVienCsv, "Thành công");
                        loggerObjectList.add(loggerObjectActive);

                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Chủ nhiệm bộ môn đã xóa tên đề tài của nhóm '" + item.getValue().getMaNhom() + "'",
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(item.getValue().getMaNhom());
                        loggerObjectList.add(loggerObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (response.getStatus() == true) {
                cnNhomDatnRepository.saveAll(listNhomDatnUpdate.values());
                loggerObjectList.forEach(el -> {
                    logService.sendLogMessage(el);
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi hệ thống");
            return response;
        }
        return response;
    }

    public boolean countSoNhomGiangVienDangHuongDan(String tenTaiKhoan) {
        AtomicInteger count = new AtomicInteger(0);
        nhomList.parallelStream().forEach(nhomDatn -> {
            if (nhomDatn.getGvhd().equals(tenTaiKhoan)) {
                count.set(count.get() + 1);
            }
        });
        if (count.get() > mapGiangVien.get(tenTaiKhoan).getSoNhomHuongDanToiDa()) {
            return true;
        }
        return false;
    }

    public void addDataInMapGiangVien(ConcurrentHashMap<String, GiangVienHuongDan> mapAll, ConcurrentHashMap<String, GiangVienHuongDan> mapAllKeyId, String
            dotDangKyId, String coSoId, String idChuNhiem) {
        List<GiangVienHuongDan> giangVienHuongDanList = cnGiangVienHuongDanReponsitory.getAllGiangVienByIdCoSoAndByIdDotDangKy(dotDangKyId, coSoId, idChuNhiem);
        getALlPutMapGiangVien(mapAll, mapAllKeyId, giangVienHuongDanList);
    }

    public void getALlPutMapGiangVien
            (ConcurrentHashMap<String, GiangVienHuongDan> mapSimple, ConcurrentHashMap<String, GiangVienHuongDan> mapSimpleKeyId, List<GiangVienHuongDan> listGiangVien) {
        for (GiangVienHuongDan xx : listGiangVien) {
            mapSimple.put(xx.getTenTaiKhoan().toLowerCase(), xx);
            mapSimpleKeyId.put(xx.getId(), xx);
        }
    }

    public void addDataInMapNhomDatn(ConcurrentHashMap<String, NhomDatn> mapAll, ConcurrentHashMap<String, NhomDatn> mapAllBefore, String
            dotDangKyId, String coSoId, String idChuNhiem) {
        List<NhomDatn> nhomDatnList = cnNhomDatnRepository.getAllNhomDatnByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getALlPutMapNhomDatn(mapAll, mapAllBefore, nhomDatnList);
    }

    public void getALlPutMapNhomDatn
            (ConcurrentHashMap<String, NhomDatn> mapSimple, ConcurrentHashMap<String, NhomDatn> mapSimpleBefore, List<NhomDatn> listNhomDatn) {
        for (NhomDatn xx : listNhomDatn) {
            mapSimple.put(xx.getMaNhom(), xx);
            NhomDatn nhomDatnNew = formUtils.convertToObject(NhomDatn.class, xx);
            mapSimpleBefore.put(nhomDatnNew.getId(), nhomDatnNew);
        }
    }

    public Boolean checkDotDangKy(String dotDangKyId) {
        Optional<DotDangKy> dotDangKyFind = dotDangKyRepository.findById(dotDangKyId);
        if (!dotDangKyFind.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime >= dotDangKyFind.get().getNgayBatDau() && currentTime <= dotDangKyFind.get().getNgayKetThuc()) {
            return true;
        }
        return false;
    }

    public Boolean checkDotDangKyHienTai(String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return checkDotDangKy(nhomDatnFind.get().getDotDangKyId());
    }

}
