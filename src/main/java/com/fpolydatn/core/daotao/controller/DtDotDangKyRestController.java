package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateDotDangKyRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateDotDangKyRequest;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseObject getAllHistory() {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_DOT_DANG_KY);
        return new ResponseObject(writerFileCSV.readFileCSV(pathFileLogActive));
    }

//    @GetMapping("/download-log")
//    public ResponseEntity<FileSystemResource> downloadFileLog(@RequestParam(name = "tenDotDangKy", defaultValue = "") String tenDotDangKy) throws ZipException {
//        File file = dotDangKyService.downloadFileLog(tenDotDangKy);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
//
//    }

}