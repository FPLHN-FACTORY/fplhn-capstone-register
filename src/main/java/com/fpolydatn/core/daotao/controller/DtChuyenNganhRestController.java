package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateChuyenNganhRequest;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.entity.ChuyenNganh;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hoangnt
 */
@RestController
@RequestMapping("/api/dao-tao/chuyen-nganh")
public class DtChuyenNganhRestController extends BaseController {

    @Autowired
    private DtChuyenNganhService chuyenNganhService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        ChuyenNganh chuyenNganh = chuyenNganhService.findById(id);
        return new ResponseObject(chuyenNganh);
    }

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateChuyenNganhRequest request) {
        String coSoId = session.getCoSoId();
        request.setCoSoId(coSoId);
        ChuyenNganh chuyenNganh = chuyenNganhService.add(request);
        return new ResponseObject(chuyenNganh);
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(chuyenNganhService.delete(id, coSoId));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id, @RequestBody DtUpdateChuyenNganhRequest request) {
        request.setId(id);
        String coSoId = session.getCoSoId();
        request.setCoSoId(coSoId);
        return new ResponseObject(chuyenNganhService.update(request));
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_CHUYEN_NGANH);
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
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_CHUYEN_NGANH);

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
