package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtFindNhomMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public ResponseObject viewChiTietNhomMonDatn(final DtFindNhomMonDatnRequest req
            , @PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        List<DtFindNhomMonDatnResponse> dtFindNhomMonDatnResponses = dtNhomMonDatnService.getMonChiTiet(id,req);
        return new ResponseObject(dtFindNhomMonDatnResponses);
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateNhomMonDatnRequest dtUpdateNhomMonRequest) {
        dtUpdateNhomMonRequest.setId(id);
        String coSoId = session.getCoSoId();
        dtUpdateNhomMonRequest.setCoSoId(coSoId);
        return new ResponseObject(dtNhomMonDatnService.update(dtUpdateNhomMonRequest));
    }

    @PostMapping()
    public ResponseObject save(
            @RequestBody DtCreateNhomMonDatnRequest dtCreateNhomMonDatnRequest) {
        String coSoId = session.getCoSoId();
        dtCreateNhomMonDatnRequest.setCoSoId(coSoId);
        return new ResponseObject(dtNhomMonDatnService.add(dtCreateNhomMonDatnRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(dtNhomMonDatnService.delete(id));
    }
}
