package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.core.giangvien.service.GvSinhVienService;
import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.core.common.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thangdt
 */

@RestController
@RequestMapping(value = "/api/giang-vien/nhom-datn")
public class GvNhomDatnRestController extends BaseController {

    @Autowired
    private GvNhomDatnService nhomDatnService;

    @Autowired
    private GvSinhVienService sinhVienService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping()
    public ResponseObject detailToTalSinhVien(final SvFindSinhVienRequest request) {
        request.setCoSoId(session.getCoSoId());
        return new ResponseObject(sinhVienService.findByNhomId(request));
    }

    @GetMapping("/search")
    public ResponseObject viewSeachNhomDatn(final GvFindNhomDatnRequest request) {
        String idGiangVien = session.getUserId();
        request.setCoSoId(session.getCoSoId());
        request.setGiangVienHDId(idGiangVien);
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("/{maNhom}")
    public ResponseObject detailNhomDatn(@PathVariable("maNhom") String maNhom) {
        NhomDatn nhomDatnResponse = nhomDatnService.findById(maNhom);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/update-ten-de-tai/{maNhom}")
    public ResponseObject updateTenDeTaiNhomDatn(@PathVariable("maNhom") String maNhom, @RequestBody GvUpdateTenDeTaiRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.updateTenDeTai(request);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/phe-duyet-nhom")
    public ResponseObject pheDuyetNhomDatn(final GvChotNhomDatnRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.pheDuyetNhomDatn(request);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/phe-duyet-de-tai-nhom")
    public ResponseObject pheDuyetDeTaiNhomDatn(final GvChotNhomDatnRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.chotDeTaiNhomDatn(request);
        return new ResponseObject(nhomDatnResponse);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_GIANG_VIEN) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        List<LoggerObject> list = writerFileCSV.readFileCSV(loggerObject);
        List<LoggerObject> pagedSanPhamList = list.stream()
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
        List<LoggerObject> list = new ArrayList<>();
            LoggerObject object = new LoggerObject();
            String tenDotHienTai = writerFileCSV.tenDotDangKyHienTai();
            String pathTenDot = "";
            if (dotDangKyId.trim().length() != 0) {
                pathTenDot = tenDotHienTai;
            } else {
                pathTenDot = writerFileCSV.findDotDangKyById(dotDangKyId).getTenDotDangKy();
            }
            String pathFile = writerFileCSV.getPathFileLuongNhomDatn(pathTenDot);

            object.setTenNhom(maNhom);
            object.setPathFile(pathFile);
            list = writerFileCSV.readFileCSVLuong(object);

            List<LoggerObject> pagedSanPhamList = list.stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());
            return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_GIANG_VIEN) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);

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
