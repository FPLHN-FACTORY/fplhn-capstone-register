package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotExport;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotImport;
import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.DtLoaiSinhVienKhongDatRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.repository.DtNhomDatnTheoDotRepository;
import com.fpolydatn.core.daotao.repository.DtSinhVienTheoDotRepository;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
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


    private HashMap<String, String> map;

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
    public DtLoaiSinhVienResponse DtSinhVienImport(MultipartFile file, String coSoId) throws IOException {
        DtLoaiSinhVienResponse response = new DtLoaiSinhVienResponse();
        response.setStatus(true);
        DtSinhVienTheoDotImport sinhVienTheoDotImport = new DtSinhVienTheoDotImport();
        List<DtCreateSinhVienTheoDotRequest> sinhVienList = sinhVienTheoDotImport.importData(file);
        List<SinhVien> listSinhVienEntity = new ArrayList<>();
        map = new HashMap<>();
        addDataInMap(map, coSoId);
        sinhVienList.parallelStream().forEach(sinhVien -> {
            SinhVien sinhVienEntity = new SinhVien();
            if (map.get(sinhVien.getMaSinhVien()) != null) {
                sinhVienEntity.setId(map.get(sinhVien.getMaSinhVien()));
            } else if (!sinhVien.getMaSinhVien().matches("[a-zA-Z]{2}+\\d+")) {
                response.setStatus(false);
                response.setMessage("Mã sinh viên \"" + sinhVien.getMaSinhVien() + "\" không đúng định dạng");
                return;
            }

            if (sinhVien.getTenSinhVien().isEmpty() || sinhVien.getMaSinhVien().isEmpty() ||
                    sinhVien.getSoDienThoai().isEmpty() || sinhVien.getEmail().isEmpty() ||
                    sinhVien.getKhoa().isEmpty() || sinhVien.getChuyenNganh().isEmpty() ||
                    sinhVien.getDotDangKy().isEmpty()) {
                response.setStatus(false);
                response.setMessage("Không để trống (Mã, tên, sđt, email, khóa, tên chuyên ngành, đợt đăng ký)");
                return;
            }

            if (map.get(sinhVien.getMaNhom()) != null) {
                sinhVienEntity.setNhomId(map.get(sinhVien.getMaNhom()));
            } else if (sinhVien.getMaNhom().isEmpty()) {
                sinhVienEntity.setNhomId(null);
            } else if (!map.containsKey(sinhVien.getMaNhom())) {
                response.setStatus(false);
                response.setMessage("Mã nhóm \"" + sinhVien.getMaNhom() + "\" không tồn tại");
                return;
            }

            if (map.get(sinhVien.getChuyenNganh()) != null) {
                sinhVienEntity.setChuyenNganhId(map.get(sinhVien.getChuyenNganh()));
            } else if (!map.containsKey(sinhVien.getChuyenNganh())) {
                response.setStatus(false);
                response.setMessage("Tên chuyên ngành \"" + sinhVien.getChuyenNganh() + "\" không tồn tại");
                return;
            }

            if (map.get(sinhVien.getDotDangKy()) != null) {
                sinhVienEntity.setDotDangKyId(map.get(sinhVien.getDotDangKy()));
            } else if (!map.containsKey(sinhVien.getDotDangKy())) {
                response.setStatus(false);
                response.setMessage("Tên đợt đăng ký \"" + sinhVien.getDotDangKy() + "\" không tồn tại");
                return;
            }

            if (map.get(sinhVien.getMonDATN()) != null) {
                sinhVienEntity.setMonChuongTrinhId(map.get(sinhVien.getMonDATN()));
            } else if (!map.containsKey(sinhVien.getMonDATN())) {
                response.setStatus(false);
                response.setMessage("Mã môn chương trình \"" + sinhVien.getMonDATN() + "\" không tồn tại");
                return;
            }

            if (!sinhVien.getSoDienThoai().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
                response.setStatus(false);
                response.setMessage("Số điện thoại \"" + sinhVien.getSoDienThoai() + "\" không đúng định dạng");
                return;
            }

            if (!sinhVien.getEmail().matches("\\w+@fpt.edu.vn")) {
                response.setStatus(false);
                response.setMessage("Email \"" + sinhVien.getEmail() + "\" không đúng định dạng");
                return;
            }

            sinhVienEntity.setTenSinhVien(sinhVien.getTenSinhVien());
            sinhVienEntity.setMaSinhVien(sinhVien.getMaSinhVien());
            sinhVienEntity.setSoDienThoai(sinhVien.getSoDienThoai());
            sinhVienEntity.setEmail(sinhVien.getEmail());
            sinhVienEntity.setKhoa(sinhVien.getKhoa());
            sinhVienEntity.setCoSoId(coSoId);

            listSinhVienEntity.add(sinhVienEntity);
        });

        sinhVienRepository.saveAll(listSinhVienEntity);

        return response;
    }

    public void addDataInMap(HashMap<String, String> mapAll, String coSoId) {

        //lấy id, MaSv của tất cả sv theo cơ sở
        List<SimpleEntityProj> getAllMaSV = sinhVienRepository.getAllMaSV(coSoId);
        getAllPutMap(mapAll, getAllMaSV);
        // lấy id, MaNhom tất cả nhóm theo cơ sở
        List<SimpleEntityProj> getAllMaNhom = nhomDatnTheoDotRepository.getAllMaNhom(coSoId);
        getAllPutMap(mapAll, getAllMaNhom);
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

    public void getAllPutMap(HashMap<String, String> mapSimple, List<SimpleEntityProj> getAllSimpleEntityProjs) {
        for (SimpleEntityProj simpleEntityProj : getAllSimpleEntityProjs) {
            mapSimple.put(simpleEntityProj.getName(), simpleEntityProj.getId());
        }
    }

    @Override
    public DtLoaiSinhVienResponse loaiSinhVienKhongDat(DtLoaiSinhVienKhongDatRequest request) {
        DtLoaiSinhVienResponse response = new DtLoaiSinhVienResponse();
        response.setStatus(true);
        List<String> listMsvLoai = new ArrayList<>();
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
                            }
                            listMsvLoai.add(val);
                        }
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<SinhVien> listSvKhongDat = new ArrayList<>();
        Map<String, SinhVien> mapSinhVien = new HashMap<>();
        List<SinhVien> listSinhVien = sinhVienRepository.findAllSinhVienByDotDangKyId(request.getDotDangKyId(),
                request.getCoSoId());
        listSinhVien.stream().forEach(sinhVien -> {
            mapSinhVien.put(sinhVien.getMaSinhVien(), sinhVien);
        });
        List<NhomDatn> listNhomDatnDoiTruongNhom = new ArrayList<>();
        List<NhomDatn> listNhomDatnDelete = new ArrayList<>();
        List<NhomDatn> listNhomDatn = nhomDatnTheoDotRepository.findAllByDotDangKyId(request.getDotDangKyId(), request.getCoSoId());
        Map<String, NhomDatn> mapNhomDatn = new HashMap<>();
        listNhomDatn.stream().forEach(nhomDatn -> {
            mapNhomDatn.put(nhomDatn.getTruongNhomId(), nhomDatn);
        });
        listMsvLoai.parallelStream().forEach(msv -> {
            SinhVien sv = mapSinhVien.get(msv);
            if (sv == null) {
                response.setStatus(false);
                response.setMessage("Mã sinh viên " + msv + " không tồn tại");
                return;
            }
            if (sv.getTrangThai() == TrangThaiSinhVien.DU_DIEU_KIEN) {
                sv.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                listSvKhongDat.add(sv);
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
                        listNhomDatnDelete.add(nhomDatn);
                    } else {
                        listNhomDatnDoiTruongNhom.add(nhomDatn);
                    }
                }
                sv.setNhomId(null);
                sv.setMonDatnId(null);
            }
        });
        if (listNhomDatnDelete.size() != 0) {
            nhomDatnTheoDotRepository.deleteAll(listNhomDatnDelete);
        }
        if (listNhomDatnDoiTruongNhom.size() != 0) {
            nhomDatnTheoDotRepository.saveAll(listNhomDatnDoiTruongNhom);
        }
        if (listSvKhongDat.size() == 0) {
            response.setStatus(false);
            response.setMessage("Lỗi. Loại không thành công");
        }
        if (response.getStatus()) {
            sinhVienRepository.saveAll(listSvKhongDat);
            response.setMessage("Đã loại sinh viên không đủ điều kiện");
        }
        return response;
    }

    @Override
    public DtSinhVienTheoDotResponse searchStudentById(String id, String coSoId) {
        return sinhVienRepository.searchStudentById(id, coSoId);
    }

    @Override
    public int loaiSinhVien(String id) {
        try {
            return sinhVienRepository.loaiSinhVien(id);
        }catch (Exception e){
            return 0;
        }
    }
}
