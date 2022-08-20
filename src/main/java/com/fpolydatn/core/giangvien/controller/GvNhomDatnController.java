package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author thangdt
 */

@Controller
@RequestMapping("/giang-vien/nhom-datn")
public class GvNhomDatnController {

    @Autowired
    private GvNhomDatnService nhomDatnService;

    @GetMapping()
    public String viewNhomDatn(final GvFindNhomDatnRequest request, Model model) {
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        model.addAttribute("page", listNhomDatn);
        return "/giangvien/gv-nhom-datn";
    }

}
