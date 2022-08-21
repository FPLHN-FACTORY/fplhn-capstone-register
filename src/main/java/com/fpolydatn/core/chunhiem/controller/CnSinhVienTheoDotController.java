package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("chu-nhiem/dot-dang-ky/sinh-vien")
public class CnSinhVienTheoDotController extends BaseController {
    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @Autowired
    private DotDangKyRepository dotDangKyRepository;//hiện tại chưa lấy đợt nên bọn em fix cứng cái dot_dang_ky_id thầy ạ

    @GetMapping
    public String viewSinhVienTheoDot(Model model, final CnFindSinhVienTheoDotRequest req){
        req.setIdDotDangKy(dotDangKyRepository.findAll().get(0).getId());
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> pageSinhVien = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        System.out.println(pageSinhVien.getData().size());
        model.addAttribute("page",pageSinhVien);
        return "chunhiem/cn-sinh-vien-theo-dot";
    }
}
