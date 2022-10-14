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
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
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

    private List<DtSinhVienTheoDotResponse> list;

    private HashMap<String, String> map;

    @Override
    public PageableObject<DtSinhVienTheoDotResponse> findAllSinhVien(final FindDtSinhVienTheoDotRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (dotDangKyId != null) {
            request.setIdDotDangKy(dotDangKyId);
        } else {
            request.setIdDotDangKy("");
        }
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.findAllSinhVien(pageable, request);
        list = res.stream().toList();
        return new PageableObject(res);
    }

    @Override
    public PageableObject<DtSinhVienTheoDotResponse> searchStudent(final FindDtSinhVienTheoDotRequest request) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (request.getIdDotDangKy().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                request.setIdDotDangKy(dotDangKyId);
            } else {
                request.setIdDotDangKy("");
            }
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DtSinhVienTheoDotResponse> res = sinhVienRepository.searchStudent(request, pageable);
        list = res.stream().toList();
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
        DtSinhVienTheoDotExport dtSinhVienExport = new DtSinhVienTheoDotExport(list);
        dtSinhVienExport.exportData(response);
    }

    @Override
    public void MauImportExcel(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MauImportExcel" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        List<DtSinhVienTheoDotResponse> listSv = new ArrayList<>();
        DtSinhVienTheoDotExport dtSinhVienExport = new DtSinhVienTheoDotExport(listSv);
        dtSinhVienExport.exportMauImport(response);
    }

    @Override
    public RestApiException DtSinhVienImport(MultipartFile file, String coSoId) throws IOException {

        DtSinhVienTheoDotImport sinhVienTheoDotImport = new DtSinhVienTheoDotImport();
        List<DtCreateSinhVienTheoDotRequest> sinhVienList = sinhVienTheoDotImport.importData(file);
        List<SinhVien> listSinhVienEntity = new ArrayList<>();
        map = new HashMap<>();

        addDataInMap(map, coSoId);

        for (DtCreateSinhVienTheoDotRequest sinhVien : sinhVienList) {
            SinhVien sinhVienEntity = new SinhVien();
            if (map.get(sinhVien.getMaSinhVien()) != null) {
                sinhVienEntity.setId(map.get(sinhVien.getMaSinhVien()));
            } else if (!sinhVien.getMaSinhVien().matches("[a-zA-Z]{2}+\\d+")) {
                throw new RestApiException(Message.MA_SV_MALFORMED);
            }

            if (sinhVien.getTenSinhVien().isEmpty() || sinhVien.getMaSinhVien().isEmpty() ||
                    sinhVien.getSoDienThoai().isEmpty() || sinhVien.getEmail().isEmpty() ||
                    sinhVien.getKhoa().isEmpty() || sinhVien.getChuyenNganh().isEmpty() ||
                    sinhVien.getDotDangKy().isEmpty()) {
                throw new RestApiException(Message.EMPTY);
            }

            if (map.get(sinhVien.getMaNhom()) != null) {
                sinhVienEntity.setNhomId(map.get(sinhVien.getMaNhom()));
            }

            if (map.get(sinhVien.getChuyenNganh()) != null) {
                sinhVienEntity.setChuyenNganhId(map.get(sinhVien.getChuyenNganh()));
            } else if (!map.containsKey(sinhVien.getChuyenNganh())) {
                throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
            }

            if (map.get(sinhVien.getDotDangKy()) != null) {
                sinhVienEntity.setDotDangKyId(map.get(sinhVien.getDotDangKy()));
            } else if (!map.containsKey(sinhVien.getDotDangKy())) {
                throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
            }

            if (map.get(sinhVien.getMonDATN()) != null) {
                sinhVienEntity.setMonDatnId(map.get(sinhVien.getMonDATN()));
            } else if (!map.containsKey(sinhVien.getMonDATN())) {
                throw new RestApiException(Message.MON_DATN_NOT_EXIST);
            }

            if (!sinhVien.getSoDienThoai().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
                throw new RestApiException(Message.NUMBER_FORMAT_EXCEPTION);
            }

            if (!sinhVien.getEmail().matches("\\w+@fpt.edu.vn")) {
                throw new RestApiException(Message.EMAIL_MALFORMED);
            }

            sinhVienEntity.setTenSinhVien(sinhVien.getTenSinhVien());
            sinhVienEntity.setMaSinhVien(sinhVien.getMaSinhVien());
            sinhVienEntity.setSoDienThoai(sinhVien.getSoDienThoai());
            sinhVienEntity.setEmail(sinhVien.getEmail());
            sinhVienEntity.setKhoa(sinhVien.getKhoa());
            sinhVienEntity.setCoSoId(coSoId);

            listSinhVienEntity.add(sinhVienEntity);
        }
        sinhVienRepository.saveAll(listSinhVienEntity);

        return new RestApiException(Message.SUCCESS);
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
                        if (type == CellType.STRING &&
                                (cell.getStringCellValue().equals("Mã sinh viên") ||
                                        cell.getStringCellValue().equals("MSV"))) {
                            columnIndex = cell.getColumnIndex();
                            continue;
                        }
                        if (columnIndex != -1 &&
                                cell.getColumnIndex() == columnIndex &&
                                type == CellType.STRING) {
                            listMsvLoai.add(cell.getStringCellValue());
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

        listMsvLoai.parallelStream().forEach(msv -> {
            SinhVien sv = mapSinhVien.get(msv);
            if (sv != null){
                if (sv.getTrangThai() == TrangThaiSinhVien.DU_DIEU_KIEN){
                    sv.setTrangThai(TrangThaiSinhVien.KHONG_DU_DIEU_KIEN);
                    listSvKhongDat.add(sv);
                }
            } else {
                response.setStatus(false);
                response.setMessage("Mã sinh viên " + msv + " không tồn tại");
                return;
            }
        });
        if (response.getStatus()){
            sinhVienRepository.saveAll(listSvKhongDat);
        }
        return response;
    }

}
