package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dao-tao/nhom-datn")
public class DtNhomDatnTheoDotRestController extends BaseController {
    @Autowired
    private DtNhomDatnTheoDotService dtNhomDatnTheoDotService;

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("keyword") String id, final FindSinhVienRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setId(id);
        PageableObject<DtSinhVienResponse> listDanhSachSinhVien = dtNhomDatnTheoDotService.showDanhSachSinhVien(req);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @GetMapping("/search")
    public ResponseObject viewHocKy(final FindDtNhomDatnTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtNhomDatnTheoDotResponse> listNhomDatn = dtNhomDatnTheoDotService.findAllNhomDatn(req);
        DtApiResponse<DtNhomDatnTheoDotResponse> dtApiResponse = new DtApiResponse<>();
        dtApiResponse.setData(listNhomDatn);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listNhomDatn.getTotalPages(); i++) {
            list.add(i + 1);
        }
        dtApiResponse.setList(list);
        return new ResponseObject(dtApiResponse);
    }

}
