package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dao-tao/nhom-mon-datn")
public class DtNhomMonDatnRestcontroller extends BaseController {
    @Autowired
    private DtNhomMonDatnService dtNhomMonDatnService;

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateNhomMonDatnRequest dtUpdateNhomMonRequest) {
        dtUpdateNhomMonRequest.setId(id);
        return new ResponseObject(dtNhomMonDatnService.update(dtUpdateNhomMonRequest));
    }

    @PostMapping ("/{id}")
    public ResponseObject save(@PathVariable("id") String id,
                                 @RequestBody DtCreateNhomMonDatnRequest dtCreateNhomMonDatnRequest) {
        return new ResponseObject(dtNhomMonDatnService.add(dtCreateNhomMonDatnRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(dtNhomMonDatnService.delete(id));
    }
}
