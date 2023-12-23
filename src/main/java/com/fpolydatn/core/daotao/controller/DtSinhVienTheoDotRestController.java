package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateSinhVienRequest;
import com.fpolydatn.core.daotao.model.request.DtLoaiSinhVienKhongDatRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtLoaiSinhVienResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/dao-tao/sinh-vien")
public class DtSinhVienTheoDotRestController extends BaseController {
    @Autowired
    private DtSinhVienTheoDotService sinhVienService;

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping
    public ResponseObject searchStudent(final FindDtSinhVienTheoDotRequest req) {
        req.setCoSoId(session.getCoSoId());
        PageableObject<DtSinhVienTheoDotResponse> listSinhVien = sinhVienService.searchStudent(req);
        return new ResponseObject(listSinhVien);
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        sinhVienService.DTSinhVienExport(response, typeExcel, request);
    }

    @GetMapping("/export-mau")
    public void exportMauExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, FindDtSinhVienTheoDotRequest request) throws Exception {
        request.setCoSoId(session.getCoSoId());
        sinhVienService.MauImportExcel(response, typeExcel, request);
    }

    @PostMapping("/import/{dotDangKyId}")
    public ResponseObject importEcel(@RequestParam("file") MultipartFile file, @PathVariable("dotDangKyId") String dotDangKyId) throws IOException {
        String coSoId = session.getCoSoId();
        DtLoaiSinhVienResponse response = sinhVienService.DtSinhVienImport(file, coSoId, dotDangKyId);
        return new ResponseObject(response);
    }

    @PostMapping("/loai-sinh-vien/{dotDangKyId}")
    public ResponseObject loaiSinhVienKhongDat(@ModelAttribute DtLoaiSinhVienKhongDatRequest request, @PathVariable("dotDangKyId") String dotDangKyId) {
        request.setCoSoId(session.getCoSoId());
        request.setDotDangKyId(dotDangKyId);
        DtLoaiSinhVienResponse response = sinhVienService.importSinhVienKhongDuDieukien(request);
        return new ResponseObject(response);
    }

    @GetMapping("/search/{id}")
    public ResponseObject searchStudentById(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
        return new ResponseObject(sinhVienService.searchStudentById(id));
    }

    @PutMapping("/loai-sv/{id}")
    public ResponseObject loaiSinhVien(@PathVariable("id") String id) {
        return new ResponseObject(sinhVienService.loaiSinhVien(id));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getIdDotDangKyHienTai();
        return new ResponseObject(sinhVienService.delete(id, coSoId, dotDangKyId));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateSinhVienTheoDotRequest updateSinhVienTheoDotRequest) {
        updateSinhVienTheoDotRequest.setId(id);
        String coSoId = session.getCoSoId();
        updateSinhVienTheoDotRequest.setCoSoId(coSoId);
        return new ResponseObject(sinhVienService.update(updateSinhVienTheoDotRequest));
    }

    @PostMapping()
    public ResponseObject update(@RequestBody DtCreateSinhVienRequest createSinhVienRequest) {
        String coSoId = session.getCoSoId();
        createSinhVienRequest.setCoSoId(coSoId);
        return new ResponseObject(sinhVienService.create(createSinhVienRequest));
    }

    @GetMapping("/search-mondatn/{chuyenNganhId}")
    public ResponseObject getAllMonDatnByChuyenNganhId(@PathVariable("chuyenNganhId") String chuyenNganhId) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(sinhVienService.getAllMonDatnByChuyenNganhId(chuyenNganhId, coSoId));
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
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
        LogObject logObject = new LogObject();
        logObject.setPathFile(pathFile);
        List<LogObject> list = writerFileCSV.readFileCSV(logObject);
        List<LogObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
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
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_GIANG_VIEN_HUONG_DAN);

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
