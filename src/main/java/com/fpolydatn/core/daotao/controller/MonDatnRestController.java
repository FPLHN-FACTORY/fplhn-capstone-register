package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.UpdateMonDatnRequest;
import com.fpolydatn.core.daotao.service.MonDatnService;
import com.fpolydatn.entity.MonDatn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
 * @author hungpv
 */
@RestController
@RequestMapping("/api/dao-tao/mon-datn")
public class MonDatnRestController extends BaseController {

    @Autowired
    private MonDatnService monDatnService;

    @DeleteMapping("/{id}")
    public ResponseObject deleteMonDatn(@PathVariable("id") String id) {
        return new ResponseObject(monDatnService.deleteMonDatn(id));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(Model model, @PathVariable("id") String id) {
        MonDatn monDatn = null;
        try {
            monDatn = monDatnService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(monDatn);
    }

    @PutMapping("/{id}")
    public ResponseObject updateMonDatn(@PathVariable("id") String id,
                                        @RequestBody UpdateMonDatnRequest monDatnRequest) {
        monDatnRequest.setId(id);
        return new ResponseObject(monDatnService.update(monDatnRequest));
    }

    @PostMapping
    public ResponseObject add(Model model, @RequestBody CreateMonDatnRequest monDatnRequest) {
        Boolean add = false;
        try {
            add = monDatnService.create(monDatnRequest);
            return new ResponseObject(add);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ResponseObject(e);
        }
    }
}
