package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.request.CnSearchNhomInputRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienImport;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.chunhiem.service.CnMonDatnService;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.WriterFileCSV;
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
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/nhom-datn")
public class CnNhomDatnRestController extends BaseController {

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CnMonDatnService cnMonDatnService;

    @Autowired
    private CnGiangVienHuongDanService cnGiangVienHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @GetMapping("/search")
    public ResponseObject viewNhomDatn(final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req, session.getUserId());
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("/search-list-nhom")
    public ResponseObject searchMaNhom(final CnSearchNhomInputRequest request) {
        request.setCoSoId(session.getCoSoId());
        return new ResponseObject(nhomDatnService.searchNhomDatnInput(request));
    }

    @PostMapping
    public ResponseObject create(@RequestBody CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        cnCreateNhomDatnRequest.setCoSoId(session.getCoSoId());
        cnCreateNhomDatnRequest.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(nhomDatnService.create(cnCreateNhomDatnRequest));
    }

    @PutMapping("/chot-de-tai/{id}")
    public ResponseObject chotDeTai(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.chotDeTai(id));
    }

    @PutMapping("/chot-nhieu-de-tai")
    public ResponseObject chotNhieuDeTai(@RequestBody List<String> listIdNhom) {
        return new ResponseObject(nhomDatnService.chotNhieuDeTai(listIdNhom));
    }

    @PutMapping("/xac-nhan-nhom/{id}")
    public ResponseObject xacNhanNhom(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.xacNhanNhom(id));
    }

    @PutMapping("/xac-nhan-nhieu-nhom")
    public ResponseObject xacNhanNhieuNhom(@RequestBody List<String> listIdNhom) {
        return new ResponseObject(nhomDatnService.xacNhanNhieuNhom(listIdNhom));
    }

    @PutMapping
    public ResponseObject tuChoiChotDeTai(@RequestParam("id") String id, @RequestParam("nhanXet") String nhanXet) {
        return new ResponseObject(nhomDatnService.tuChoiChotDeTai(id, nhanXet));
    }

    @GetMapping("/{id}")
    public ResponseObject detailNhomDatn(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteNhomDatn(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.delete(id));
    }

    @GetMapping("/list-sinh-vien-no-group")
    public ResponseObject getlistNhomDatnNoGroup(final CnFindSinhVienNoGroupRequest req) {
        req.setCoSoId(session.getCoSoId());
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        return new ResponseObject(nhomDatnService.getListSinhVienNoGroup(req, session.getUserId()));
    }

    @GetMapping("/list-mon-datn")
    public ResponseObject getAllMonDatnByNhomMon(@RequestParam("nhomMonId") String nhomMonId) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(cnMonDatnService.getAllMonDatnByNhomMon(coSoId, nhomMonId));
    }

    @GetMapping("/list-gvhd")
    public ResponseObject getAllGvhdByIdMon(@RequestParam("idMon") String idMon) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        return new ResponseObject(cnGiangVienHuongDanService.getGiangVienHuongDanByIdMonDatn(idMon, coSoId, dotDangKyId));
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, final CnFindSinhVienTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        nhomDatnService.CnSinhVienExport(response, request, session.getUserId());
    }

    @GetMapping("/export/sinh-vien")
    public void exportExcelSinhVien(HttpServletResponse response, final CnFindSinhVienTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        nhomDatnService.CnExportThongTinSinhVien(response, request, session.getUserId());
    }

    @PostMapping("/import")
    public ResponseObject importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        CnSinhVienImport response = nhomDatnService.CnSinhVienImport(file, dotDangKyId, coSoId, session.getUserId());
        return new ResponseObject(response);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        List<LoggerObject> list = writerFileCSV.readFileCSV(loggerObject);
        List<LoggerObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }


    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy
    ) throws IOException {
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);

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
