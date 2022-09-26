package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.AddSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvShowSinhVienTheoNhomResponse;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseObject viewNhomDatn(final SvFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setDotDangKyId("67f067fa-a0f4-4792-9cc4-3dbc92341561");
        PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req);
        DtApiResponse<SvNhomDatnResponse> dtApiResponse = new DtApiResponse<>();
        dtApiResponse.setData(listNhomDatn);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listNhomDatn.getTotalPages(); i++) {
            list.add(i + 1);
        }
        dtApiResponse.setList(list);
        return new ResponseObject(dtApiResponse);
    }

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("id") String id) {
        List<SvShowSinhVienTheoNhomResponse> listDanhSachSinhVien = svNhomDatnService.showDanhSachSinhVien(id);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @PostMapping("/add")
    public ResponseObject addSinhVien(@RequestBody AddSinhVienRequest req) {
        req.setIdUser("27b2dc99-65cb-4cbf-b02b-e2e3213517f5");
        return new ResponseObject(svNhomDatnService.addSinhVienTheoNhom(req));
    }

}
