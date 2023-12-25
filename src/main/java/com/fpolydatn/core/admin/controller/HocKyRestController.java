package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.service.*;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.admin.model.request.CreateHocKyRequest;
import com.fpolydatn.core.admin.model.request.UpdateHocKyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phongtt35
 */

@RestController
@RequestMapping("/api/dao-tao/hoc-ky")
public final class HocKyRestController extends BaseController {

    private final TaoHocKyUseCase taoHocKyUseCase;
    private final SuaHocKyUseCase suaHocKyUseCase;
    private final XoaHocKyUseCase xoaHocKyUseCase;
    private final TimDsHocKyUseCase timDsHocKyUseCase;
    private final XemChiTietHocKyUseCase xemChiTietHocKyUseCase;

    @Autowired
    public HocKyRestController(TaoHocKyUseCase taoHocKyUseCase,
                               SuaHocKyUseCase suaHocKyUseCase,
                               XoaHocKyUseCase xoaHocKyUseCase,
                               TimDsHocKyUseCase timDsHocKyUseCase,
                               XemChiTietHocKyUseCase xemChiTietHocKyUseCase) {
        this.taoHocKyUseCase = taoHocKyUseCase;
        this.suaHocKyUseCase = suaHocKyUseCase;
        this.xoaHocKyUseCase = xoaHocKyUseCase;
        this.timDsHocKyUseCase = timDsHocKyUseCase;
        this.xemChiTietHocKyUseCase = xemChiTietHocKyUseCase;
    }

    @PostMapping
    public ResponseObject add(@RequestBody CreateHocKyRequest cmd) {
        return new ResponseObject(taoHocKyUseCase.execute(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(xemChiTietHocKyUseCase.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody UpdateHocKyRequest cmd) {
        cmd.setId(id);
        return new ResponseObject(suaHocKyUseCase.execute(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(xoaHocKyUseCase.execute(id));
    }

    @GetMapping
    public ResponseObject getAll() {
        FindHocKyRequest request = new FindHocKyRequest();
        request.setTenHocKy("");
        return new ResponseObject(timDsHocKyUseCase.execute(request));
    }

//    @GetMapping("/history")
//    public ResponseObject getAllHistory(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "50") int size
//    ) {
//        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_HOC_KY);
//        LogObject logObject = new LogObject();
//        logObject.setPathFile(pathFileLogActive);
//        List<LogObject> list = writerFileCSV.readFileCSV(logObject);
//        List<LogObject> pagedSanPhamList = list.stream()
//                .skip(page * size)
//                .limit(size)
//                .collect(Collectors.toList());
//        return new ResponseObject(new PageImpl<>(pagedSanPhamList, PageRequest.of(page, size), list.size()));
//    }
//
//    @GetMapping("/download-log")
//    public ResponseEntity<Resource> downloadCsv() throws IOException {
//        String pathFileLogActive = writerFileCSV.getSwitchCoSo() + writerFileCSV.getPropertiesRead(ConfigurationsConstant.NAME_FILE_HOC_KY);
//
//        File file = new File(pathFileLogActive);
//
//        if (!file.exists()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }
}
