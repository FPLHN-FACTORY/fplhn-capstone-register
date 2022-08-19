package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.BaseGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.FindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.GiangVienHDResponse;
import com.fpolydatn.core.daotao.service.GiangVienHDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vinhnv
 */
@Controller
@RequestMapping("/dao-tao/giang-vien-huong-dan")
public class GiangVienController extends BaseGiangVienHDRequest {

    @Autowired
    private GiangVienHDService giangVienHDService;

    public String viewGiangVienHD(Model model , final FindGiangVienHDRequest rep){
        PageableObject<GiangVienHDResponse> listGiangVien = giangVienHDService.search(rep);
        model.addAttribute("page",listGiangVien);
        return "daotao/giang-vien-huong-dan";
    }
}
