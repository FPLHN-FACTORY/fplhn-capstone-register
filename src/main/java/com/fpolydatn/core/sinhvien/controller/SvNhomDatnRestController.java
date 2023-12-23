package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.sinhvien.model.request.SvAddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvCreateNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenDeTaiRequest;
import com.fpolydatn.core.sinhvien.model.request.SvUpdateTenNhomRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.service.SvExportFileWordService;
import com.fpolydatn.core.sinhvien.service.SvGiangVienHuongDanService;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.core.common.log.LogObject;
import com.fpolydatn.core.common.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SvExportFileWordService svExportFileWordService;

    @Autowired
    private WriterFileCSV writerFileCSV;

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
        req.setDotDangKyId(commonHelper.getIdDotDangKyHienTai());
        PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req, idUser);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("id") String id) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
        List<SvShowSinhVienTheoNhomResponse> listDanhSachSinhVien = svNhomDatnService.showDanhSachSinhVien(id,
                coSoId, dotDangKyId);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @PostMapping("/add")
    public ResponseObject addSinhVien(@RequestBody SvAddSinhVienRequest req) {
        req.setIdUser(session.getUserId());
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getIdDotDangKyHienTai());
        return new ResponseObject(svNhomDatnService.addSinhVienTheoNhom(req));
    }

    @PostMapping
    public ResponseObject createNhomDatn(@RequestBody SvCreateNhomDatnRequest svNhomDatnRequest) {
        String coSoid = session.getCoSoId();
        svNhomDatnRequest.setCoSoId(coSoid);
        svNhomDatnRequest.setDotDangKyId(commonHelper.getIdDotDangKyHienTai());
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
        String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
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
        String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
        return new ResponseObject(svNhomDatnService.chonGvhd(userId, idGiangVien, coSoId, dotDangKyId));
    }

    @GetMapping("/export-word")
    public ResponseEntity<ByteArrayResource> exportWord() throws IOException {
        String idUser = session.getUserId();
        byte[] fileBytes = svExportFileWordService.exportWord(idUser);

        ByteArrayResource resource = new ByteArrayResource(fileBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Mau-phieu-dang-ky-ten-DATN.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PutMapping("/update-ten-nhom")
    public ResponseObject doiTenNhom(@RequestBody SvUpdateTenNhomRequest request) {
        request.setUserId(session.getUserId());
        request.setCoSoId(session.getCoSoId());
        request.setDotDangKyId(commonHelper.getIdDotDangKyHienTai());
        return new ResponseObject(svNhomDatnService.doiTenNhom(request));
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
            @RequestParam(name = "maNhom", defaultValue = "") String maNhom
    ) {
        List<LogObject> list = new ArrayList<>();
        if (maNhom != null && maNhom.trim().length() != 0) {
            LogObject object = new LogObject();
            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(writerFileCSV.tenDotDangKyHienTai());
            object.setTenNhom(maNhom);
            object.setPathFile(pathFile);
            list = writerFileCSV.readFileCSVLuong(object);
            List<LogObject> pagedSanPhamList = list.stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());
            return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
        }
        return new ResponseObject(new PageImpl<>(list, PageRequest.of(page, size), list.size()));
    }

}
