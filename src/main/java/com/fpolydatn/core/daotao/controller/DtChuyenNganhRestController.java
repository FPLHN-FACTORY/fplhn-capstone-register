package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateChuyenNganhRequest;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.util.CommonHelper;
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
 * @author hoangnt
 */
@RestController
@RequestMapping("/api/dao-tao/chuyen-nganh")
public class DtChuyenNganhRestController extends BaseController {

    @Autowired
    private DtChuyenNganhService chuyenNganhService;

    @Autowired
    private CommonHelper commonHelper;

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
}
