package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public String viewNhomDatnThamGia(Model model, @PathVariable String id){
        Boolean checkTruongNhom = svNhomDatnService.checkTruongNhom(id,
                session.getCoSoId(), "db85db46-8ac1-4267-8ee1-588f395df8bf");
        List<SvNhomDatnResponse> svNhomDatnResponse = svNhomDatnService.getNhomDatnById(id);
        model.addAttribute("checkTruongNhom",checkTruongNhom);
        model.addAttribute("nhomDatnThamGia",svNhomDatnResponse);
        return "sinhvien/sv-nhom-datn";
    }
}
