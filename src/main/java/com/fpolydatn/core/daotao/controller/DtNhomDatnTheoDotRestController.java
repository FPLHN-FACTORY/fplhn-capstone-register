package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.core.common.log.LogObject;
import com.fpolydatn.core.common.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dao-tao/nhom-datn")
public class DtNhomDatnTheoDotRestController extends BaseController {
    @Autowired
    private DtNhomDatnTheoDotService dtNhomDatnTheoDotService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("keyword") String id) {
        String coSoId = session.getCoSoId();
        List<DtSinhVienResponse> listDanhSachSinhVien = dtNhomDatnTheoDotService.showDanhSachSinhVien(id, coSoId);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @GetMapping("/search")
    public ResponseObject viewNhomDatn(final FindDtNhomDatnTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtNhomDatnTheoDotResponse> listNhomDatn = dtNhomDatnTheoDotService.findAllNhomDatn(req);
        return new ResponseObject(listNhomDatn);
    }

    @DeleteMapping("/xoa-nhom")
    public ResponseObject deleteNhomDatn(@RequestBody List<String> listIdNhom) {
        return new ResponseObject(dtNhomDatnTheoDotService.deleteNhomDatn(listIdNhom));
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, FindDtNhomDatnTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        if (request != null && (request.getDotDangKyId() == null || request.getDotDangKyId().trim().length() == 0)) {
            request.setDotDangKyId(commonHelper.getIdDotDangKyHienTai());
        }
        dtNhomDatnTheoDotService.DTNhomDatnExport(response, typeExcel, request);
    }

    @GetMapping("/export-excel")
    public void exportEcel(HttpServletResponse response, FindDtNhomDatnTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        if (request != null && (request.getDotDangKyId() == null || request.getDotDangKyId().trim().length() == 0)) {
            request.setDotDangKyId(commonHelper.getIdDotDangKyHienTai());
        }
        dtNhomDatnTheoDotService.DTNhomDatnSinhVienExport(response, request);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                idDotDangKy = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        String pathTenDot = "";
        if (idDotDangKy.trim().length() == 0) {
            String tenDotHienTai = writerFileCSV.tenDotDangKyHienTai();
            pathTenDot = tenDotHienTai;
        } else {
            pathTenDot = writerFileCSV.findDotDangKyById(idDotDangKy).getTenDotDangKy();
        }
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(pathTenDot) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
        LogObject logObject = new LogObject();
        logObject.setPathFile(pathFile);
        List<LogObject> list = writerFileCSV.readFileCSV(logObject);
        Collections.reverse(list);
        List<LogObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }

    @GetMapping("/history-detail")
    public ResponseObject getAllHistoryDetail(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
            @RequestParam(name = "maNhom", defaultValue = "") String maNhom,
            @RequestParam(name = "dotDangKyId", defaultValue = "") String dotDangKyId
    ) {
        if (dotDangKyId.equals("")) {
            String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                dotDangKyId = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        List<LogObject> list = new ArrayList<>();
        if (maNhom != null) {
            LogObject object = new LogObject();

            String pathTenDot = "";
            if (dotDangKyId.trim().length() == 0) {
                String tenDotHienTai = writerFileCSV.tenDotDangKyHienTai();
                pathTenDot = tenDotHienTai;
            } else {
                pathTenDot = writerFileCSV.findDotDangKyById(dotDangKyId).getTenDotDangKy();
            }
            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(pathTenDot);

            object.setTenNhom(maNhom);
            object.setPathFile(pathFile);
            list = writerFileCSV.readFileCSVLuong(object);
            Collections.reverse(list);
            List<LogObject> pagedSanPhamList = list.stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());
            return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
        }
        return new ResponseObject(new PageImpl<>(list, PageRequest.of(page, size), list.size()));
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy
    ) throws IOException {
        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                idDotDangKy = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);

        File file = new File(pathFile);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/download-log-luong")
    public ResponseEntity<Resource> downloadCsvLuong(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy
    ) throws IOException {
        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                idDotDangKy = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        String pathFile = writerFileCSV.getPathFileLuongNhomDatn(dotDangKyService.findById(idDotDangKy).getTenDotDangKy());

        File file = new File(pathFile);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
