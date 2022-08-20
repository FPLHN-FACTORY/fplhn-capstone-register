package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.admin.service.HocKyService;
import com.fpolydatn.core.chunhiem.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.NhomDatnResponse;
import com.fpolydatn.core.chunhiem.service.NhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thangncph26123
 */
@Controller
@RequestMapping("/chu-nhiem/nhom-datn")
public class NhomDatnController {

    @Autowired
    private NhomDatnService nhomDatnService;

    @GetMapping
    public String viewHocKy(Model model, final FindNhomDatnRequest req) {
        PageableObject<NhomDatnResponse> listNhomDatn = nhomDatnService.search(req);
        List<String> listTenChuyenNganh = nhomDatnService.listTenChuyenNganh();
        List<String> listTenGvhd = nhomDatnService.listTenGvhd();
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listTenChuyenNganh", listTenChuyenNganh);
        model.addAttribute("listTenGvhd", listTenGvhd);
        return "chunhiem/nhom-datn";
    }
}
