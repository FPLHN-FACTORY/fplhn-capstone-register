package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvMonDatnService;
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

    @Autowired
    private GvMonDatnService monDatnService;

    @GetMapping()
    public String viewNhomDatn(final GvFindNhomDatnRequest request, Model model) {
        String coSoId = session.getCoSoId();
        String idGiangVien = session.getUserId();
        request.setGiangVienHDId(idGiangVien);
        request.setCoSoId(coSoId);
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        List<SimpleEntityProj> listMon = monDatnService.findAllSimpleEntityByCoSoAndGvhd(idGiangVien, coSoId);
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listMon", listMon);
        return "giangvien/gv-nhom-datn";
    }

}
