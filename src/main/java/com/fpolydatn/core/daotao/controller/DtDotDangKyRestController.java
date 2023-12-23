package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateDotDangKyRequest;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.core.common.log.LogObject;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SonPT
 */

@RestController
@RequestMapping("/api/dao-tao/dot-dang-ky")
public final class DtDotDangKyRestController extends BaseController {

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateDotDangKyRequest createDotDangKyRequest) {
        createDotDangKyRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(dotDangKyService.create(createDotDangKyRequest));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable String id, @RequestBody DtUpdateDotDangKyRequest dtUpdateDotDangKyRequest) {
        dtUpdateDotDangKyRequest.setId(id);
        dtUpdateDotDangKyRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(dotDangKyService.update(dtUpdateDotDangKyRequest));
    }

    @GetMapping("/{id}")
    public ResponseObject findById(@PathVariable("id") String id) {
        return new ResponseObject(dotDangKyService.findById(id));
    }

    @GetMapping
    public ResponseObject getListDotDangKy() {
        return new ResponseObject(dotDangKyService.getAllByCoSo(session.getCoSoId()));
    }

    @GetMapping("/search/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(dotDangKyService.detail(id, coSoId));
    }

    @GetMapping("/create-file-log")
    public ResponseObject createFileLog(@RequestParam(name = "tenDotDangKy", defaultValue = "") String tenDotDangKy) {
        return new ResponseObject(dotDangKyService.createFileLog(tenDotDangKy));
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_DOT_DANG_KY);
        LogObject logObject = new LogObject();
        logObject.setPathFile(pathFileLogActive);
        List<LogObject> list = writerFileCSV.readFileCSV(logObject);
        List<LogObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }


    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_DOT_DANG_KY);

        File file = new File(pathFileLogActive);

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