package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.MuitiPathFile;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtNhomMonDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dao-tao/nhom-mon-datn")
public class DtNhomMonDatnController extends BaseController {
    @Autowired
    private DtNhomMonDatnService dtNhomMonDatnService;
    @Autowired
    private DtChuyenNganhService chuyenNganhService;
    @GetMapping
    public String viewNhomMonDatn(Model model, final DtFindNhomMonDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtNhomMonDatnResponse> pageNhomMonDatn = dtNhomMonDatnService.search(req);
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.findAllSimpleEntityByCoSo(coSoId);
        model.addAttribute("page", pageNhomMonDatn);
        model.addAttribute("file", new MuitiPathFile());
        model.addAttribute("tenChuyenNganh", listChuyenNganh);
        return "daotao/dt-nhom-mon-datn";
    }
}
