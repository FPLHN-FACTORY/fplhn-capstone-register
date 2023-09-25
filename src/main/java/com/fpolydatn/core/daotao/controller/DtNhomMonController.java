package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import com.fpolydatn.core.daotao.service.DtSvChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dao-tao/nhom-mon-datn")
public class DtNhomMonController extends BaseController {
    @Autowired
    private DtNhomMonDatnService dtNhomMonDatnService;

    @Autowired
    private DtSvChuyenNganhService chuyenNganhService;

    @GetMapping
    public String viewNhomMonDatn(Model model, final DtFindNhomMonDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtNhomMonDatnResponse> pageNhomMonDatn = dtNhomMonDatnService.findAllByCoSo(req);
        model.addAttribute("listChuyenNganh", chuyenNganhService.getListChuyenNganh(coSoId));
        model.addAttribute("page", pageNhomMonDatn);
        return "daotao/dt-nhom-mon-datn";
    }

}
