package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.sinhvien.model.request.CreateSinhVienJoinNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvSinhVienNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sinh-vien/nhom-datn")
public class SvSinhVienNhomDatnRestController extends BaseController {
    @Autowired
    private SvSinhVienNhomDatnService svSinhVienNhomDatnService;

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("keyword") String id, final FindSinhVienRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setId(id);
        PageableObject<DtSinhVienResponse> listDanhSachSinhVien = svSinhVienNhomDatnService.showDanhSachSinhVien(req);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @GetMapping("/search")
    public ResponseObject viewHocKy(final FindDtNhomDatnTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<SvSinhVienNhomDatnResponse> listNhomDatn = svSinhVienNhomDatnService.findAllNhomDatn(req);
        DtApiResponse<SvSinhVienNhomDatnResponse> dtApiResponse = new DtApiResponse<>();
        dtApiResponse.setData(listNhomDatn);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listNhomDatn.getTotalPages(); i++) {
            list.add(i + 1);
        }
        dtApiResponse.setList(list);
        return new ResponseObject(dtApiResponse);
    }

    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(final CreateSinhVienJoinNhomDatnRequest req) {
        return new ResponseObject(svSinhVienNhomDatnService.addSinhVienTheoNhom(req));
    }
}

