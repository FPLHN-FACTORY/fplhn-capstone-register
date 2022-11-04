package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.excel.CnNhomDatnExport;
import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotExport;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.MocThoiGianCommon;
import com.fpolydatn.util.NhomDatnHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    private List<CnNhomDatnResponse> list;

    @Override
    public PageableObject<CnNhomDatnResponse> search(CnFindNhomDatnRequest request, String idChuNhiem) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnNhomDatnResponse> res = cnNhomDatnRepository.findNhomDatn(request, pageable, idChuNhiem);
        list = cnNhomDatnRepository.findNhomDatnExport(request, idChuNhiem);
        return new PageableObject(res);
    }

    @Override
    public boolean chotDeTai(String id) {
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM);
        }
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
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM);
        }
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (nhomDatnCheck.get().getTrangThai() != TrangThaiNhom.MOI_THANH_LAP) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        nhomDatnCheck.get().setTrangThai(TrangThaiNhom.CHU_NHIEM_DA_XAC_NHAN);
        cnNhomDatnRepository.save(nhomDatnCheck.get());
        return true;
    }

    @Override
    public boolean tuChoiChotDeTai(String id, String nhanXet) {
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM);
        }
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
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM);
        }
        Optional<NhomDatn> nhomDatnCheck = cnNhomDatnRepository.findById(id);
        if (Objects.isNull(nhomDatnCheck)) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        cnNhomDatnRepository.delete(nhomDatnCheck.get());
        List<SinhVien> listSinhVien = cnSinhVienRepository.getAllSinhVien(id, commonHelper.getDotDangKyHienTai());
        for (SinhVien xx : listSinhVien) {
            cnSinhVienRepository.deleteSinhVienOutGroup(xx.getMaSinhVien(), commonHelper.getDotDangKyHienTai());
        }
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
        boolean check = mocThoiGianCommon.checkMocThoiGianChuNhiem();
        if (!check) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_CHU_NHIEM);
        }
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
        nhomDatn.setGiangVienId(idGiangVien);
        nhomDatn.setMaNhom(nhomDatnHelper.genMaNhomTuDong());
        nhomDatn.setMatKhau(cnCreateNhomDatnRequest.getMatKhau());
        nhomDatn.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        nhomDatn.setCoSoId(cnCreateNhomDatnRequest.getCoSoId());
        nhomDatn.setDotDangKyId(cnCreateNhomDatnRequest.getDotDangKyId());
        nhomDatn.setId(cnNhomDatnRepository.save(nhomDatn).getId());
        cnNhomDatnRepository.setNhomForTruongNhom(nhomDatn.getId(), monDatnId, idTruongNhom, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public List<String> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req) {
        return cnSinhVienRepository.getStringListSinhVienNoGroup(req);
    }

    @Override
    public void DTSinhVienExport(HttpServletResponse response, String typeExcel, CnFindNhomDatnRequest request) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDatn" + currentDateTime + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        CnNhomDatnExport cnNhomDatnExport = new CnNhomDatnExport(list);
        cnNhomDatnExport.exportData(response);
    }

    @Override
    public boolean checkHanChuNhiem() {
        return mocThoiGianCommon.checkMocThoiGianChuNhiem();
    }

}
