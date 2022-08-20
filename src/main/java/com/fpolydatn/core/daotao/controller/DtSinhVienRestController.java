package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.service.DtSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/dao-tao/sinh-vien")
public class DtSinhVienRestController extends BaseController {
    @Autowired
    private DtSinhVienService sinhVienService;

    @GetMapping
    public ResponseObject searchStudent(final FindDtSinhVienRequest req){
        req.setGetCoSoId(session.getCoSoId());
        PageableObject<DtSinhVienResponse> listSinhVien = sinhVienService.findAllSinhVien(req);
        return new ResponseObject(listSinhVien);
    }
}
