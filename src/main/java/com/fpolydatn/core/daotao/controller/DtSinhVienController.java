package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtSinhVienService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/dao-tao/sinh-vien")
public class DtSinhVienController extends BaseController {
    @Autowired
    private DtSinhVienService sinhVienService;

    @Autowired
    private DtChuyenNganhService chuyenNganhService;
    @GetMapping
    public String findAllSinhVien(Model model,final FindDtSinhVienRequest req) {
        req.setGetCoSoId(session.getCoSoId());
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.findAllSimpleEntityByCoSo(req.getGetCoSoId());
        PageableObject<DtSinhVienResponse> listSinhVien = sinhVienService.findAllSinhVien(req);
        model.addAttribute("page", listSinhVien);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        return "daotao/sinh-vien";
    }
}
