package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnImport;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

@RestController
@RequestMapping("/api/chu-nhiem/dot-dang-ky/sinh-vien")
public class CnSinhVienTheoDotRestController extends BaseController {

    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping("/search")
    public ResponseObject searchSinhVienTheoDot(final CnFindSinhVienTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setIdChuNhiem(session.getUserId());
        req.setCoSoId(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> listNhomDatnTheoDot = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        return new ResponseObject(listNhomDatnTheoDot);
    }

    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("id") String maNhom, @RequestParam("maSinhVien") String maSinhVien) {
        return new ResponseObject(cnSinhVienTheoDotService.addSinhVienTheoNhom(maNhom, maSinhVien));
    }

    @PostMapping("/random-nhom-datn")
    public ResponseObject randomNhomDatn(@RequestParam("number") Integer number) {
        String coSoId = session.getCoSoId();
        String idChuNhiem = session.getUserId();
        return new ResponseObject(cnSinhVienTheoDotService.randomNhomDatnByIdChuNhiem(idChuNhiem, coSoId, number));
    }

    @GetMapping("/export-sap-gvhd")
    public void exportExcelSapGvhd(HttpServletResponse response, final CnFindSinhVienTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        cnSinhVienTheoDotService.CnExportSapGvhd(response, request, session.getUserId());
    }

    @PostMapping("/import-sap-gvhd")
    public ResponseObject importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        CnNhomDatnImport response = cnSinhVienTheoDotService.importSapGvhd(file, dotDangKyId, coSoId, session.getUserId());
        return new ResponseObject(response);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
        List<LoggerObject> list = writerFileCSV.readFileCSV(pathFile);
        List<LoggerObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }


    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(writerFileCSV.tenDotDangKyHienTai()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);

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
    };

}
