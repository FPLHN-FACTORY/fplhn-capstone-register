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
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.FormUtils;
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
import org.springframework.web.multipart.MultipartFile;
import com.github.pjfanning.xlsx.StreamingReader;

import javax.servlet.http.HttpServletResponse;
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

/**
 * @author thepvph20110
 */
@Service
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

        try {
            response.setStatus(true);
            DtSinhVienTheoDotImport sinhVienTheoDotImport = new DtSinhVienTheoDotImport();
            List<DtCreateSinhVienTheoDotRequest> sinhVienList = sinhVienTheoDotImport.importData(file);
            if (sinhVienList.size() <= 0) {
                response.setStatus(false);
                response.setMessage("Import file không thành công");
            }
            ConcurrentMap<String, SinhVien> listSinhVienEntity = new ConcurrentHashMap<>();
            map = new ConcurrentHashMap<>();
            addDataInMap(map, coSoId, dotDangKyId);
            sinhVienList.parallelStream().forEach(sinhVien -> {
                SinhVien sinhVienEntity = new SinhVien();
                if (sinhVien.getTenSinhVien().isEmpty() || sinhVien.getMaSinhVien().isEmpty() ||
                        sinhVien.getEmail().isEmpty() ||
                        sinhVien.getChuyenNganh().isEmpty() ||
                        sinhVien.getDotDangKy().isEmpty()) {
                    response.setStatus(false);
                    response.setMessage("Không để trống (Mã, tên, email, tên chuyên ngành, đợt đăng ký)");
                    return;
                }
                if (map.containsKey(sinhVien.getMaSinhVien())) {
                    response.setStatus(false);
                    response.setMessage("Mã sinh viên " + sinhVien.getMaSinhVien() + " không được trùng");
                    return;
                }

                if (map.containsKey(sinhVien.getChuyenNganh())) {
                    sinhVienEntity.setChuyenNganhId(map.get(sinhVien.getChuyenNganh()));
                } else {
                    response.setStatus(false);
                    response.setMessage("Tên chuyên ngành \"" + sinhVien.getChuyenNganh() + "\" không tồn tại");
                    return;
                }

                if (map.containsKey(sinhVien.getDotDangKy())) {
                    sinhVienEntity.setDotDangKyId(map.get(sinhVien.getDotDangKy()));
                } else {
                    response.setStatus(false);
                    response.setMessage("Tên đợt đăng ký \"" + sinhVien.getDotDangKy() + "\" không tồn tại");
                    return;
                }

                if (map.containsKey(sinhVien.getMonDATN())) {
                    sinhVienEntity.setMonChuongTrinhId(map.get(sinhVien.getMonDATN()));
                } else {
                    response.setStatus(false);
                    response.setMessage("Mã môn chương trình \"" + sinhVien.getMonDATN() + "\" không tồn tại");
                    return;
                }

                if (map.containsKey(sinhVien.getEmail())) {
                    response.setStatus(false);
                    response.setMessage("Email " + sinhVien.getEmail() + " không được trùng");
                    return;
                }

                sinhVienEntity.setTenSinhVien(sinhVien.getTenSinhVien());
                sinhVienEntity.setMaSinhVien(sinhVien.getMaSinhVien());
                if (sinhVien.getSoDienThoai() == null) {
                    sinhVienEntity.setSoDienThoai("");
                } else {
                    sinhVienEntity.setSoDienThoai(sinhVien.getSoDienThoai());
                }
                sinhVienEntity.setEmail(sinhVien.getEmail());
                sinhVienEntity.setKhoa(sinhVien.getKhoa());
                sinhVienEntity.setCoSoId(coSoId);

                map.put(sinhVien.getMaSinhVien(), sinhVien.getMaSinhVien());
                map.put(sinhVien.getEmail(), sinhVien.getEmail());
                listSinhVienEntity.put(sinhVienEntity.getMaSinhVien(), sinhVienEntity);
            });
            if (response.getStatus()) {
                sinhVienRepository.saveAll(listSinhVienEntity.values());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi hệ thống");
            return response;
        }

        return response;
    }

    public void addDataInMap(ConcurrentHashMap<String, String> mapAll, String coSoId, String dotDangKyId) {

        //lấy id, MaSv của tất cả sv theo cơ sở
        List<SimpleEntityProj> getAllMaSV = sinhVienRepository.getAllMaSV(coSoId, dotDangKyId);
        getAllPutMap(mapAll, getAllMaSV);
        // lấy id, MaNhom tất cả nhóm theo cơ sở
//        List<SimpleEntityProj> getAllMaNhom = nhomDatnTheoDotRepository.getAllMaNhom(coSoId);
//        getAllPutMap(mapAll, getAllMaNhom);
        // lấy id, tenChuyenNganh tất cả chuyên ngành theo cơ sở
        List<SimpleEntityProj> getAllTenCN = chuyenNganhRepository.findAllSimpleEntityByCoSo(coSoId);
        getAllPutMap(mapAll, getAllTenCN);
        // lấy id, tenDotDangKy tất cả đợt đăng ký theo cơ sở
        List<SimpleEntityProj> getAllTenDDK = dotDangKyRepository.findAllSimpleEntity(coSoId);
        getAllPutMap(mapAll, getAllTenDDK);
        // lấy id, monDatn tất cả đợt đăng ký theo cơ sở
        List<SimpleEntityProj> getAllMaMon = monDatnRepository.getAllMaMon(coSoId);
        getAllPutMap(mapAll, getAllMaMon);
    }

    public void getAllPutMap(ConcurrentHashMap<String, String> mapSimple, List<SimpleEntityProj> getAllSimpleEntityProjs) {
        for (SimpleEntityProj simpleEntityProj : getAllSimpleEntityProjs) {
            mapSimple.put(simpleEntityProj.getName(), simpleEntityProj.getId());
        }
    }

    @Override
    public DtLoaiSinhVienResponse importSinhVienKhongDuDieukien(DtLoaiSinhVienKhongDatRequest request) {
        DtLoaiSinhVienResponse response = new DtLoaiSinhVienResponse();
        response.setStatus(true);
        ConcurrentMap<String, String> mapMsvLoai = new ConcurrentHashMap<>();
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
                            if (!val.matches("[a-zA-Z]{2}\\d+")) {
                                response.setStatus(false);
                                response.setMessage("Mã sinh viên " + val + " không đúng định dạng");
                                return response;
                            } else if (mapMsvLoai.containsKey(val)) {
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
                if (nhomDatn != null) {
                    boolean check = true;
                    for (SinhVien sinhVien : listSinhVien) {
                        if (nhomDatn.getId().equals(sinhVien.getNhomId()) && !sinhVien.getId().equals(sv.getId())) {
                            nhomDatn.setTruongNhomId(sinhVien.getId());
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        listNhomDatnDelete.put(nhomDatn.getId(), nhomDatn);
                    } else {
                        listNhomDatnDoiTruongNhom.put(nhomDatn.getId(), nhomDatn);
                    }
                }
                sv.setNhomId(null);
                sv.setMonDatnId(null);
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
    public SinhVien loaiSinhVien(String id, String coSoId, String dotDangKyId) {
        Map<String, NhomDatn> map = new HashMap<>();
        Optional<SinhVien> sv = sinhVienRepository.findById(id);
        if (!sv.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }
        SinhVien sinhVien = sv.get();
        List<NhomDatn> nhomDatnList = nhomDatnTheoDotRepository.findAll();
        NhomDatn chuyenTruongNhom;
        for (NhomDatn nhomDatn : nhomDatnList) {
            map.put(nhomDatn.getTruongNhomId(), nhomDatn);
        }

        if (sinhVien.getNhomId() == null) {
            sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
        } else {
            if (map.get(sinhVien.getId()) == null) {
                sinhVien.setNhomId(null);
                sinhVien.setMonDatnId(null);
                sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
            } else {
                //nếu là nhóm trưởng thì chuyển quyền cho sinh viên trong nhóm
                SinhVien motSVtrongNhom = sinhVienRepository.motSVTrongNhom(sinhVien.getId(),
                        sinhVien.getNhomId(), coSoId, dotDangKyId);
                chuyenTruongNhom = map.get(sinhVien.getId());
                if (motSVtrongNhom == null) {
                    nhomDatnTheoDotRepository.delete(chuyenTruongNhom);
                    sinhVien.setNhomId(null);
                    sinhVien.setMonDatnId(null);
                    sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                } else {
                    chuyenTruongNhom.setTruongNhomId(motSVtrongNhom.getId());
                    nhomDatnTheoDotRepository.save(chuyenTruongNhom);
                    sinhVien.setNhomId(null);
                    sinhVien.setMonDatnId(null);
                    sinhVien.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                }

            }
        }
        SinhVien svKhongDuDK = sinhVienRepository.save(sinhVien);

        return svKhongDuDK;
    }

    @Override
    public Boolean delete(String id, String coSoId, String dotDangKyId) {
        Optional<SinhVien> checkSV = sinhVienRepository.findById(id);
        if (Objects.isNull(checkSV)) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }
        sinhVienRepository.delete(checkSV.get());
        return true;
    }

    @Override
    public SinhVien update(DtUpdateSinhVienTheoDotRequest command) {

        Optional<SinhVien> checkSinhVien = sinhVienRepository.findById(command.getId());

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
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    public SinhVien create(DtCreateSinhVienRequest command) {
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
