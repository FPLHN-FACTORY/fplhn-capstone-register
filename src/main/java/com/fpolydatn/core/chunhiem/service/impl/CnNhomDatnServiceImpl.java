package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.excel.CnSinhVienTheoDotExport;
import com.fpolydatn.core.chunhiem.excel.CnSinhVienTheoDotImport;
import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnCreateSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienImport;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotExcel;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.EmailSender;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.NhomDatnHelper;
import org.apache.commons.math3.analysis.function.Sinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author thangncph26123
 */
@Service
public class CnNhomDatnServiceImpl implements CnNhomDatnService {

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private CnSinhVienRepository cnSinhVienRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    private NhomDatnHelper nhomDatnHelper;

    @Autowired
    private CnMonDatnRepository cnMonDatnRepository;

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    private EmailSender emailSender;

    private List<CnSinhVienTheoDotExcel> list;

    List<CnCreateSinhVienTheoDotRequest> sinhVienList;

    ConcurrentHashMap<String, SinhVien> mapSinhVien;

    ConcurrentHashMap<String, SinhVien> mapTruongNhom;

    ConcurrentHashMap<String, NhomDatn> mapNhomDatn;

    ConcurrentHashMap<String, GiangVienHuongDan> mapGiangVien;

    @Override
    public PageableObject<CnNhomDatnResponse> search(CnFindNhomDatnRequest request, String idChuNhiem) {
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (request.getDotDangKyId() == null) {
            if (dotDangKyId != null) {
                request.setDotDangKyId(dotDangKyId);
            } else {
                request.setDotDangKyId("");
            }
        } else if (request.getDotDangKyId().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                request.setDotDangKyId(dotDangKyId);
            } else {
                request.setDotDangKyId("");
            }
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnNhomDatnResponse> res = cnNhomDatnRepository.findNhomDatn(request, pageable, idChuNhiem);
        list = cnNhomDatnRepository.findNhomDatnExport(request, idChuNhiem, TrangThaiSinhVien.DU_DIEU_KIEN.ordinal());
        return new PageableObject(res);
    }

    @Override
    public boolean chotDeTai(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.GVHD_CHOT) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CNBM_CHOT);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        return true;
    }

    @Override
    public boolean xacNhanNhom(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.MOI_THANH_LAP) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        Optional<GiangVienHuongDan> gvhd = null;
        List<SinhVien> listSinhVien = cnSinhVienRepository.getAllSinhVien(nhomDatnCheck.get().getId(), commonHelper.getDotDangKyHienTai());
        int soThanhvien = listSinhVien.size();
        String[] email = new String[soThanhvien];
        for (int i = 0; i < soThanhvien; i++) {
            email[i] = listSinhVien.get(i).getEmail();
        }
        if (nhomDatnCheck.get().getDotDangKyId() != null) {
            SinhVien truongNhom = null;
            for (SinhVien sv : listSinhVien) {
                if (sv.getId().equals(nhomDatnCheck.get().getTruongNhomId())) {
                    truongNhom = sv;
                }
            }
            if (nhomDatnCheck.get().getGiangVienId() != null) {
                gvhd = cnGiangVienHuongDanReponsitory.findById(nhomDatnCheck.get().getGiangVienId());
                String[] messageGV = new String[]{"CNBM đã xác nhận nhóm bạn hướng dẫn nhóm:",
                        "Mã nhóm: " + nhomDatnCheck.get().getMaNhom(), "Số thành viên: " + soThanhvien,
                        "Trưởng nhóm: " + truongNhom.getMaSinhVien() + " - " + truongNhom.getTenSinhVien(),
                        "SĐT: " + truongNhom.getSoDienThoai()};
                emailSender.sendEmail(new String[]{gvhd.get().getEmailFe()},
                        "[FCR] Chủ nhiệm bộ môn xác nhận nhóm",
                        "Xác nhận nhóm thành lập", messageGV);
            }
        }
        String[] messageSV = null;
        if (!gvhd.isPresent()) {
            messageSV = new String[]{"Chủ nhiệm đã xác nhận nhóm của bạn được thành lập:", "GVHD: Chưa có",
                    "Tên đề tài: " + (nhomDatnCheck.get().getTenDeTai() == null ? "chưa có" : nhomDatnCheck.get().getTenDeTai())};
        } else {
            messageSV = new String[]{"Chủ nhiệm đã xác nhận nhóm của bạn được thành lập:",
                    "GVHD: " + gvhd.get().getTenGvhd() + " - " + gvhd.get().getTenTaiKhoan(),
                    "SĐT: " + gvhd.get().getSoDienThoai(),
                    "Email: " + gvhd.get().getEmailFe(),
                    "Tên đề tài: " + (nhomDatnCheck.get().getTenDeTai() == null ? "chưa có" : nhomDatnCheck.get().getTenDeTai())};
        }
        emailSender.sendEmail(email, "[FCR] Chủ nhiệm bộ môn xác nhận nhóm",
                "Xác nhận nhóm thành lập", messageSV);
        return true;
    }

    @Override
    public boolean tuChoiChotDeTai(String id, String nhanXet) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.GVHD_CHOT) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CAN_SUA);
        nhomDatnCheck.get().setNhanXet(nhanXet);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        return true;
    }

    @Override
    public Boolean delete(String id) {
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        cnNhomDatnRepository.delete(nhomDatnCheck.get());
        List<SinhVien> listSinhVien = cnSinhVienRepository.getAllSinhVien(id, commonHelper.getDotDangKyHienTai());
        for (SinhVien xx : listSinhVien) {
            cnSinhVienRepository.deleteSinhVienOutGroup(xx.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
        }
        int soThanhvien = listSinhVien.size();
        String[] email = new String[soThanhvien];
        for (int i = 0; i < soThanhvien; i++) {
            email[i] = listSinhVien.get(i).getEmail();
        }
        String[] message = new String[]{"Chủ nhiệm đã xóa nhóm của bạn"};
        emailSender.sendEmail(email, "[FCR] Chủ nhiệm bộ môn xóa nhóm",
                "Nhóm đã bị xóa", message);
        return true;
    }

    @Override
    public NhomDatn findById(String id) {
        Optional<NhomDatn> nhomdatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomdatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return nhomdatnCheck.get();
    }

    @Override
    public boolean create(@Valid final CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        String idGiangVien = cnCreateNhomDatnRequest.getIdGiangVien();
        if (cnGiangVienHuongDanReponsitory.findById(idGiangVien) == null) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        if (!idGiangVien.equals("")) {
            if (commonHelper.getSoNhomGiangVienDangHuongDan(idGiangVien) ==
                    cnNhomDatnRepository.getSoNhomGiangVienHuongDan(idGiangVien, commonHelper.getDotDangKyHienTai())) {
                throw new RestApiException(Message.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM);
            }
        }
        String idTruongNhomCheck = cnNhomDatnRepository.getSinhVienByMaSinhVien(cnCreateNhomDatnRequest.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
        if (idTruongNhomCheck == null) {
            throw new RestApiException(Message.STUDENT_NOT_EXIST);
        }
        String idTruongNhom = cnNhomDatnRepository.getSinhVienByMaSinhVienAndMaNhom(cnCreateNhomDatnRequest.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
        if (idTruongNhom == null) {
            throw new RestApiException(Message.STUDENT_HAVE_GROUP);
        }
        String monDatnId = cnCreateNhomDatnRequest.getMonDatnId();
        if (cnMonDatnRepository.findById(monDatnId) == null) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        String checkMonTuongDuong = cnSinhVienRepository.checkMonTuongDuong(monDatnId, idTruongNhom, commonHelper.getDotDangKyHienTai());
        if (checkMonTuongDuong == null) {
            throw new RestApiException(Message.MON_DATN_KHONG_THOA_MAN_VOI_SINH_VIEN);
        }
        NhomDatn nhomDatn = new NhomDatn();
        nhomDatn.setTruongNhomId(idTruongNhom);
        if ("".equals(cnCreateNhomDatnRequest.getIdGiangVien().trim())) {
            nhomDatn.setGiangVienId(null);
        }
        String maNhom = nhomDatnHelper.genMaNhomTuDong();
        if (maNhom == null) {
            throw new RestApiException(Message.TAO_NHOM_THAT_BAI);
        }
        nhomDatn.setMaNhom(maNhom);
        nhomDatn.setMatKhau(cnCreateNhomDatnRequest.getMatKhau());
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        nhomDatn.setCoSoId(cnCreateNhomDatnRequest.getCoSoId());
        nhomDatn.setDotDangKyId(cnCreateNhomDatnRequest.getDotDangKyId());
        nhomDatn.setId(cnNhomDatnRepository.save(nhomDatn).getId());
        cnNhomDatnRepository.setNhomForTruongNhom(nhomDatn.getId(), monDatnId, idTruongNhom, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public List<String> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req, String idChuNhiem) {
        return cnSinhVienRepository.getStringListSinhVienNoGroup(req, idChuNhiem);
    }

    @Override
    public boolean checkHanChuNhiem() {
        return mocThoiGianCommon.checkMocThoiGianChuNhiem();
    }

    @Override
    public void CnSinhVienExport(HttpServletResponse response, String typeExcel, CnFindNhomDatnRequest request) throws IOException {
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=SinhVienTheoDot" + currentDateTime + "." + typeExcel;
            response.setHeader(headerKey, headerValue);
            CnSinhVienTheoDotExport cnNhomDatnExport = new CnSinhVienTheoDotExport();
            cnNhomDatnExport.employeeDetailReport(response, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CnSinhVienImport CnSinhVienImport(MultipartFile file, String dotDangKyId, String coSoId, String idChuNhiem) throws IOException {
        CnSinhVienImport response = new CnSinhVienImport();
        try {
            response.setStatus(true);
            CnSinhVienTheoDotImport sinhVienTheoDotImport = new CnSinhVienTheoDotImport();
            sinhVienList = new ArrayList<>();
            sinhVienList = sinhVienTheoDotImport.importData(file);
            ConcurrentMap<String, SinhVien> listSinhVienEntity = new ConcurrentHashMap();
            ConcurrentMap<String, NhomDatn> listNhomDatnEntity = new ConcurrentHashMap<>();
            ConcurrentMap<String, String> listMaNhomCreate = new ConcurrentHashMap<>();
            ConcurrentMap<String, CnCreateSinhVienTheoDotRequest> mapTruongNhomCreate = new ConcurrentHashMap<>();
            mapSinhVien = new ConcurrentHashMap<>();
            mapNhomDatn = new ConcurrentHashMap<>();
            mapGiangVien = new ConcurrentHashMap<>();
            mapTruongNhom = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, CnCreateSinhVienTheoDotRequest> mapSinhVienTaoNhom = new ConcurrentHashMap<>();
            addDataInMapSinhVien(mapSinhVien, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapNhomDatn(mapNhomDatn, dotDangKyId, coSoId, idChuNhiem);
            addDataInMapGiangVien(mapGiangVien, dotDangKyId, coSoId);
            addDataInMapTruongNhomCu(mapTruongNhom, dotDangKyId, coSoId, idChuNhiem);

            sinhVienList.parallelStream().forEach(sinhVien -> {
                SinhVien sv = mapSinhVien.get(sinhVien.getMaSinhVien());
                NhomDatn nhomDatn = mapNhomDatn.get(sinhVien.getMaNhom());
                if (nhomDatn != null) {
                    if (countSoLuongThanhVien(nhomDatn.getMaNhom())) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " quá 7 người");
                        return;
                    }
                }
                GiangVienHuongDan giangVien = mapGiangVien.get(sinhVien.getGvhd());
                if (giangVien != null) {
                    if (countSoNhomGiangVienDangHuongDan(giangVien.getTenTaiKhoan())) {
                        response.setStatus(false);
                        response.setMessage("Giảng viên " + giangVien.getTenTaiKhoan() + " đang hướng dẫn số nhóm nhiều hơn đăng ký");
                        return;
                    }
                }
                if (nhomDatn != null) {
                    if (countTruongNhom(nhomDatn.getMaNhom()) == 0) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " đang không có trưởng nhóm");
                        return;
                    }
                    if (countTruongNhom(nhomDatn.getMaNhom()) > 1) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " đang nhiều hơn 1 trưởng nhóm");
                        return;
                    }
                    if (!sinhVien.getChucVu().isEmpty()) {
//                        if (sinhVien.getGvhd().isEmpty()) {
//                            response.setStatus(false);
//                            response.setMessage("Nhóm " + nhomDatn.getMaNhom() + " chưa có giảng viên hướng dẫn");
//                            return;
//                        }
                        nhomDatn.setTruongNhomId(sv.getId());
                        if(giangVien != null) {
                            nhomDatn.setGiangVienId(giangVien.getId());
                        }
                        if (nhomDatn.getTrangThai() == TrangThaiNhom.MOI_THANH_LAP) {
                            nhomDatn.setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
                        }
                    }
                    sv.setNhomId(nhomDatn.getId());
                    SinhVien truongNhom = mapSinhVien.get(nhomDatn.getId());
                    if (!sv.getMaSinhVien().equals(truongNhom.getMaSinhVien())) {
                        sv.setMonDatnId(truongNhom.getMonDatnId());
                    }
                }
                if (!sinhVien.getMaNhom().isEmpty() && sinhVien.getMaNhom() != null && nhomDatn == null) {
                    if (countTruongNhom(sinhVien.getMaNhom()) == 0) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + sinhVien.getMaNhom() + " đang không có trưởng nhóm");
                        return;
                    }
                    if (countTruongNhom(sinhVien.getMaNhom()) > 1) {
                        response.setStatus(false);
                        response.setMessage("Nhóm " + sinhVien.getMaNhom() + " đang nhiều hơn 1 trưởng nhóm");
                        return;
                    }
                    if (!sinhVien.getChucVu().isEmpty()) {
//                        if (sinhVien.getGvhd().isEmpty()) {
//                            response.setStatus(false);
//                            response.setMessage("Nhóm " + sinhVien.getMaNhom() + " đang không có giảng viên hướng dẫn");
//                            return;
//                        }
                    }
                    mapSinhVienTaoNhom.put(sinhVien.getMaSinhVien(), sinhVien);
                    if (sinhVien.getChucVu().equalsIgnoreCase("x")) {
                        mapTruongNhomCreate.put(sinhVien.getMaNhom(), sinhVien);
                        listMaNhomCreate.put(sinhVien.getMaNhom(), sinhVien.getMaNhom());
                    }
                }
                if (sinhVien.getMaNhom().isEmpty()) {
                    sv.setNhomId(null);
                }
                listSinhVienEntity.put(sv.getId(), sv);
                if (nhomDatn != null) {
                    listNhomDatnEntity.put(nhomDatn.getId(), nhomDatn);
                }
            });
            if (response.getStatus() == true) {
                for (Map.Entry<String, SinhVien> item : listSinhVienEntity.entrySet()){
                    SinhVien sinhVien = item.getValue();
                    if(sinhVien.getNhomId() == null){
                        sinhVien.setMonDatnId(null);
                    }
                }
                ConcurrentMap<String, NhomDatn> listNhomDatnCreate = new ConcurrentHashMap<>();

                String maNhom = nhomDatnHelper.genMaNhomTuDong();
                if (maNhom == null) {
                    response.setStatus(false);
                    response.setMessage("Tạo nhóm thất bại");
                    return response;
                }
                String array[] = maNhom.split("_");
                Long count = Long.parseLong(array[1]);
                for (Map.Entry<String, String> item : listMaNhomCreate.entrySet()){
                    String maNhomCreate = item.getValue();
                    NhomDatn nhomDatn = new NhomDatn();
                    nhomDatn.setMaNhom(array[0] + "_" + count++);
                    nhomDatn.setCoSoId(coSoId);
                    nhomDatn.setDotDangKyId(dotDangKyId);
                    nhomDatn.setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
                    for (Map.Entry<String, CnCreateSinhVienTheoDotRequest> xx : mapTruongNhomCreate.entrySet()) {
                        if (xx.getValue().getChucVu().equalsIgnoreCase("x")) {
                            GiangVienHuongDan giangVien = mapGiangVien.get(mapTruongNhomCreate.get(maNhomCreate).getGvhd());
                            nhomDatn.setTruongNhomId(mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien()).getId());
                            if(giangVien != null) {
                                nhomDatn.setGiangVienId(giangVien.getId());
                            }
                        }
                    }
                    listNhomDatnCreate.put(nhomDatn.getTruongNhomId(), nhomDatn);
                }
                cnNhomDatnRepository.saveAll(listNhomDatnCreate.values());
                for (Map.Entry<String, String> item : listMaNhomCreate.entrySet()){
                    String maNhomCreate = item.getValue();
                    for (Map.Entry<String, CnCreateSinhVienTheoDotRequest> xx : mapSinhVienTaoNhom.entrySet()) {
                        SinhVien truongNhomCreate = mapSinhVien.get(mapTruongNhomCreate.get(maNhomCreate).getMaSinhVien());
                        truongNhomCreate.setMonDatnId(truongNhomCreate.getMonChuongTrinhId());
                        truongNhomCreate.setNhomId(listNhomDatnCreate.get(truongNhomCreate.getId()).getId());
                        if (xx.getValue().getMaNhom().equals(mapTruongNhomCreate.get(maNhomCreate).getMaNhom())) {
                            mapSinhVien.get(xx.getValue().getMaSinhVien()).setNhomId(truongNhomCreate.getNhomId());
                            mapSinhVien.get(xx.getValue().getMaSinhVien()).setMonDatnId(truongNhomCreate.getMonDatnId());
                            listSinhVienEntity.put(mapSinhVien.get(xx.getValue().getMaSinhVien()).getId(), mapSinhVien.get(xx.getValue().getMaSinhVien()));
                        }
                        listSinhVienEntity.put(truongNhomCreate.getId(), truongNhomCreate);
                    }
                }
                cnSinhVienRepository.saveAll(listSinhVienEntity.values());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi hệ thống");
            return response;
        }
        return response;
    }

    public boolean countSoLuongThanhVien(String maNhom) {
        AtomicInteger count = new AtomicInteger(0);
        sinhVienList.parallelStream().forEach(sinhVien -> {
            if (sinhVien.getMaNhom().equals(maNhom)) {
                count.set(count.get() + 1);
            }
        });
        if (count.get() > 7) {
            return true;
        }
        return false;
    }

    public int countTruongNhom(String maNhom) {
        AtomicInteger count = new AtomicInteger(0);
        sinhVienList.parallelStream().forEach(sinhVien -> {
            if (sinhVien.getMaNhom().equals(maNhom) && sinhVien.getChucVu().equalsIgnoreCase("x")) {
                count.set(count.get() + 1);
            }
        });
        return count.get();
    }

    public boolean countSoNhomGiangVienDangHuongDan(String tenTaiKhoan) {
        AtomicInteger count = new AtomicInteger(0);
        sinhVienList.parallelStream().forEach(sinhVien -> {
            if (sinhVien.getGvhd().equals(tenTaiKhoan) && sinhVien.getChucVu().equalsIgnoreCase("x")) {
                count.set(count.get() + 1);
            }
        });
        if (count.get() > mapGiangVien.get(tenTaiKhoan).getSoNhomHuongDanToiDa()) {
            return true;
        }
        return false;
    }

    public void addDataInMapSinhVien(ConcurrentHashMap<String, SinhVien> mapAll, String dotDangKyId, String coSoId, String idChuNhiem) {
        List<SinhVien> sinhVienList = cnSinhVienRepository.getALlSinhVienByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        List<SinhVien> truongNhomList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        List<SinhVien> truongNhomCuList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getAllPutMapSinhVien(mapAll, sinhVienList);
        getTruongNhomPutMapSinhVien(mapAll, truongNhomList);
        getTruongNhomCuPutMapSinhVien(mapAll, truongNhomCuList);
    }

    public void addDataInMapTruongNhomCu(ConcurrentHashMap<String, SinhVien> mapAll, String dotDangKyId, String coSoId, String idChuNhiem) {
        List<SinhVien> truongNhomCuList = cnSinhVienRepository.getALlTruongNhomByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getTruongNhomCuPutMapSinhVien(mapAll, truongNhomCuList);
    }

    public void addDataInMapNhomDatn(ConcurrentHashMap<String, NhomDatn> mapAll, String dotDangKyId, String coSoId, String idChuNhiem) {
        List<NhomDatn> nhomDatnList = cnNhomDatnRepository.getAllNhomDatnByIdChuNhiem(dotDangKyId, coSoId, idChuNhiem);
        getALlPutMapNhomDatn(mapAll, nhomDatnList);
    }

    public void addDataInMapGiangVien(ConcurrentHashMap<String, GiangVienHuongDan> mapAll, String dotDangKyId, String coSoId) {
        List<GiangVienHuongDan> giangVienHuongDanList = cnGiangVienHuongDanReponsitory.getAllGiangVienByIdCoSoAndByIdDotDangKy(dotDangKyId, coSoId);
        getALlPutMapGiangVien(mapAll, giangVienHuongDanList);
    }

    public void getAllPutMap(ConcurrentHashMap<String, String> mapSimple, List<SimpleEntityProj> getAllSimpleEntityProjs) {
        for (SimpleEntityProj simpleEntityProj : getAllSimpleEntityProjs) {
            mapSimple.put(simpleEntityProj.getName(), simpleEntityProj.getId());
        }
    }

    public void getAllPutMapSinhVien(ConcurrentHashMap<String, SinhVien> mapSimple, List<SinhVien> listSinhVien) {
        for (SinhVien xx : listSinhVien) {
            mapSimple.put(xx.getMaSinhVien(), xx);
        }
    }

    public void getTruongNhomPutMapSinhVien(ConcurrentHashMap<String, SinhVien> mapSimple, List<SinhVien> listTruongNhom) {
        for (SinhVien xx : listTruongNhom) {
            mapSimple.put(xx.getNhomId(), xx);
        }
    }

    public void getTruongNhomCuPutMapSinhVien(ConcurrentHashMap<String, SinhVien> mapSimple, List<SinhVien> listTruongNhom) {
        for (SinhVien xx : listTruongNhom) {
            mapSimple.put(xx.getMaSinhVien(), xx);
        }
    }

    public void getALlPutMapNhomDatn(ConcurrentHashMap<String, NhomDatn> mapSimple, List<NhomDatn> listNhomDatn) {
        for (NhomDatn xx : listNhomDatn) {
            mapSimple.put(xx.getMaNhom(), xx);
        }
    }

    public void getALlPutMapGiangVien(ConcurrentHashMap<String, GiangVienHuongDan> mapSimple, List<GiangVienHuongDan> listGiangVien) {
        for (GiangVienHuongDan xx : listGiangVien) {
            mapSimple.put(xx.getTenTaiKhoan(), xx);
        }
    }

}
