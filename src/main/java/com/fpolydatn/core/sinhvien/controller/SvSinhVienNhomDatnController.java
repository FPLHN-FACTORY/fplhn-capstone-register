package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.sinhvien.model.request.FindSvNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvSinhVienNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sinh-vien/nhom-datn")
public class SvSinhVienNhomDatnController extends BaseController {
    @Autowired
    private SvSinhVienNhomDatnService svSinhVienNhomDatnService;

    @Autowired
    private DtChuyenNganhService dtChuyenNganhService;

    @Autowired
    private DtDotDangKyService dtDotDangKyService;

    @GetMapping
    public String findAllNhomDatn(Model model, final FindSvNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<SvSinhVienNhomDatnResponse> listNhomDatn = svSinhVienNhomDatnService.findAllNhomDatn(req);
        model.addAttribute("page", listNhomDatn);
        return "sinhvien/sv-nhom-datn";
    }
}

