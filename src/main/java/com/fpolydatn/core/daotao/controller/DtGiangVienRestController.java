package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponseMessage;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Vinhnv
 */
@RestController
@RequestMapping("/api/dao-tao/giang-vien-huong-dan")
public class DtGiangVienRestController extends BaseController {

    @Autowired
    private DtGiangVienHDService giangVienHDService;

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateGiangVienHDRequest cmd) {
        String coSoid = session.getCoSoId();
        cmd.setCoSoId(coSoid);
        if (cmd.getDotDangKyId().equalsIgnoreCase("")) {
            cmd.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        }
        return new ResponseObject(giangVienHDService.create(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateGiangVienHDRequest cmd) {
        cmd.setId(id);
        cmd.setCoSoId(session.getCoSoId());
        return new ResponseObject(giangVienHDService.update(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.delete(id));
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindGiangVienHDRequest req) {
        req.setCoSoId(session.getCoSoId());
        PageableObject<DtGiangVienHDResponse> listGiangVienHD = giangVienHDService.search(req);
        return new ResponseObject(listGiangVienHD);
    }

    @PostMapping("/import/{dotDangKyId}")
    public ResponseObject importEcel(@RequestParam("file") MultipartFile file, @PathVariable("dotDangKyId") String dotDangKyId) throws IOException {
        String idCoSo = session.getCoSoId();
        DtGiangVienHDResponseMessage responseMessage = giangVienHDService.DtGiangVienHDImport(file, idCoSo, dotDangKyId.trim());
        return new ResponseObject(responseMessage);
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        if (request != null && (request.getDotDangKyId() == null || request.getDotDangKyId().trim().length() == 0)) {
            request.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        }
        giangVienHDService.DtGiangVienHDExportExcel(response, typeExcel, request);
    }

    @GetMapping("/export/mau")
    public void exportMauExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, DtFindGiangVienHDRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        giangVienHDService.DtGiangVienHDMauExportExcel(response, typeExcel, request);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "dotDangKy", defaultValue = "") String idDotDangKy,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
            if (dotDangKyHienTai != null) {
                idDotDangKy = dotDangKyHienTai;
            } else {
                throw new RestApiException(Message.KHONG_CO_DOT_HIEN_TAI_VUI_LONG_CHON_DOT);
            }
        }
        String pathFile = writerFileCSV.getPathFileByDotDangKyAndCoSo(dotDangKyService.findById(idDotDangKy).getTenDotDangKy()) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_GIANG_VIEN_HUONG_DAN);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        List<LoggerObject> list = writerFileCSV.readFileCSV(loggerObject);
        Collections.reverse(list);
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
        if (idDotDangKy.equals("")) {
            String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
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
