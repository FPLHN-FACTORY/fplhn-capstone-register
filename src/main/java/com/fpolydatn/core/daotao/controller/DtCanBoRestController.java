package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.service.DtCanBoService;
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
 * @author caodinh
 */

@RestController
@RequestMapping("/api/dao-tao/can-bo")
public final class DtCanBoRestController extends BaseController {

    @Autowired
    private DtCanBoService canBoService;

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
        return new ResponseObject(canBoService.delete(id));
    }

}