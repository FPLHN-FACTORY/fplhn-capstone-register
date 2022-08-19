package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.UpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.service.GiangVienHDService;
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
 * @author Vinhnv
 */
@RestController
@RequestMapping("/api/dao-tao/giang-vien-huong-dan")
public class GiangVienRestController {

    @Autowired
    private GiangVienHDService giangVienHDService;

    @PostMapping
    public ResponseObject add(@RequestBody CreateGiangVienHDRequest cmd) {
        return new ResponseObject(giangVienHDService.create(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody UpdateGiangVienHDRequest cmd) {
        cmd.setId(id);
        return new ResponseObject(giangVienHDService.update(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.delete(id));
    }

}
