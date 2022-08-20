package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thangdt
 */

@Controller
@RequestMapping("/giang-vien/nhom-datn")
public class GvNhomDatnController extends BaseController {

    @Autowired
    private GvNhomDatnService nhomDatnService;

    @GetMapping()
    public String viewNhomDatn(final GvFindNhomDatnRequest request, Model model) {
        String coSoId = session.getCoSoId();
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        List<SimpleEntityProj> listDotDangKy = nhomDatnService.getListDotDangKy(coSoId);
        List<SimpleEntityProj> listChuyenNganh = nhomDatnService.getListChuyenNganh(coSoId);
        List<SimpleEntityProj> listHocKy =  nhomDatnService.getListHocKy();
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listHocKy", listHocKy);
        return "/giangvien/gv-nhom-datn";
    }

}
