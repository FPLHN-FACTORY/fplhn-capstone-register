package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.service.SvGiangVienHuongDanService;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;
import java.util.ArrayList;
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
    private SvSinhVienService svSinhVienService;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    private String idUser = "4d5eff02-6565-44eb-92f2-8beeb3687145";

    @GetMapping("/search-giang-vien")
    public ResponseObject getGiangVienHuongDan(@RequestParam(defaultValue = "", required = true) String idMonDatn) {
        return new ResponseObject(svGiangVienHuongDanService.getGiangVienHuongDan(idMonDatn));
    }

    @PutMapping("/{id}")
    public ResponseObject guiYeuCauPheDuyet(@PathVariable String id){
        return new ResponseObject(svNhomDatnService.guiYeuCauPheDuyet(id, idUser));
    }

    @GetMapping("/search")
    public ResponseObject searchNhomDatn(final SvFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setDotDangKyId(dotDangKyRepository.findDotDangKyID(convertDateToTimeStampSecond()));
        PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("id") String id) {
        List<SvShowSinhVienTheoNhomResponse> listDanhSachSinhVien = svNhomDatnService.showDanhSachSinhVien(id);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @PostMapping("/add")
    public ResponseObject addSinhVien(@RequestBody SvAddSinhVienRequest req) {
        req.setIdUser(idUser);
        return new ResponseObject(svNhomDatnService.addSinhVienTheoNhom(req));
    }

    @PostMapping
    public ResponseObject addNhomDatn(@RequestBody SvCreateNhomDatnRequest svNhomDatnRequest) {
        String coSoid = session.getCoSoId();
        svNhomDatnRequest.setCoSoId(coSoid);
        svNhomDatnRequest.setTruongNhomId(idUser);
        svNhomDatnRequest.setDotDangKyId(dotDangKyRepository.findDotDangKyID(convertDateToTimeStampSecond()));
        NhomDatn nhomDatn = svNhomDatnService.createNhomDatn(svNhomDatnRequest);
        svSinhVienService.saveNhomDatn(svNhomDatnRequest.getTruongNhomId(),nhomDatn.getId());
        return new ResponseObject(nhomDatn);
    }

    @PutMapping("/update-ten-de-tai")
    public ResponseObject updateTenDeTai(@RequestBody SvUpdateTenDeTaiRequest svUpdateTenDeTaiRequest) {
        svUpdateTenDeTaiRequest.setIdUser(idUser);
        return new ResponseObject(svNhomDatnService.updateTenDeTai(svUpdateTenDeTaiRequest));
    }

    @PutMapping("/roi-nhom")
    public ResponseObject roiNhom() {
        String idUser = "";
        return new ResponseObject(svNhomDatnService.roiNhom(idUser));
    }

}
