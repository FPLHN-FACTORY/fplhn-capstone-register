package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * @author hoangnt
 */
@Controller
@RequestMapping("/sinh-vien/nhom-datn")
public class SvNhomDatnController extends BaseController {

    @Autowired
    private SvNhomDatnService svNhomDatnService;

    @GetMapping("")
    public String viewNhomDatnThamGia(Model model){
        Boolean checkTruongNhom = svNhomDatnService.checkTruongNhom("7f796270-4834-4a4c-b1c4-1d61ef50fccd",
                session.getCoSoId(), "bf1ed6d8-ff54-4a7b-a14d-7946a1a11170");
        List<SvNhomDatnResponse> svNhomDatnResponse = svNhomDatnService.getNhomDatnById("7f796270-4834-4a4c-b1c4-1d61ef50fccd");
        model.addAttribute("checkTruongNhom",checkTruongNhom);
        model.addAttribute("nhomDatnThamGia",svNhomDatnResponse);
        return "sinhvien/sv-nhomdatn";
    }
}
