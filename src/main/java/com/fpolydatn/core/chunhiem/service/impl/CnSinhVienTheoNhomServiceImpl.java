package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.repository.CnDotDangKyRepository;
import com.fpolydatn.core.chunhiem.repository.CnGiangVienHuongDanReponsitory;
import com.fpolydatn.core.chunhiem.repository.CnMonDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.repository.CnSinhVienRepository;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.MocThoiGianCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CnSinhVienTheoNhomServiceImpl implements CnSinhVienTheoNhomService {

    @Autowired
    private CnSinhVienRepository cnSinhVienTheoNhomRepository;

    @Autowired
    private CnGiangVienHuongDanReponsitory cnGiangVienHuongDanReponsitory;

    @Autowired
    private CnDotDangKyRepository cnDotDangKyRepository;

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    @Autowired
    private CnMonDatnRepository cnMonDatnRepository;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private MocThoiGianCommon mocThoiGianCommon;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Override
    public PageableObject<CnSinhVienTheoNhomResponse> searchByIdNhom(final CnFindSinhVienTheoNhomRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CnSinhVienTheoNhomResponse> res = cnSinhVienTheoNhomRepository.searchSinhVien(request, pageable);
        return new PageableObject<>(res);
    }

    @Override
    public boolean addSinhVienTheoNhom(String idNhom, String maSinhViens) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        String listMaSinhVien[] = maSinhViens.split("-");
        int soSinhVienDangLuaChon = listMaSinhVien.length;
        int soThanhVienTrongNhom = cnNhomDatnRepository.getSoLuongThanhVienNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idTruongNhom = cnNhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(idTruongNhom, commonHelper.getDotDangKyHienTai());
        String idNhomMonDatn = cnMonDatnRepository.findIdNhomMonDatn(idMonDatn);
        if ((soThanhVienTrongNhom + soSinhVienDangLuaChon) > 7) {
            throw new RestApiException(Message.VUOT_QUA_SO_LUONG_CHO_PHEP);
        }
        for (String maSinhVien : listMaSinhVien) {
            String idMonChuongTrinh = cnSinhVienTheoNhomRepository.findIdMonChuongTrinh(maSinhVien, commonHelper.getDotDangKyHienTai());
            String check = cnMonDatnRepository.checkMonChuongTrinh(idNhomMonDatn, idMonChuongTrinh);
            if (check == null) {
                throw new RestApiException(Message.MON_KHONG_TUONG_DUONG);
            }
            String idSinhVien = cnSinhVienTheoNhomRepository.getIdSinhVienByMaSinhVien(maSinhVien, commonHelper.getDotDangKyHienTai());
            cnSinhVienTheoNhomRepository.addSinhVienInGroup(idNhom, maSinhVien, idMonDatn, commonHelper.getDotDangKyHienTai());
        }
        return true;
    }

    @Override
    public Integer getSizeListSinhVien(String idNhom, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            return null;
        }
        return cnSinhVienTheoNhomRepository.countListSinhVien(idNhom, coSoId, commonHelper.getDotDangKyHienTai());
    }

    @Override
    public boolean deleteFromIdSinhVien(String idNhom, String maSinhVien, String coSoId) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        cnSinhVienTheoNhomRepository.deleteSinhVienOutGroup(maSinhVien, commonHelper.getDotDangKyHienTai());
        if (getSizeListSinhVien(idNhom, coSoId) == 0) {
            cnNhomDatnRepository.deleteById(idNhom);
        }
        return true;
    }

    @Override
    public boolean addGvhd(String tenTaiKhoan, String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        String idGiangVien = cnGiangVienHuongDanReponsitory.getIdFromGiangVienId(tenTaiKhoan, commonHelper.getDotDangKyHienTai());
        if (idGiangVien == null) {
            throw new RestApiException(Message.GIANG_VIEN_HUONG_DAN_NOT_EXIST);
        }
        if (cnNhomDatnRepository.getSoNhomGiangVienHuongDan(idGiangVien, commonHelper.getDotDangKyHienTai()) ==
                commonHelper.getSoNhomGiangVienDangHuongDan(idGiangVien)) {
            throw new RestApiException(Message.GIANG_VIEN_DA_HUONG_DAN_DU_SO_NHOM);
        }
        String idTruongNhom = cnNhomDatnRepository.findIDTruongNhom(idNhom, commonHelper.getDotDangKyHienTai());
        String idMonDatn = cnSinhVienTheoNhomRepository.findIdMonDatnByIdCaptain(idTruongNhom, commonHelper.getDotDangKyHienTai());
        String check = cnGiangVienHuongDanReponsitory.checkIdGiangVienAndMonDatn(idGiangVien, idMonDatn, commonHelper.getDotDangKyHienTai());
        if (check == null) {
            throw new RestApiException(Message.GIANG_VIEN_KHONG_DAY_MON_NAY);
        }
        cnNhomDatnRepository.addGvhd(idGiangVien, idNhom, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public boolean changeCaptain(String idCaptain, String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        cnNhomDatnRepository.changeCaptainByTruongNhomId(idCaptain, idNhom, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public boolean changeCaptainToOther(String maSinhVien, String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        String idCapTainOld = cnSinhVienTheoNhomRepository.getIdByMaSinhVienAndIdNhom(maSinhVien, idNhom, commonHelper.getDotDangKyHienTai());
        String idCapTainNew = cnSinhVienTheoNhomRepository.getListSinhVienNotCaptain(idCapTainOld, idNhom, commonHelper.getDotDangKyHienTai()).get(0).getId();
        cnNhomDatnRepository.changeCaptainByTruongNhomId(idCapTainNew, idNhom, commonHelper.getDotDangKyHienTai());
        return true;
    }

    @Override
    public SinhVien getOldCaptain(String idGroup) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idGroup);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            return null;
        }
        String idOldCaptain = cnNhomDatnRepository.findIDTruongNhom(idGroup, commonHelper.getDotDangKyHienTai());
        Optional<SinhVien> captain = cnSinhVienTheoNhomRepository.findById(idOldCaptain);
        return captain.get();
    }

    @Override
    public List<SinhVien> getStudentNotCaptain(String idCaptain, String idGroup) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idGroup);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            return null;
        }
        List<SinhVien> listStudentNotCaptain = cnSinhVienTheoNhomRepository.getListSinhVienNotCaptain(idCaptain, idGroup, commonHelper.getDotDangKyHienTai());
        return listStudentNotCaptain;
    }

    @Override
    public List<DotDangKy> getListDotDangKy() {
        return cnDotDangKyRepository.findAll();
    }

    @Override
    public List<CnSinhVienSearchResponse> getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req, String idChuNhiem) {
        String idNhom = req.getIdNhom();
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            return null;
        }
        Optional<NhomDatn> nhomDatn = cnNhomDatnRepository.findById(idNhom);
        Optional<SinhVien> truongNhom = cnSinhVienTheoNhomRepository.findById(nhomDatn.get().getTruongNhomId());
        Optional<MonDatn> nhomMonDatn = cnMonDatnRepository.findById(truongNhom.get().getMonDatnId());
        req.setNhomMonDatnId(nhomMonDatn.get().getNhomMonDatnId());
        return cnSinhVienTheoNhomRepository.getListSinhVienNoGroup(req, idChuNhiem);
    }

    @Override
    public NhomDatn doiTenDeTai(String idNhom, String tenDeTai) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()){
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if(!checkDotDangKy(nhomDatnFind.get().getDotDangKyId())){
            throw new RestApiException(Message.DOT_DANG_KY_KHONG_PHAI_HIEN_TAI_KHONG_DUOC_THAO_TAC);
        }
        if(tenDeTai.trim().length() > 255){
            throw new RestApiException(Message.DO_DAI_KHONG_QUA_255_KY_TU);
        }
        nhomDatnFind.get().setTenDeTai(tenDeTai);
        return cnNhomDatnRepository.save(nhomDatnFind.get());
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

    @Override
    public Boolean checkDotDangKyHienTai(String idNhom) {
        Optional<NhomDatn> nhomDatnFind = cnNhomDatnRepository.findById(idNhom);
        if(!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        return checkDotDangKy(nhomDatnFind.get().getDotDangKyId());
    }

}