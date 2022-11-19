package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.service.SvGiangVienHuongDanService;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SonPT
 */

@RestController
@RequestMapping("/api/sinh-vien")
public class SvNhomDatnRestController extends BaseController {

    @Autowired
    private SvNhomDatnService svNhomDatnService;

    @Autowired
    private SvGiangVienHuongDanService svGiangVienHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping("/search-giang-vien")
    public ResponseObject getGiangVienHuongDan(@RequestParam(defaultValue = "", required = true) String idMonDatn) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(svGiangVienHuongDanService.getGvhdByMonDatnId(idMonDatn, coSoId));
    }

    @GetMapping("/search")
    public ResponseObject searchNhomDatn(final SvFindNhomDatnRequest req) {
        String idUser = session.getUserId();
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req, idUser);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("id") String id) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        List<SvShowSinhVienTheoNhomResponse> listDanhSachSinhVien = svNhomDatnService.showDanhSachSinhVien(id,
                coSoId, dotDangKyId);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @PostMapping("/add")
    public ResponseObject addSinhVien(@RequestBody SvAddSinhVienRequest req) {
        req.setIdUser(session.getUserId());
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(svNhomDatnService.addSinhVienTheoNhom(req));
    }

    @PostMapping
    public ResponseObject createNhomDatn(@RequestBody SvCreateNhomDatnRequest svNhomDatnRequest) {
        String coSoid = session.getCoSoId();
        svNhomDatnRequest.setCoSoId(coSoid);
        svNhomDatnRequest.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(svNhomDatnService.createNhomDatn(svNhomDatnRequest, session.getUserId()));
    }

    @PutMapping("/update-ten-de-tai")
    public ResponseObject updateTenDeTai(@RequestBody SvUpdateTenDeTaiRequest svUpdateTenDeTaiRequest) {
        svUpdateTenDeTaiRequest.setIdUser(session.getUserId());
        return new ResponseObject(svNhomDatnService.updateTenDeTai(svUpdateTenDeTaiRequest));
    }

    @PutMapping("/roi-nhom")
    public ResponseObject roiNhom() {
        String idUser = session.getUserId();
        String coSoid = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        return new ResponseObject(svNhomDatnService.roiNhom(idUser, coSoid, dotDangKyId));
    }

    @PutMapping("/chuyen-quyen-truong-nhom")
    public ResponseObject chuyenQuyenTruongNhom(@RequestParam("idTruongNhomMoi") String idTruongNhomMoi) {
        return new ResponseObject(svNhomDatnService.chuyenQuyenTruongNhom(session.getUserId(), idTruongNhomMoi));
    }

    @PutMapping("/chon-giang-vien")
    public ResponseObject chonGvhd(@RequestParam String idGiangVien) {
        String userId = session.getUserId();
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        return new ResponseObject(svNhomDatnService.chonGvhd(userId, idGiangVien, coSoId, dotDangKyId));
    }

}
