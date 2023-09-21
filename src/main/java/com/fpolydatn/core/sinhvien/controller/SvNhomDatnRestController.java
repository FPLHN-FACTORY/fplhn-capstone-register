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
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private SvExportFileWordService svExportFileWordService;

    private CsrfTokenRepository csrfTokenRepository;

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
    public ResponseObject addSinhVien(HttpServletRequest requests, @RequestBody SvAddSinhVienRequest req) {
        req.setIdUser(session.getUserId());
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        return new ResponseObject(svNhomDatnService.addSinhVienTheoNhom(req));
    }

    @PostMapping
    public ResponseObject createNhomDatn(HttpServletRequest requests, @RequestBody SvCreateNhomDatnRequest svNhomDatnRequest) {
        String coSoid = session.getCoSoId();
        svNhomDatnRequest.setCoSoId(coSoid);
        svNhomDatnRequest.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        return new ResponseObject(svNhomDatnService.createNhomDatn(svNhomDatnRequest, session.getUserId()));
    }

    @PutMapping("/update-ten-de-tai")
    public ResponseObject updateTenDeTai(HttpServletRequest requests, @RequestBody SvUpdateTenDeTaiRequest svUpdateTenDeTaiRequest) {
        svUpdateTenDeTaiRequest.setIdUser(session.getUserId());
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        return new ResponseObject(svNhomDatnService.updateTenDeTai(svUpdateTenDeTaiRequest));
    }

    @PutMapping("/roi-nhom")
    public ResponseObject roiNhom(HttpServletRequest requests) {
        String idUser = session.getUserId();
        String coSoid = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        return new ResponseObject(svNhomDatnService.roiNhom(idUser, coSoid, dotDangKyId));
    }

    @PutMapping("/chuyen-quyen-truong-nhom")
    public ResponseObject chuyenQuyenTruongNhom(HttpServletRequest requests, @RequestParam("idTruongNhomMoi") String idTruongNhomMoi) {
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        return new ResponseObject(svNhomDatnService.chuyenQuyenTruongNhom(session.getUserId(), idTruongNhomMoi));
    }

    @PutMapping("/chon-giang-vien")
    public ResponseObject chonGvhd(HttpServletRequest requests, @RequestParam String idGiangVien) {
        String userId = session.getUserId();
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        return new ResponseObject(svNhomDatnService.chonGvhd(userId, idGiangVien, coSoId, dotDangKyId));
    }

    @GetMapping("/export-word")
    public ResponseEntity<ByteArrayResource> exportWord(HttpServletRequest requests) throws IOException {
        String idUser = session.getUserId();
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        byte[] fileBytes = svExportFileWordService.exportWord(idUser);

        ByteArrayResource resource = new ByteArrayResource(fileBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Mau-phieu-dang-ky-ten-DATN.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PutMapping("/update-ten-nhom")
    public ResponseObject doiTenNhom(HttpServletRequest requests,
                                     @RequestBody SvUpdateTenNhomRequest request) {
        CsrfToken csrfToken = (CsrfToken) requests.getAttribute(CsrfToken.class.getName());

        // Kiểm tra CSRF token ở đây
//        if (csrfToken == null || !csrfToken.getToken().equals(requests.getHeader("X-XSRF-TOKEN"))) {
//            throw new RestApiException("Không có quyền");
        // Token hợp lệ, tiếp tục xử lý yêu cầu
        // return ResponseEntity.ok("Yêu cầu hợp lệ!");
//        }
        if (csrfToken == null) {
            throw new RestApiException("Không có quyền");
        }
        request.setUserId(session.getUserId());
        request.setCoSoId(session.getCoSoId());
        request.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(svNhomDatnService.doiTenNhom(request));
    }

}

