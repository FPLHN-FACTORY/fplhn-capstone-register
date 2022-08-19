package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.NhomDatnResponse;
import com.fpolydatn.core.giangvien.service.NhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author thangdt
 */

@Controller
@RequestMapping("/giang-vien/nhom-datn")
public class NhomDatnController {

    @Autowired
    private NhomDatnService nhomDatnService;

    @GetMapping()
    public String viewNhomDatn(Model model, final FindNhomDatnRequest request) {
        PageableObject<NhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        List<String> listTenHocKy = nhomDatnService.listHocKy();
        List<String> listTenDotDangKy = nhomDatnService.listDotDangKi();
        List<String> listTenChuyenNganh = nhomDatnService.listChuyenNganh();
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listTenHocKy", listTenHocKy);
        model.addAttribute("listTenDotDangKy", listTenDotDangKy);
        model.addAttribute("listTenChuyenNganh", listTenChuyenNganh);
        return "/giangvien/gv-nhom-datn";
    }

}
