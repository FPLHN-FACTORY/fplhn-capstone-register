package com.fpolydatn.core.daotao.controller;


import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.UpdateCanBoRequest;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author caodinh
 */

@RestController
@RequestMapping("/api/dao-tao/can-bo")
public class DtCanBoRestController {

    @Autowired
    private DtCanBoService canBoService;

    @Autowired
    private FpolyDatnSession fpolyDatnSession;


    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") UUID id) {
        return new ResponseObject(canBoService.findById(id));
    }

    @PostMapping
    public ResponseObject add(@RequestBody CreateCanBoRequest createCanBoRequest) {
        createCanBoRequest.setCoSoId(UUID.fromString( fpolyDatnSession.getCoSoId()));
        return new ResponseObject(canBoService.create(createCanBoRequest));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") UUID id,
                                 @RequestBody UpdateCanBoRequest updateCanBoRequest) {
        updateCanBoRequest.setId(id);
        updateCanBoRequest.setCoSoId(UUID.fromString( fpolyDatnSession.getCoSoId()));
        return new ResponseObject(canBoService.update(id, updateCanBoRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") UUID id) {
        return new ResponseObject(canBoService.delete(id));
    }

}