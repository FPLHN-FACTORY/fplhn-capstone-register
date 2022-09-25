package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.sinhvien.model.request.FindSvNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SonPT
 */

@RestController
@RequestMapping("/api/sinh-vien")
public class SvNhomDatnRestController extends BaseController {

    @Autowired
    private SvNhomDatnService svNhomDatnService;

    @PutMapping("/{id}")
    public ResponseObject guiYeuCauPheDuyet(@PathVariable String id){
        return new ResponseObject(svNhomDatnService.guiYeuCauPheDuyet(id, "db85db46-8ac1-4267-8ee1-588f395df8bf"));
    }

    @GetMapping("/search")
    public ResponseObject viewHocKy(final FindSvNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setDotDangKyId("e3afc2f8-3562-4606-9aae-62366445d077");
        PageableObject<SvSinhVienNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req);
        DtApiResponse<SvSinhVienNhomDatnResponse> dtApiResponse = new DtApiResponse<>();
        dtApiResponse.setData(listNhomDatn);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listNhomDatn.getTotalPages(); i++) {
            list.add(i + 1);
        }
        dtApiResponse.setList(list);
        return new ResponseObject(dtApiResponse);
    }

}
