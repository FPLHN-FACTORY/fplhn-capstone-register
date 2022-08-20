package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.UpdateMonDatnRequest;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
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
 * @author hungpv
 */
@RestController
@RequestMapping("/api/dao-tao/mon-datn")
public class DtMonDatnRestController extends BaseController {

    @Autowired
    private DtMonDatnService monDatnService;

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(monDatnService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(monDatnService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                        @RequestBody UpdateMonDatnRequest monDatnRequest) {
        monDatnRequest.setId(id);
        return new ResponseObject(monDatnService.update(monDatnRequest));
    }

    @PostMapping
    public ResponseObject add(@RequestBody CreateMonDatnRequest monDatnRequest) {
        monDatnRequest.setCoSoId(session.getCoSoId());
        return new ResponseObject(monDatnService.add(monDatnRequest));
        }
}
