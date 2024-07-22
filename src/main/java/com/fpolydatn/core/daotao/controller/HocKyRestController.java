package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateHocKyRequest;
import com.fpolydatn.core.daotao.model.request.UpdateHocKyRequest;
import com.fpolydatn.core.daotao.service.HocKyService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.logger.LoggerObject;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author phongtt35
 */

@RestController
@RequestMapping("/api/dao-tao/hoc-ky")
public final class HocKyRestController extends BaseController {

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @PostMapping
    public ResponseObject add(@RequestBody CreateHocKyRequest cmd) {
        return new ResponseObject(hocKyService.create(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(hocKyService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody UpdateHocKyRequest cmd) {
        cmd.setId(id);
        return new ResponseObject(hocKyService.update(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(hocKyService.delete(id));
    }

    @GetMapping
    public ResponseObject getAll() {
        return new ResponseObject(hocKyService.getAll());
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_HOC_KY);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFileLogActive);
        List<LoggerObject> list = writerFileCSV.readFileCSV(loggerObject);
        List<LoggerObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_HOC_KY);

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
