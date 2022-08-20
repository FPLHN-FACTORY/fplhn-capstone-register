package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.CreateChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.request.UpdateChuyenNganhRequest;
import com.fpolydatn.core.daotao.service.ChuyenNganhService;
import com.fpolydatn.entity.ChuyenNganh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dao-tao/chuyen-nganh")
public class ChuyenNganhRestController extends BaseController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id){
        return new ResponseObject(id);
    }

    @PostMapping
    public ResponseObject add(@RequestBody CreateChuyenNganhRequest request){
        ChuyenNganh chuyenNganh = chuyenNganhService.add(request);
        return new ResponseObject(chuyenNganh);
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id){
        return new ResponseObject(chuyenNganhService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id, @RequestBody UpdateChuyenNganhRequest request){
        return new ResponseObject(chuyenNganhService.update(id,request));
    }

}
