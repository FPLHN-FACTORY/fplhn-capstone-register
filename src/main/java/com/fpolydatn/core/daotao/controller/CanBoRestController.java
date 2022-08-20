package com.fpolydatn.core.daotao.controller;


import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.UpdateCanBoRequest;
import com.fpolydatn.core.daotao.service.CanBoService;
import com.fpolydatn.entity.CanBo;
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
 * @author caodinh
 */

@RestController
@RequestMapping("/api/dao-tao/can-bo")
public class CanBoRestController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping("/{id}")
    public ResponseObject detailHocKy(Model model, @PathVariable("id") UUID id) {
        CanBo canBo = null;
        try {
            canBo = canBoService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(canBo);
    }

    @PostMapping
    public ResponseObject addHocKy(Model model, @RequestBody CreateCanBoRequest createCanBoRequest) {
        CanBo canBo = null;
        try {
            canBo = canBoService.addNew(createCanBoRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(canBo);
    }

    @PutMapping("/{id}")
    public ResponseObject updateHocKy(Model model, @PathVariable("id") UUID id,
                                      @RequestBody UpdateCanBoRequest canBoRequest) {
        CanBo canBo = null;
        try {
            canBo = canBoService.update(id, canBoRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(canBo);
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteHocKy(Model model, @PathVariable("id") UUID id) {
        Boolean remove = false;
        try {
            remove = canBoService.delete(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(remove);
    }

}