package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMessMonDatnReponse;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        return new ResponseObject(monDatnService.delete(id, coSoId, dotDangKyId));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(monDatnService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateMonDatnRequest monDatnRequest) {
        monDatnRequest.setId(id);
        return new ResponseObject(monDatnService.update(monDatnRequest));
    }

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateMonDatnRequest monDatnRequest) {
        monDatnRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(monDatnService.add(monDatnRequest));
    }

    @PostMapping("/import")
    public ResponseObject importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        DtMessMonDatnReponse check = this.monDatnService.importExcel(file, session.getCoSoId());
        return new ResponseObject(check);
    }

}
