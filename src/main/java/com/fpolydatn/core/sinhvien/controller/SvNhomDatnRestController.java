package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SonPT
 */

@RestController
@RequestMapping("/api/sinh-vien/nhom-datn")
public class SvNhomDatnRestController extends BaseController {

    @Autowired
    private SvNhomDatnService svNhomDatnService;

    @PutMapping("/{id}")
    public ResponseObject guiYeuCauPheDuyet(@PathVariable String id){
        return new ResponseObject(svNhomDatnService.guiYeuCauPheDuyet(id, "db85db46-8ac1-4267-8ee1-588f395df8bf"));
    }

}
