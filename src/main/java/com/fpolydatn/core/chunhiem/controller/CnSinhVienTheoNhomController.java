package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chu-nhiem/sinh-vien")
public class CnSinhVienTheoNhomController extends BaseController {

    @Autowired
    private CnSinhVienTheoNhomService sinhVienTheoNhomService;

    @GetMapping
    public String viewSinhVienTheoNhom(Model model, final CnFindSinhVienTheoNhomRequest req) {
        req.setIdNhom("428a005e-0418-49b1-ad1e-fb379e62a81d");
        PageableObject<CnSinhVienTheoNhomResponse> pageSinhVien = sinhVienTheoNhomService.searchByIdNhom(req);
        model.addAttribute("page", pageSinhVien);
        model.addAttribute("idNhom",req.getIdNhom());
        return "chunhiem/sinh-vien-theo-nhom";
    }

}
