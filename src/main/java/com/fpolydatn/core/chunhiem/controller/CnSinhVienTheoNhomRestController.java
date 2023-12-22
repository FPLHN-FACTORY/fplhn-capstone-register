package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindGvhdRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.core.common.CommonHelper;
import com.fpolydatn.core.common.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chu-nhiem/sinh-vien")
public class CnSinhVienTheoNhomRestController extends BaseController {

    @Autowired
    private CnSinhVienTheoNhomService sinhVienTheoNhomService;

    @Autowired
    private CnGiangVienHuongDanService cnGiangVienHuongDanService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private CnDotDangKyService cnDotDangKyService;

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

    @PutMapping("/doi-ten-de-tai")
    public ResponseObject getListSinhVienNoGroup(@RequestParam("idNhom") String idNhom,
                                                 @RequestParam("tenDeTai") String tenDetai) {
        return new ResponseObject(sinhVienTheoNhomService.doiTenDeTai(idNhom, tenDetai));
    }

    @GetMapping("/listGvhd")
    public ResponseObject getListGvhd(final CnFindGvhdRequest req) {
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(cnGiangVienHuongDanService.getListGvhd(req));
    }

    @GetMapping("/history-detail")
    public ResponseObject getAllHistoryDetail(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
            @RequestParam(name = "maNhom", defaultValue = "") String maNhom,
            @RequestParam(name = "dotDangKyId", defaultValue = "") String dotDangKyId
    ) {
        List<LoggerObject> list = new ArrayList<>();
        if (maNhom != null && maNhom.trim().length() != 0) {
            LoggerObject object = new LoggerObject();
            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(cnDotDangKyService.findById(dotDangKyId).getTenDotDangKy());

            object.setTenNhom(maNhom);
            object.setPathFile(pathFile);
            list = writerFileCSV.readFileCSVLuong(object);

            List<LoggerObject> pagedSanPhamList = list.stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());
            return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
        }
        return new ResponseObject(new PageImpl<>(list, PageRequest.of(page, size), list.size()));
    }

}
