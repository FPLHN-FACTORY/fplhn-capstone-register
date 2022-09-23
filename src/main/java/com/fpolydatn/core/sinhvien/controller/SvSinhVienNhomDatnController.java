package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvSinhVienNhomDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

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
    public String findAllNhomDatn(Model model, final FindDtNhomDatnTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<SvSinhVienNhomDatnResponse> listNhomDatn = svSinhVienNhomDatnService.findAllNhomDatn(req);
        List<SimpleEntityProj> listChuyenNganh = dtChuyenNganhService.findAllSimpleEntityByCoSo(coSoId);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        List<SimpleEntityProj> listDotDangKy = dtDotDangKyService.findAllSimpleEntityByCoSo(coSoId);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("page", listNhomDatn);
        return "sinhvien/sv-nhom-datn";
    }
}

