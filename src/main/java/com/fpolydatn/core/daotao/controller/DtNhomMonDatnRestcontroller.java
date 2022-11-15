package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtDetailNhomMonResponse;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dao-tao/nhom-mon-datn")
public class DtNhomMonDatnRestcontroller extends BaseController {
    @Autowired
    private DtNhomMonDatnService dtNhomMonDatnService;

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
}
