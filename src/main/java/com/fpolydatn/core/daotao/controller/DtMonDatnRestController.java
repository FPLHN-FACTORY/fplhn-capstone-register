package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMessMonDatnReponse;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hungpv
 */
@RestController
@RequestMapping("/api/dao-tao/mon-datn")
public class DtMonDatnRestController extends BaseController {

    @Autowired
    private DtMonDatnService monDatnService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(monDatnService.delete(id, coSoId));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(monDatnService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateMonDatnRequest monDatnRequest) {
        monDatnRequest.setId(id);
        String coSoId = session.getCoSoId();
        monDatnRequest.setCoSoId(coSoId);
        return new ResponseObject(monDatnService.update(monDatnRequest));
    }

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateMonDatnRequest monDatnRequest) {
        monDatnRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(monDatnService.add(monDatnRequest));
    }

    @PostMapping("/import")
    public ResponseObject importExcel(@RequestParam("file") MultipartFile file) {
        DtMessMonDatnReponse check = this.monDatnService.importExcel(file, session.getCoSoId());
        return new ResponseObject(check);
    }

    @GetMapping("/get-nhom-mon")
    public ResponseObject getNhomMon(@RequestParam("chuyenNganhId") String chuyenNganhId) {
        return new ResponseObject(monDatnService.getNhomMonByChuyeNganh(session.getCoSoId(), chuyenNganhId));
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindMonDatnRequest req) {
        req.setCoSoId(session.getCoSoId());
        System.out.println(req);
        PageableObject<DtMonDatnResponse> listMon = monDatnService.search(req);
        return new ResponseObject(listMon);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFile = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_MON_DATN);
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
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFile = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_MON_DATN);

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
