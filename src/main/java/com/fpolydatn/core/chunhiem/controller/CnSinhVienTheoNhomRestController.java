package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chu-nhiem/sinh-vien")
public class CnSinhVienTheoNhomRestController extends BaseController {
    @Autowired
    private CnSinhVienTheoNhomService sinhVienTheoNhomService;

    @Autowired
    private CnGiangVienHuongDanService cnGiangVienHuongDanService;

    @Autowired
    private CommonHelper commonHelper;


    @DeleteMapping("")
    public ResponseObject updateSinhVienByNhom(@RequestParam("maSinhVien") String maSinhVien, @RequestParam("idNhom") String idNhom) {
        return new ResponseObject(sinhVienTheoNhomService.deleteFromIdSinhVien(idNhom, maSinhVien, session.getCoSoId()));
    }


    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("id") String idNhom, @RequestParam("maSinhVien") String maSinhVien) {
        return new ResponseObject(sinhVienTheoNhomService.addSinhVienTheoNhom(idNhom, maSinhVien));
    }

    @PostMapping("/addGvhd")
    public ResponseObject addGvhd(@RequestParam("giangVienId") String giangVienId, @RequestParam("idNhom") String idNhom) {
        return new ResponseObject(sinhVienTheoNhomService.addGvhd(giangVienId, idNhom));
    }

    @PutMapping("/changeCaptain")
    public ResponseObject changeCaptain(@RequestParam("idCaptain") String idCaptain, @RequestParam("idGroup") String idGroup) {
        return new ResponseObject(sinhVienTheoNhomService.changeCaptain(idCaptain, idGroup));
    }

    @PutMapping("/changeCaptainToOther")
    public ResponseObject changeCaptainToOther(@RequestParam("idCaptain") String idCaptain, @RequestParam("idGroup") String idGroup) {
        return new ResponseObject(sinhVienTheoNhomService.changeCaptainToOther(idCaptain, idGroup));
    }

    @GetMapping("/listStudentNotCaptain")
    public ResponseObject getListStudentNotCaptain(@RequestParam("idGroup") String idGroup) {
        SinhVien captain = sinhVienTheoNhomService.getOldCaptain(idGroup);
        return new ResponseObject(sinhVienTheoNhomService.getStudentNotCaptain(captain.getId(), idGroup));
    }

    @GetMapping("/oldCaptain")
    public ResponseObject getOldCaptain(@RequestParam("idGroup") String idGroup) {
        return new ResponseObject(sinhVienTheoNhomService.getOldCaptain(idGroup));
    }

    @GetMapping("/listNhomDatnNoGroup")
    public ResponseObject getListSinhVienNoGroup(final CnFindSinhVienNoGroupRequest req, String idChuNhiem) {
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(sinhVienTheoNhomService.getListSinhVienNoGroup(req, idChuNhiem));
    }

    @GetMapping("/listGvhd")
    public ResponseObject getListGvhd(final CnFindGvhdRequest req) {
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(cnGiangVienHuongDanService.getListGvhd(req));
    }

}
