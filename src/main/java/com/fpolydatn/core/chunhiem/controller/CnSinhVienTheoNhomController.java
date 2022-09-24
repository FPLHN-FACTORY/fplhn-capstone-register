package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/chu-nhiem/sinh-vien")
public class CnSinhVienTheoNhomController extends BaseController {

    @Autowired
    private CnSinhVienTheoNhomService sinhVienTheoNhomService;

    @GetMapping
    public String viewSinhVienTheoNhom(Model model, @RequestParam("id") String id, final CnFindSinhVienTheoNhomRequest req) {
        String coSoId = session.getCoSoId();
        req.setIdNhom(id);
        req.setCoSoId(coSoId);
        PageableObject<CnSinhVienTheoNhomResponse> pageSinhVien = sinhVienTheoNhomService.searchByIdNhom(req);
        model.addAttribute("page", pageSinhVien);
        model.addAttribute("idNhom",req.getIdNhom());
        return "chunhiem/cn-sinh-vien-theo-nhom";
    }

}
