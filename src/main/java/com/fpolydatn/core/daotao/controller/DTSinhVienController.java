package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDTSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DTSinhVienResponse;
import com.fpolydatn.core.daotao.service.DTSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/dao-tao/sinh-vien")
public class DTSinhVienController extends BaseController {
    @Autowired
    private DTSinhVienService sinhVienService;

    @GetMapping
    public String findAllSinhVien(Model model,final FindDTSinhVienRequest req) {
        PageableObject<DTSinhVienResponse> listSinhVien = sinhVienService.findAllSinhVien(req);
        model.addAttribute("page", listSinhVien);
        return "daotao/sinh-vien";
    }
}
