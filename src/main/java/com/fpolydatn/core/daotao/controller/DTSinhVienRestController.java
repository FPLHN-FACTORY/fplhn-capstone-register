package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDTSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DTSinhVienResponse;
import com.fpolydatn.core.daotao.service.DTSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/dao-tao/sinh-vien")
public class DTSinhVienRestController extends BaseController {
    @Autowired
    private DTSinhVienService sinhVienService;

    @GetMapping
    public ResponseObject searchStudent(final FindDTSinhVienRequest req){
        req.setGetCoSoId(session.getCoSoId());
        PageableObject<DTSinhVienResponse> listSinhVien = sinhVienService.findAllSinhVien(req);
        return new ResponseObject(listSinhVien);
    }
}
