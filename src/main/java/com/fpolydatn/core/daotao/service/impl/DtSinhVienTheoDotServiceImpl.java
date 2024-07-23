package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotExport;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotImport;
import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.DtLoaiSinhVienKhongDatRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.core.daotao.model.response.DtMonDatnSearchResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LogService;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.util.WriterFileCSV;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author thepvph20110
 */
@Service
@Validated
public class DtSinhVienTheoDotServiceImpl implements DtSinhVienTheoDotService {

    @Autowired
    private DtSinhVienTheoDotRepository sinhVienRepository;

    @Autowired
    private DtDotDangKyRepository dotDangKyRepository;

    @Autowired
    private DtNhomDatnTheoDotRepository nhomDatnTheoDotRepository;

    @Autowired
    private DtChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private DtMonDatnRepository monDatnRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private LogService logService;

    private ConcurrentHashMap<String, String> map;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request) {
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
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.searchStudent(request, pageable);
        return new PageableObject<>(res);
    }

    public boolean checkTypeFile(MultipartFile file) {
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        String excel = "xls";
        if (!extension.equals(excel)) {
            return false;
        }
        return true;
    }

    @Override
    public void DTSinhVienExport(HttpServletResponse response, String typeExcel, final FindDtSinhVienTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=QLSinhVienFpoly" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
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
        response.setHeader("Content-Disposition", headerValue);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
        List<DtSinhVienTheoDotResponse> list = sinhVienRepository.exportExcel(request);
        DtSinhVienTheoDotExport dtSinhVienExport = new DtSinhVienTheoDotExport();
        dtSinhVienExport.employeeDetailReport(response, list);
    }

    @Override
    public void MauImportExcel(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws Exception {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MauQlSinhVien" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        List<DtSinhVienTheoDotResponse> listSv = new ArrayList<>();
        DtSinhVienTheoDotExport dtSinhVienExport = new DtSinhVienTheoDotExport(listSv);
        dtSinhVienExport.headerLineMau(response);
    }

    @Override
    public DtLoaiSinhVienResponse DtSinhVienImport(MultipartFile file, String coSoId, String dotDangKyId) throws IOException {
        DtLoaiSinhVienResponse response = new DtLoaiSinhVienResponse();
        if (!dotDangKyRepository.existsById(dotDangKyId)) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        try {
            response.setStatus(true);
            DtSinhVienTheoDotImport sinhVienTheoDotImport = new DtSinhVienTheoDotImport();
            List<DtCreateSinhVienTheoDotRequest> sinhVienList = sinhVienTheoDotImport.importData(file);
            if (sinhVienList.size() <= 0) {
                response.setStatus(false);
                response.setMessage("Import file không thành công");
            }
            ConcurrentMap<String, SinhVien> listSinhVienEntity = new ConcurrentHashMap<>();
            StringBuilder logMessage = new StringBuilder();
            map = new ConcurrentHashMap<>();
            addDataInMap(map, coSoId, dotDangKyId);
            sinhVienList.parallelStream().forEach(sinhVien -> {
                SinhVien sinhVienEntity = new SinhVien();
                if (sinhVien.getTenSinhVien().isEmpty() || sinhVien.getMaSinhVien().isEmpty() ||
                        sinhVien.getEmail().isEmpty() ||
                        sinhVien.getChuyenNganh().isEmpty()) {
                    response.setStatus(false);
                    response.setMessage("Không để trống (Mã, tên, email, tên chuyên ngành,)");
                    return;
                }

                if (map.containsKey(sinhVien.getMaSinhVien().toLowerCase())) {
                    response.setStatus(false);
                    response.setMessage("Mã sinh viên " + sinhVien.getMaSinhVien() + " đã tồn tại");
                    return;
                }

                if (map.containsKey(sinhVien.getChuyenNganh().toLowerCase())) {
                    sinhVienEntity.setChuyenNganhId(map.get(sinhVien.getChuyenNganh().toLowerCase()));
                } else {
                    response.setStatus(false);
                    response.setMessage("Tên chuyên ngành \"" + sinhVien.getChuyenNganh() + "\" không tồn tại");
                    return;
                }

                if (map.containsKey(sinhVien.getMonDATN().toLowerCase())) {
                    sinhVienEntity.setMonChuongTrinhId(map.get(sinhVien.getMonDATN().toLowerCase()));
                } else {
                    response.setStatus(false);
                    response.setMessage("Mã môn chương trình \"" + sinhVien.getMonDATN() + "\" không tồn tại");
                    return;
                }

                if (map.containsKey(sinhVien.getEmail().toLowerCase())) {
                    response.setStatus(false);
                    response.setMessage("Email " + sinhVien.getEmail() + " đã tồn tại");
                    return;
                }

                if (sinhVien.getSoDienThoai().length() > 11) {
                    response.setStatus(false);
                    response.setMessage("Số điện thoại " + sinhVien.getEmail() + " không được vượt quá 11 kí tự");
                    return;
                }

                sinhVienEntity.setTenSinhVien(sinhVien.getTenSinhVien());
                sinhVienEntity.setMaSinhVien(sinhVien.getMaSinhVien());
                if (sinhVien.getSoDienThoai() == null) {
                    sinhVienEntity.setSoDienThoai("");
                } else {
                    sinhVienEntity.setSoDienThoai(sinhVien.getSoDienThoai());
                }
                sinhVienEntity.setDotDangKyId(dotDangKyId);
                sinhVienEntity.setEmail(sinhVien.getEmail());
                sinhVienEntity.setKhoa(sinhVien.getKhoa());
                sinhVienEntity.setCoSoId(coSoId);

                map.put(sinhVien.getMaSinhVien().toLowerCase(), sinhVien.getMaSinhVien());
                map.put(sinhVien.getEmail().toLowerCase(), sinhVien.getEmail());
                listSinhVienEntity.put(sinhVienEntity.getMaSinhVien(), sinhVienEntity);

                logMessage.append("Đào tạo đã thêm sinh viên '").append(sinhVien.getMaSinhVien())
                            .append(" - ").append(sinhVien.getTenSinhVien()).append("'. ");
            });
            if (response.getStatus()) {
                sinhVienRepository.saveAll(listSinhVienEntity.values());
                try {
                    String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKyRepository.findById(dotDangKyId).get().getTenDotDangKy());
                    LoggerObject loggerObjectActive = writerFileCSV.createLoggerObject(logMessage.toString(), pathFileLogActive, "Thành công");
                    logService.sendLogMessage(loggerObjectActive);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi hệ thống");
            return response;
        }

        return response;
    }

    private void addDataInMap(ConcurrentHashMap<String, String> mapAll, String coSoId, String dotDangKyId) {
        //lấy id, MaSv của tất cả sv theo cơ sở
        List<SimpleEntityProj> getAllMaSV = sinhVienRepository.getAllMaSV(coSoId, dotDangKyId);
        getAllPutMap(mapAll, getAllMaSV);
        // Lấy id, email của tất cả các sv theo cơ sở và đợt đăng ký
        List<SimpleEntityProj> getAllEmail = sinhVienRepository.getAllEmailByDotDangKyId(coSoId, dotDangKyId);
        getAllPutMap(mapAll, getAllEmail);
        // lấy id, tenChuyenNganh tất cả chuyên ngành theo cơ sở
        List<SimpleEntityProj> getAllTenCN = chuyenNganhRepository.findAllSimpleEntityByCoSo(coSoId);
        getAllPutMap(mapAll, getAllTenCN);
        // lấy id, monDatn tất cả đợt đăng ký theo cơ sở
        List<SimpleEntityProj> getAllMaMon = monDatnRepository.getAllMaMon(coSoId);
        getAllPutMap(mapAll, getAllMaMon);

        // lấy id, MaNhom tất cả nhóm theo cơ sở
//        List<SimpleEntityProj> getAllMaNhom = nhomDatnTheoDotRepository.getAllMaNhom(coSoId);
//        getAllPutMap(mapAll, getAllMaNhom);
        // lấy id, tenDotDangKy tất cả đợt đăng ký theo cơ sở
//        List<SimpleEntityProj> getAllTenDDK = dotDangKyRepository.findAllSimpleEntity(coSoId);
//        getAllPutMap(mapAll, getAllTenDDK);
    }

    public void getAllPutMap(ConcurrentHashMap<String, String> mapSimple, List<SimpleEntityProj> getAllSimpleEntityProjs) {
        for (SimpleEntityProj simpleEntityProj : getAllSimpleEntityProjs) {
            mapSimple.put(simpleEntityProj.getName().toLowerCase(), simpleEntityProj.getId());
        }
    }

    @Override
    @Transactional
    public DtLoaiSinhVienResponse importSinhVienKhongDuDieukien(DtLoaiSinhVienKhongDatRequest request) {
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(request.getDotDangKyId());
        if (!dotDangKy.isPresent()) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        DtLoaiSinhVienResponse response = new DtLoaiSinhVienResponse();
        response.setStatus(true);
        ConcurrentMap<String, String> mapMsvLoai = new ConcurrentHashMap<>();
        CopyOnWriteArrayList<LoggerObject> loggerObjects = new CopyOnWriteArrayList<>();
        String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKy.get().getTenDotDangKy());
        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(dotDangKy.get().getTenDotDangKy());

        int columnIndex = -1;
        try {
            Workbook workbook = StreamingReader.builder()
                    .bufferSize(4096)
                    .rowCacheSize(50)
                    .open(request.getFileLoaiSv().getInputStream());

            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        CellType type = cell.getCellType();
                        String val = cell.getStringCellValue();
                        if (type == CellType.STRING &&
                                ("Mã sinh viên".equals(val) ||
                                        "MSV".equals(val)) ||
                                "MSSV".equals(val)) {
                            columnIndex = cell.getColumnIndex();
                            continue;
                        }
                        if (columnIndex != -1 &&
                                cell.getColumnIndex() == columnIndex &&
                                type == CellType.STRING) {
                            if (mapMsvLoai.containsKey(val)) {
                                continue;
                            }
                            mapMsvLoai.put(val, val);
                        }
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (mapMsvLoai.size() == 0) {
            response.setStatus(false);
            response.setMessage("Không có sinh viên nào được đọc");
            return response;
        }
        ConcurrentMap<String, SinhVien> listSvKhongDat = new ConcurrentHashMap<>();
        Map<String, SinhVien> mapSinhVien = new HashMap<>();
        List<SinhVien> listSinhVien = sinhVienRepository.findAllSinhVienByDotDangKyId(request.getDotDangKyId(),
                request.getCoSoId());
        listSinhVien.stream().forEach(sinhVien -> {
            mapSinhVien.put(sinhVien.getMaSinhVien(), sinhVien);
        });
        ConcurrentMap<String, NhomDatn> listNhomDatnDoiTruongNhom = new ConcurrentHashMap<>();
        ConcurrentMap<String, NhomDatn> listNhomDatnDelete = new ConcurrentHashMap<>();
        List<NhomDatn> listNhomDatn = nhomDatnTheoDotRepository.findAllByDotDangKyId(request.getDotDangKyId(), request.getCoSoId());
        ConcurrentMap<String, NhomDatn> mapNhomDatn = new ConcurrentHashMap<>();
        listNhomDatn.parallelStream().forEach(nhomDatn -> {
            mapNhomDatn.put(nhomDatn.getTruongNhomId(), nhomDatn);
        });
        mapMsvLoai.values().parallelStream().forEach(msv -> {
            SinhVien sv = mapSinhVien.get(msv);
            if (sv == null) {
                response.setStatus(false);
                response.setMessage("Mã sinh viên " + msv + " không tồn tại");
                return;
            }
            if (sv.getTrangThai() == TrangThaiSinhVien.DU_DIEU_KIEN) {
                sv.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                listSvKhongDat.put(sv.getId(), sv);
            }
            if (sv.getNhomId() != null) {
                NhomDatn nhomDatn = mapNhomDatn.get(sv.getId());

                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Đào tạo đã loại sinh viên '" + sv.getMaSinhVien()
                                        + "' ra khỏi nhóm '" + nhomDatn.getMaNhom() + "' do không đủ điều kiện",
                                pathFile, "Thành công");
                loggerObject.setTenNhom(nhomDatn.getMaNhom());
                loggerObjects.add(loggerObject);

                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Đào tạo đã loại sinh viên '" + sv.getMaSinhVien()
                                        + "' ra khỏi nhóm '" + nhomDatn.getMaNhom() + "' do không đủ điều kiện",
                                pathFileLogActive, "Thành công");
                loggerObjects.add(loggerObjectActive);
                if (nhomDatn != null) {
                    boolean check = true;
                    for (SinhVien sinhVien : listSinhVien) {
                        if (nhomDatn.getId().equals(sinhVien.getNhomId()) && !sinhVien.getId().equals(sv.getId())) {
                            nhomDatn.setTruongNhomId(sinhVien.getId());
                            check = false;
                            try {
                                LoggerObject loggerObjectOutNhom = writerFileCSV.
                                        createLoggerObject("Đào tạo đổi quyền trưởng nhóm '" + nhomDatn.getMaNhom() + "' " +
                                                        "cho sinh viên '" + sinhVien.getMaSinhVien() + "' do sinh viên '"
                                                        + sv.getMaSinhVien() + "' đã bị loại do không đủ kiều kiện",
                                                pathFile, "Thành công");
                                loggerObjectOutNhom.setTenNhom(nhomDatn.getMaNhom());
                                loggerObjects.add(loggerObjectOutNhom);

                                LoggerObject loggerObjectActiveOutNhom = writerFileCSV.
                                        createLoggerObject("Đào tạo đổi quyền trưởng nhóm '" + nhomDatn.getMaNhom() + "' " +
                                                        "cho sinh viên '" + sinhVien.getMaSinhVien() + "' do sinh viên '"
                                                        + sv.getMaSinhVien() + "' đã bị loại do không đủ kiều kiện",
                                                pathFileLogActive, "Thành công");
                                loggerObjects.add(loggerObjectActiveOutNhom);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break;
                        }
                    }
                    if (check) {
                        listNhomDatnDelete.put(nhomDatn.getId(), nhomDatn);
                        try {
                            LoggerObject loggerObjectActiveDelete = writerFileCSV.
                                    createLoggerObject("Đào tạo đã xóa nhóm '" + nhomDatn.getMaNhom() + "'" +
                                                    " do không có thành viên nào trong nhóm",
                                            pathFileLogActive, "Thành công");
                            logService.sendLogMessage(loggerObjectActiveDelete);

                            LoggerObject loggerObjectDelete = writerFileCSV.
                                    createLoggerObject("Đào tạo đã xóa nhóm '" + nhomDatn.getMaNhom() + "'" +
                                                    " do không có thành viên nào trong nhóm",
                                            pathFile, "Thành công");
                            loggerObjectDelete.setTenNhom(nhomDatn.getMaNhom());
                            logService.sendLogMessage(loggerObjectDelete);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        listNhomDatnDoiTruongNhom.put(nhomDatn.getId(), nhomDatn);
                    }
                }
                sv.setNhomId(null);
                sv.setMonDatnId(null);
            }

            try {
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Sinh viên '" + sv.getMaSinhVien() + "' bị loại vì không đủ điều kiện.",
                                pathFileLogActive, "Thành công");
                loggerObjects.add(loggerObjectActive);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        if (response.getStatus() == false) {
            return response;
        }
        if (listNhomDatnDelete.size() != 0) {
            nhomDatnTheoDotRepository.deleteAll(listNhomDatnDelete.values());
        }
        if (listNhomDatnDoiTruongNhom.size() != 0) {
            nhomDatnTheoDotRepository.saveAll(listNhomDatnDoiTruongNhom.values());
        }
        if (listSvKhongDat.size() == 0) {
            response.setStatus(false);
            response.setMessage("Danh sách sinh viên đã đang ở trạng thái không đủ điều kiện");
        }
        if (response.getStatus()) {
            try {
                sinhVienRepository.saveAll(listSvKhongDat.values());
                response.setMessage("Đã loại sinh viên không đủ điều kiện");
                loggerObjects.forEach(el -> {
                    logService.sendLogMessage(el);
                });
            } catch (Exception e) {
                response.setStatus(false);
                response.setMessage("Lỗi hệ thống");
                return response;
            }
        }
        return response;
    }

    @Override
    public DtSinhVienTheoDotResponse searchStudentById(String id) {
        return sinhVienRepository.searchStudentById(id);
    }

    @Override
    @Transactional
    public SinhVien loaiSinhVien(String id) {
        Map<String, NhomDatn> map = new HashMap<>();
        Optional<SinhVien> sv = sinhVienRepository.findById(id);
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(sv.get().getDotDangKyId());
        Optional<ChuyenNganh> chuyenNganh = chuyenNganhRepository.findById(sv.get().getChuyenNganhId());
        if (!sv.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }
        SinhVien sinhVien = sv.get();

        if (sinhVien.getNhomId() == null) {
            sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
        } else {
            Optional<NhomDatn> nhomDatn = nhomDatnTheoDotRepository.findById(sinhVien.getNhomId());
            try {
                String pathFile = writerFileCSV.getPathFileLuongNhomDatn(dotDangKy.get().getTenDotDangKy());
                LoggerObject loggerObject = writerFileCSV.
                        createLoggerObject("Đào tạo đã xóa sinh viên '" + sinhVien.getMaSinhVien()
                                        + "' ra khỏi nhóm '" + nhomDatn.get().getMaNhom() + "' do không đủ điều kiện",
                                pathFile, "Thành công");
                loggerObject.setTenNhom(nhomDatn.get().getMaNhom());
                loggerObject.setIdChuyenNganh(chuyenNganh.get().getId());
                loggerObject.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
                logService.sendLogMessage(loggerObject);

                String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKy.get().getTenDotDangKy());
                LoggerObject loggerObjectActive = writerFileCSV.
                        createLoggerObject("Đào tạo đã xóa sinh viên '" + sinhVien.getMaSinhVien()
                                        + "' ra khỏi nhóm '" + nhomDatn.get().getMaNhom() + "' do không đủ điều kiện",
                                pathFileLogActive, "Thành công");
                loggerObject.setIdChuyenNganh(chuyenNganh.get().getId());
                loggerObject.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
                logService.sendLogMessage(loggerObjectActive);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (!nhomDatn.get().getTruongNhomId().equals(sinhVien.getId())) {
                sinhVien.setNhomId(null);
                sinhVien.setMonDatnId(null);
                sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
            } else {
                //nếu là nhóm trưởng thì chuyển quyền cho sinh viên trong nhóm
                SinhVien motSVtrongNhom = sinhVienRepository.motSVTrongNhom(sinhVien.getId(),
                        sinhVien.getNhomId(), sinhVien.getCoSoId(), sinhVien.getDotDangKyId());
//                chuyenTruongNhom = map.get(sinhVien.getId());
                if (motSVtrongNhom == null) {
                    nhomDatnTheoDotRepository.delete(nhomDatn.get());
                    sinhVien.setNhomId(null);
                    sinhVien.setMonDatnId(null);
                    sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                    try {
                        String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKy.get().getTenDotDangKy());
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Đào tạo đã xóa nhóm '" + nhomDatn.get().getMaNhom() + "'" +
                                                " do không có thành viên nào trong nhóm",
                                        pathFileLogActive, "Thành công");
                        loggerObjectActive.setIdChuyenNganh(chuyenNganh.get().getId());
                        loggerObjectActive.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
                        logService.sendLogMessage(loggerObjectActive);

                        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(dotDangKy.get().getTenDotDangKy());
                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Đào tạo đã xóa nhóm '" + nhomDatn.get().getMaNhom() + "'" +
                                                " do không có thành viên nào trong nhóm",
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(nhomDatn.get().getMaNhom());
                        loggerObjectActive.setIdChuyenNganh(chuyenNganh.get().getId());
                        loggerObjectActive.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
                        logService.sendLogMessage(loggerObject);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    nhomDatn.get().setTruongNhomId(motSVtrongNhom.getId());
                    nhomDatnTheoDotRepository.save(nhomDatn.get());
                    sinhVien.setNhomId(null);
                    sinhVien.setMonDatnId(null);
                    sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                    try {
                        String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKy.get().getTenDotDangKy());
                        LoggerObject loggerObjectActive = writerFileCSV.
                                createLoggerObject("Đào tạo đổi quyền trưởng nhóm '" + nhomDatn.get().getMaNhom() + "' " +
                                                "cho sinh viên '" + motSVtrongNhom.getMaSinhVien() + "' do sinh viên '"
                                                + sinhVien.getMaSinhVien() + "' đã bị loại do không đủ kiều kiện",
                                        pathFileLogActive, "Thành công");
                        loggerObjectActive.setIdChuyenNganh(chuyenNganh.get().getId());
                        loggerObjectActive.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
                        logService.sendLogMessage(loggerObjectActive);

                        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(dotDangKy.get().getTenDotDangKy());
                        LoggerObject loggerObject = writerFileCSV.
                                createLoggerObject("Đào tạo đổi quyền trưởng nhóm '" + nhomDatn.get().getMaNhom() + "' " +
                                                "cho sinh viên '" + motSVtrongNhom.getMaSinhVien() + "' do sinh viên '"
                                                + sinhVien.getMaSinhVien() + "' đã bị loại do không đủ kiều kiện",
                                        pathFile, "Thành công");
                        loggerObject.setTenNhom(nhomDatn.get().getMaNhom());
                        loggerObjectActive.setIdChuyenNganh(chuyenNganh.get().getId());
                        loggerObjectActive.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
                        logService.sendLogMessage(loggerObject);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        SinhVien svKhongDuDK = sinhVienRepository.save(sinhVien);
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKy.get().getTenDotDangKy());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Sinh viên '" + sinhVien.getMaSinhVien() + "' bị loại vì không đủ điều kiện.",
                            pathFileLogActive, "Thành công");
            loggerObjectActive.setIdChuyenNganh(chuyenNganh.get().getId());
            loggerObjectActive.setTenChuyenNganh(chuyenNganh.get().getTenChuyenNganh());
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return svKhongDuDK;
    }

    @Override
    @Transactional
    public Boolean delete(String id, String coSoId, String dotDangKyId) {
        Optional<SinhVien> checkSV = sinhVienRepository.findById(id);
        if (Objects.isNull(checkSV)) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }
        sinhVienRepository.delete(checkSV.get());

        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKyRepository.findById(checkSV.get().getDotDangKyId()).get().getTenDotDangKy());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Sinh viên '" + checkSV.get().getMaSinhVien() + "' đã được xóa trong hệ thống.",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional
    public SinhVien update(@Valid DtUpdateSinhVienTheoDotRequest command) {

        Optional<SinhVien> checkSinhVien = sinhVienRepository.findById(command.getId());
        Optional<ChuyenNganh> chuyenNganhCu = chuyenNganhRepository.findById(checkSinhVien.get().getChuyenNganhId());
        Optional<MonDatn> monCu = monDatnRepository.findById(checkSinhVien.get().getMonChuongTrinhId());

        SinhVien sinhVienCu = checkSinhVien.get();
        String maSVCu = sinhVienCu.getMaSinhVien();
        String tenSVCu = sinhVienCu.getTenSinhVien();
        String sdtCu = sinhVienCu.getSoDienThoai();
        String emailCu = sinhVienCu.getEmail();
        String khoaCu = sinhVienCu.getKhoa();
        TrangThaiSinhVien trangThaiCu = sinhVienCu.getTrangThai();
        String dieuKien = trangThaiCu.toString();
        String idSinhVienCheckMaSV = sinhVienRepository.findSinhVienByMaSVUpdate(command.getMaSinhVien(),
                command.getId(), command.getCoSoId(), command.getDotDangKyId());
        if (idSinhVienCheckMaSV != null) {
            throw new RestApiException(Message.SINH_VIEN_ALREADY_EXIST);
        }

        String idSinhVienCheckEmail = sinhVienRepository.findSinhVienByEmailUpdate(command.getEmail(),
                command.getId(), command.getCoSoId(), command.getDotDangKyId());
        if (idSinhVienCheckEmail != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }

        Optional<ChuyenNganh> chuyenNganhMoi = chuyenNganhRepository.findById(command.getChuyenNganhId());
        Optional<MonDatn> monMoi = monDatnRepository.findById(command.getMonChuongTrinh());

        SinhVien sinhVien = checkSinhVien.get();
        sinhVien.setMaSinhVien(command.getMaSinhVien());
        sinhVien.setTenSinhVien(command.getTenSinhVien());
        sinhVien.setEmail(command.getEmail());
        sinhVien.setSoDienThoai(command.getSdt());
        sinhVien.setKhoa(command.getKhoa());
        sinhVien.setDotDangKyId(command.getDotDangKyId());
        sinhVien.setMonChuongTrinhId(command.getMonChuongTrinh());
        sinhVien.setChuyenNganhId(command.getChuyenNganhId());
        if (command.getTrangThai() == 0) {
            sinhVien.setTrangThai(TrangThaiSinhVien.DU_DIEU_KIEN);
        } else {
            sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
        }
        StringBuilder logMessage = new StringBuilder();
        if (!(sinhVien.getMaSinhVien()).equals(maSVCu)) {
            logMessage.append("Đào tạo đã sửa mã sinh viên từ '").append(maSVCu)
                    .append("' thành '").append(sinhVien.getMaSinhVien()).append("'.");
        }
        if (!(sinhVien.getTenSinhVien()).equals(tenSVCu)) {
            logMessage.append("Đào tạo đã sửa tên sinh viên từ '").append(tenSVCu)
                    .append("' thành '").append(sinhVien.getTenSinhVien()).append("'.");
        }
        if (!(sinhVien.getSoDienThoai()).equals(sdtCu)) {
            logMessage.append("Đào tạo đã sửa số điện thoại sinh viên '").append(sinhVien.getMaSinhVien())
                    .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append(sdtCu)
                    .append("' thành '").append(sinhVien.getSoDienThoai()).append("'.");
        }
        if (!(sinhVien.getEmail()).equals(emailCu)) {
            logMessage.append("Đào tạo đã sửa email sinh viên '").append(sinhVien.getMaSinhVien())
                    .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append(emailCu)
                    .append("' thành '").append(sinhVien.getEmail()).append("'.");
        }
        if (!(sinhVien.getKhoa()).equals(khoaCu)) {
            logMessage.append("Đào tạo đã sửa khóa của sinh viên '").append(sinhVien.getMaSinhVien())
                    .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append(khoaCu)
                    .append("' thành '").append(sinhVien.getKhoa()).append("'.");
        }
        if (!chuyenNganhCu.get().getId().equals(chuyenNganhMoi.get().getId())) {
            logMessage.append("Đào tạo đã sửa chuyên ngành của sinh viên '").append(sinhVien.getMaSinhVien())
                    .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append(chuyenNganhCu.get().getTenChuyenNganh())
                    .append("' thành '").append(chuyenNganhMoi.get().getTenChuyenNganh()).append("'.");
        }
        if (!monCu.get().getId().equals(monMoi.get().getId())) {
            logMessage.append("Đào tạo đã sửa môn chương trình của sinh viên '").append(sinhVien.getMaSinhVien())
                    .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append(monCu.get().getMaMon())
                    .append("' thành '").append(monMoi.get().getMaMon()).append("'.");
        }
        if (!trangThaiCu.equals(sinhVien.getTrangThai())) {
            if (dieuKien.equals("DU_DIEU_KIEN")) {
                logMessage.append("Đào tạo đã sửa trạng thái của sinh viên '").append(sinhVien.getMaSinhVien())
                        .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append("Đủ điều kiện")
                        .append("' thành '").append("Không đủ điều kiện").append("'.");
            } else {
                logMessage.append("Đào tạo đã sửa trạng thái của sinh viên '").append(sinhVien.getMaSinhVien())
                        .append(" - ").append(sinhVien.getTenSinhVien()).append("' từ '").append("Không đủ điều kiện")
                        .append("' thành '").append("Đủ điều kiện").append("'.");
            }
        }
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKyRepository.findById(sinhVien.getDotDangKyId()).get().getTenDotDangKy());
            LoggerObject loggerObjectActive = writerFileCSV.createLoggerObject(logMessage.toString(), pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    @Transactional
    public SinhVien create(@Valid DtCreateSinhVienRequest command) {
        SinhVien sinhVien = new SinhVien();

        String idSinhVienCheckMaSV = sinhVienRepository.findSinhVienByMaSV(command.getMaSinhVien(),
                command.getCoSoId(), command.getDotDangKyId());
        if (idSinhVienCheckMaSV != null) {
            throw new RestApiException(Message.SINH_VIEN_ALREADY_EXIST);
        }

        String idSinhVienCheckEmail = sinhVienRepository.findSinhVienByEmail(command.getEmail(),
                command.getCoSoId(), command.getDotDangKyId());
        if (idSinhVienCheckEmail != null) {
            throw new RestApiException(Message.EMAIL_FPT_EXIST);
        }
        if (command.getTrangThai() == 0) {
            sinhVien.setTrangThai(TrangThaiSinhVien.DU_DIEU_KIEN);
        } else {
            sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
        }
        sinhVien = formUtils.convertToObject(SinhVien.class, command);
        sinhVien.setSoDienThoai(command.getSdt());
        sinhVien.setMonChuongTrinhId(command.getMonChuongTrinh());
        try {
            String pathFileLogActive = writerFileCSV.getPathFileActiveWithApi(dotDangKyRepository.findById(sinhVien.getDotDangKyId()).get().getTenDotDangKy());
            LoggerObject loggerObjectActive = writerFileCSV.
                    createLoggerObject("Sinh viên '" + sinhVien.getMaSinhVien() + "' đã được thêm vào hệ thống.",
                            pathFileLogActive, "Thành công");
            logService.sendLogMessage(loggerObjectActive);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    public List<DtMonDatnSearchResponse> getAllMonDatnByChuyenNganhId(String chuyenNganhId, String coSoId) {
        return sinhVienRepository.getAllMonDatnByChuyenNganhId(chuyenNganhId, coSoId);
    }

    @Override
    public List<SimpleEntityProj> getMaMonChuongTrinh(String coSoId) {
        return monDatnRepository.getAllMaMon(coSoId);
    }

    @Override
    public List<NhomDatn> getListNhomMon(String coSoId) {
        return nhomDatnTheoDotRepository.findAllByDotDangKyId(commonHelper.getDotDangKyHienTai(), coSoId);
    }


}
