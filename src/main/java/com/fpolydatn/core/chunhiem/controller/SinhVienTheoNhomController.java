package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.chunhiem.model.request.FindSinhVienRequest;
import com.fpolydatn.core.chunhiem.model.response.SinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.service.SinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chu-nhiem/sinh-vien")
public class SinhVienTheoNhomController extends BaseController {

    @Autowired
    private SinhVienTheoNhomService sinhVienTheoNhomService;

    @GetMapping("")
    public String viewSinhVienTheoNhom(Model model, final FindSinhVienRequest req) {
        PageableObject<SinhVienTheoNhomResponse> listHocKy = sinhVienTheoNhomService.searchByIdNhom(req);
        model.addAttribute("page", listHocKy);
        return "chunhiem/sinh-vien-theo-nhom";
    }

}
