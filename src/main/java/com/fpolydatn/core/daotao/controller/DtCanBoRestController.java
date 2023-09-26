package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponseMessage;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
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
 * @author caodinh
 */

@RestController
@RequestMapping("/api/dao-tao/can-bo")
public final class DtCanBoRestController extends BaseController {

    @Autowired
    private DtCanBoService canBoService;

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(canBoService.findById(id));
    }

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateCanBoRequest createCanBoRequest) {
        createCanBoRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(canBoService.create(createCanBoRequest));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateCanBoRequest updateCanBoRequest) {
        updateCanBoRequest.setId(id);
        updateCanBoRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(canBoService.update(updateCanBoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(canBoService.delete(id, session.getCoSoId()));
    }

    @PostMapping("/import")
    public ResponseObject importEcel(@RequestParam("file") MultipartFile file) throws IOException {
        String coSoId = session.getCoSoId();
        DtCanBoResponseMessage response = canBoService.DtCanBoImport(file, coSoId);
        return new ResponseObject(response);
    }

    @GetMapping("/export-mau")
    public void exportMauExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel) throws IOException {
        canBoService.MauImportExcel(response, typeExcel);
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindCanBoRequest req) {
        req.setCoSoId(session.getCoSoId());
        PageableObject<DtCanBoResponse> listCanBo = canBoService.search(req);
        return new ResponseObject(listCanBo);
    }

    @GetMapping("/history")
    public ResponseObject getAllHistory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_CAN_BO);
        List<LoggerObject> list = writerFileCSV.readFileCSV(pathFileLogActive);
        List<LoggerObject> pagedSanPhamList = list.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() throws IOException {
        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_CAN_BO);

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