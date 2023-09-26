package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailNhomMonResponse;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
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

@RestController
@RequestMapping("/api/dao-tao/nhom-mon-datn")
public class DtNhomMonDatnRestcontroller extends BaseController {
    @Autowired
    private DtNhomMonDatnService dtNhomMonDatnService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(dtNhomMonDatnService.findById(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject showDanhSachMonDatn(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        List<DtDetailNhomMonResponse> dtDetailNhomMonRespons = dtNhomMonDatnService.detail(id, coSoId);
        return new ResponseObject(dtDetailNhomMonRespons);
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateNhomMonDatnRequest dtUpdateNhomMonRequest) {
        dtUpdateNhomMonRequest.setCoSoId(session.getCoSoId());
        dtUpdateNhomMonRequest.setId(id);
        return new ResponseObject(dtNhomMonDatnService.update(dtUpdateNhomMonRequest));
    }

    @PostMapping()
    public ResponseObject create(@RequestBody DtCreateNhomMonDatnRequest dtCreateNhomMonDatnRequest) {
        String coSoId = session.getCoSoId();
        dtCreateNhomMonDatnRequest.setCoSoId(coSoId);
        return new ResponseObject(dtNhomMonDatnService.add(dtCreateNhomMonDatnRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        return new ResponseObject(dtNhomMonDatnService.delete(id, coSoId));
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindNhomMonDatnRequest req) {
        req.setCoSoId(session.getCoSoId());
        PageableObject<DtNhomMonDatnResponse> listMon = dtNhomMonDatnService.findAllByCoSo(req);
        return new ResponseObject(listMon);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFile = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_MON_DATN);

        List<LoggerObject> list = writerFileCSV.readFileCSV(pathFile);
        List<LoggerObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }


    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFile = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_MON_DATN);

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
