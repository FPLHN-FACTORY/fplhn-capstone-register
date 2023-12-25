package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.excel.CnNhomTheoDotExport;
import com.fpolydatn.core.chunhiem.excel.CnNhomTheoDotImport;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnLoggerResponse;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnImport;
import com.fpolydatn.core.chunhiem.model.response.CnNhomTheoDotExcel;
import com.fpolydatn.core.chunhiem.model.response.CnNhomTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnTruongNhomRandomResponse;
import com.fpolydatn.core.chunhiem.repository.CnChuyenNganhRepository;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.RabbitLogger;
import com.fpolydatn.core.common.log.LogObject;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.core.common.CommonHelper;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.core.common.MocThoiGianHelper;
import com.fpolydatn.core.common.NhomDatnHelper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
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
@Validated
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
    private MocThoiGianHelper mocThoiGianHelper;

    @Autowired
    private CnChuyenNganhRepository cnChuyenNganhRepository;

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

    private ConcurrentHashMap<String, SinhVien> mapTruongNhom;

    private ConcurrentHashMap<String, ChuyenNganh> mapChuyenNganh;

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private RabbitLogger rabbitLogger;

    FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<CnSinhVienTheoDotResponse> searchByIdDotDangKy(final CnFindSinhVienTheoDotRequest request) {
        String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
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
    public boolean addSinhVienTheoNhom(String maNhom, String maSinhVien, String idSinhVien) {
        String idNhom = nhomDatnRepository.getIdByMaNhom(maNhom, commonHelper.getIdDotDangKyHienTai());
        if (idNhom == null) {
            throw new RestApiException(Message.GROUP_NO_EXIST);
        }
        if (!checkDotDangKyHienTai((idNhom))) {
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        int numberOfGroup = nhomDatnRepository.getSoLuongThanhVienNhom(idNhom, commonHelper.getIdDotDangKyHienTai());
        if (numberOfGroup >= 7) {
            throw new RestApiException(Message.OVERLOADED_GROUP);
        }
        SinhVien sinhVienFind = cnSinhVienTheoNhomRepository.findById(idSinhVien).get();
        String captainId = nhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getIdDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(captainId, commonHelper.getIdDotDangKyHienTai());
        Optional<SinhVien> captain = cnSinhVienTheoNhomRepository.findById(captainId);
        String tenChuyenNganh = chuyenNganhRepository.getTenChuyenNganhById(captain.get().getChuyenNganhId());
        if(!sinhVienFind.getChuyenNganhId().equals(captain.get().getChuyenNganhId())) {
            throw new RestApiException(Message.CHUYEN_NGANH_CUA_SINH_VIEN_KHONG_PHU_HOP_VOI_NHOM);
        }
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(writerFileCSV.tenDotDangKyHienTai());
            LogObject logObjectActive = writerFileCSV.
                    createLoggerObject("Đã thêm sinh viên '" + sinhVienFind.getTenSinhVien() + "-" + sinhVienFind.getMaSinhVien() + "' vào nhóm '" + maNhom + "'",
                            pathFileLogActive, "Thành công");
            logObjectActive.setIdChuyenNganh(captain.get().getChuyenNganhId());
            logObjectActive.setTenChuyenNganh(tenChuyenNganh);
            rabbitLogger.sendLogMessage(logObjectActive);

            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            LogObject logObject = writerFileCSV.
                    createLoggerObject("Đã thêm sinh viên '" + sinhVienFind.getTenSinhVien() + "-" + sinhVienFind.getMaSinhVien() + "' vào nhóm '" + maNhom + "'",
                            pathFile, "Thành công");
            logObject.setTenNhom(maNhom);
            logObject.setIdChuyenNganh(captain.get().getChuyenNganhId());
            logObject.setTenChuyenNganh(tenChuyenNganh);
            rabbitLogger.sendLogMessage(logObject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom, maSinhVien, idMonDatn, commonHelper.getIdDotDangKyHienTai());
        return true;
    }

    @Override
    public List<CnSinhVienTheoDotResponse> getSinhVienByTenSinhVien(String tenSinhVien, String coSoId) {
        return cnSinhVienTheoNhomRepository.getSinhVienByTenSinhVien(tenSinhVien, coSoId, commonHelper.getIdDotDangKyHienTai());
    }

    @Synchronized
    public List<NhomDatn> randomNhomDatn(List<SinhVien> listSinhVien, Integer number) {
        List<LogObject> logObjectList = new ArrayList<>();
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
        List<CnLoggerResponse> listLogger = new ArrayList<>();
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
            // thêm vào logger
            CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
            cnLoggerResponse.setMaNhom(maNhomAuto);
            cnLoggerResponse.setValue("Đã tạo nhóm '" + maNhomAuto + "'.");
            listLogger.add(cnLoggerResponse);

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
                    CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                    cnLoggerResponse.setMaNhom(nhomDatn.getMaNhom());
                    cnLoggerResponse.setValue(" Đã thêm và phân cho sinh viên '" + truongNhomByMaNhom.getTenSinhVien() + "-" + truongNhomByMaNhom.getMaSinhVien() + " làm trưởng nhóm.");
                    listLogger.add(cnLoggerResponse);
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

                CnLoggerResponse cnLoggerResponse = new CnLoggerResponse();
                cnLoggerResponse.setMaNhom(nhomDatn.getMaNhom());
                cnLoggerResponse.setValue(" Đã thêm sinh viên '" + sinhVien.getTenSinhVien() + "-" + sinhVien.getMaSinhVien() + " vào nhóm.");
                listLogger.add(cnLoggerResponse);
            }
        }
        ChuyenNganh chuyenNganhFind = new ChuyenNganh();
        if (listSinhVien != null && listSinhVien.size() > 0) {
            chuyenNganhFind = cnChuyenNganhRepository.findById(listSinhVien.get(0).getChuyenNganhId()).get();
        }
        for (NhomDatn nhomDatn : listNhomDatn) {
            StringBuffer stringBuffer = new StringBuffer();
            for (CnLoggerResponse logger : listLogger) {
                if (nhomDatn.getMaNhom().equals(logger.getMaNhom())) {
                    stringBuffer.append(logger.getValue());
                }
            }
            try {
                LogObject logObjectActive = writerFileCSV.
                        createLoggerObject(stringBuffer.toString(),
                                fileSinhVienCsv, "Thành công");
                logObjectActive.setIdChuyenNganh(chuyenNganhFind.getId());
                logObjectActive.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                logObjectList.add(logObjectActive);

                LogObject logObject = writerFileCSV.
                        createLoggerObject(stringBuffer.toString(),
                                pathFile, "Thành công");
                logObject.setTenNhom(nhomDatn.getMaNhom());
                logObject.setIdChuyenNganh(chuyenNganhFind.getId());
                logObject.setTenChuyenNganh(chuyenNganhFind.getTenChuyenNganh());
                logObjectList.add(logObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logObjectList.forEach(el -> {
            rabbitLogger.sendLogMessage(el);
        });
        cnSinhVienTheoNhomRepository.saveAll(listSinhVien);
        return listNhomDatn;
    }

    public Boolean randomNhomDatnByIdChuNhiem(String idChuNhiem, String coSoId, Integer number) {
        try {
            String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
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
            String headerValue = "attachment; filename=NhomTheoDot" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            CnNhomTheoDotExport cnNhomTheoDotExport = new CnNhomTheoDotExport();
            String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
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
        List<LogObject> logObjectList = new ArrayList<>();
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
            mapTruongNhom = new ConcurrentHashMap<>();
            mapChuyenNganh = new ConcurrentHashMap<>();
            addDataInMapGiangVien(mapGiangVien, mapGiangVienKeyId, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapNhomDatn(mapNhomDatn, mapNhomDatnBefore, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapTruongNhomCu(mapTruongNhom, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapChuyenNganh(mapChuyenNganh, idChuNhiem, coSoId);
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
            List<CnLoggerResponse> loggerResponseList = new ArrayList<>();
            for (Map.Entry<String, NhomDatn> item : listNhomDatnUpdate.entrySet()) {
                String maGiangVienMoi = item.getValue().getGiangVienId();
                String maGiangVienCu = mapNhomDatnBefore.get(item.getValue().getId()).getGiangVienId();
                if (maGiangVienCu == null && maGiangVienMoi != null) {
                    GiangVienHuongDan giangVienMoi = mapGiangVienKeyId.get(maGiangVienMoi);
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(item.getValue().getMaNhom());
                    cnLoggerResponseNew.setValue(" Đã phân cho giảng viên '" + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan() + "' hướng dẫn nhóm " + item.getValue().getMaNhom() + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                }
                if (maGiangVienCu != null && maGiangVienMoi == null) {
                    GiangVienHuongDan giangVienCu = mapGiangVienKeyId.get(maGiangVienCu);
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(item.getValue().getMaNhom());
                    cnLoggerResponseNew.setValue(" Đã xóa giảng viên '" + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + "' ra khỏi nhóm " + item.getValue().getMaNhom() + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                }
                if (maGiangVienCu != null && maGiangVienMoi != null && !maGiangVienCu.equals(maGiangVienMoi)) {
                    GiangVienHuongDan giangVienCu = mapGiangVienKeyId.get(maGiangVienCu);
                    GiangVienHuongDan giangVienMoi = mapGiangVienKeyId.get(maGiangVienMoi);
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(item.getValue().getMaNhom());
                    cnLoggerResponseNew.setValue(" Đã chuyển giảng viên của nhóm '" + item.getValue().getMaNhom() + "' từ " + giangVienCu.getTenGvhd() + "-" + giangVienCu.getTenTaiKhoan() + " sang " + giangVienMoi.getTenGvhd() + "-" + giangVienMoi.getTenTaiKhoan() + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                }
                String tenDetaiMoi = item.getValue().getTenDeTai();
                String tenDetaiCu = mapNhomDatnBefore.get(item.getValue().getId()).getTenDeTai();
                if (tenDetaiCu != null && tenDetaiMoi != null && !tenDetaiCu.trim().equals(tenDetaiMoi.trim())) {
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(item.getValue().getMaNhom());
                    cnLoggerResponseNew.setValue(" Đã đổi tên đề tài của nhóm '" + item.getValue().getMaNhom() + "' từ '" + tenDetaiCu + "' sang '" + tenDetaiMoi + "'" + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                }
                if (tenDetaiCu == null && tenDetaiMoi != null) {
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(item.getValue().getMaNhom());
                    cnLoggerResponseNew.setValue(" Đã đặt đề tài của nhóm '" + item.getValue().getMaNhom() + "' là: " + tenDetaiMoi + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                }
                if (tenDetaiCu != null && tenDetaiMoi == null) {
                    CnLoggerResponse cnLoggerResponseNew = new CnLoggerResponse();
                    cnLoggerResponseNew.setMaNhom(item.getValue().getMaNhom());
                    cnLoggerResponseNew.setValue(" Đã xóa tên đề tài của nhóm '" + item.getValue().getMaNhom() + "'" + ".");
                    loggerResponseList.add(cnLoggerResponseNew);
                }
            }
            if (response.getStatus() == true) {
                cnNhomDatnRepository.saveAll(listNhomDatnUpdate.values());
                for (Map.Entry<String, NhomDatn> nhomDatn : listNhomDatnUpdate.entrySet()) {
                    boolean shouldCreateStringBuffer = false;
                    StringBuffer stringBuffer = new StringBuffer();
                    SinhVien sinhVien = mapTruongNhom.get(nhomDatn.getValue().getTruongNhomId());
                    ChuyenNganh chuyenNganh = mapChuyenNganh.get(sinhVien.getChuyenNganhId());
                    stringBuffer.append("Nhóm " + nhomDatn.getValue().getMaNhom() + ":");

                    for (CnLoggerResponse logger : loggerResponseList) {
                        if (nhomDatn.getValue().getMaNhom().equals(logger.getMaNhom())) {
                            shouldCreateStringBuffer = true;
                            stringBuffer.append(logger.getValue());
                        }
                    }

                    if (shouldCreateStringBuffer) {
                        try {
                            LogObject logObjectActive = writerFileCSV.
                                    createLoggerObject(stringBuffer.toString(),
                                            fileSinhVienCsv, "Thành công");
                            logObjectActive.setIdChuyenNganh(chuyenNganh.getId());
                            logObjectActive.setTenChuyenNganh(chuyenNganh.getTenChuyenNganh());
                            logObjectList.add(logObjectActive);

                            LogObject logObject = writerFileCSV.
                                    createLoggerObject(stringBuffer.toString(),
                                            pathFile, "Thành công");
                            logObject.setTenNhom(nhomDatn.getValue().getMaNhom());
                            logObject.setIdChuyenNganh(chuyenNganh.getId());
                            logObject.setTenChuyenNganh(chuyenNganh.getTenChuyenNganh());
                            logObjectList.add(logObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                logObjectList.forEach(el -> {
                    rabbitLogger.sendLogMessage(el);
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

    public void addDataInMapTruongNhomCu(ConcurrentHashMap<String, SinhVien> mapAll, String dotDangKyId, String
            coSoId, String idChuNhiem) {
        List<SinhVien> truongNhomCuList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getTruongNhomCuPutMapSinhVien(mapAll, truongNhomCuList);
    }

    public void getTruongNhomCuPutMapSinhVien
            (ConcurrentHashMap<String, SinhVien> mapSimple, List<SinhVien> listTruongNhom) {
        for (SinhVien xx : listTruongNhom) {
            mapSimple.put(xx.getId(), xx);
        }
    }

    public void addDataInMapChuyenNganh(ConcurrentHashMap<String, ChuyenNganh> mapAll, String idChuNhiem, String coSoId) {
        List<ChuyenNganh> chuyenNganhList = cnChuyenNganhRepository.getAllChuyenNganhEntityByIdChuNhiem(coSoId, idChuNhiem);
        getAllPutMapChuyenNganh(mapAll, chuyenNganhList);
    }

    public void getAllPutMapChuyenNganh
            (ConcurrentHashMap<String, ChuyenNganh> mapSimple, List<ChuyenNganh> chuyenNganhList) {
        for (ChuyenNganh chuyenNganh : chuyenNganhList) {
            mapSimple.put(chuyenNganh.getId(), chuyenNganh);
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
