package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chu-nhiem/sinh-vien-dot-dang-ky")
public class CnSinhVienTheoDotController extends BaseController {
    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @GetMapping
    public String viewSinhVienTheoDot(Model model, final CnFindSinhVienTheoDotRequest req){
        req.setIdDotDangKy("817dabae-992f-4601-bdcf-01eaa2e5d7c7");
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> pageSinhVien = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        model.addAttribute("page",pageSinhVien);
        return "chunhiem/sinh-vien-theo-dot";
    }
}
