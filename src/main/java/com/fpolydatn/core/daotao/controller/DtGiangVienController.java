package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.MuitiPathFile;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;



import java.util.List;

/**
 * @author Vinhnv
 */
@Controller
@RequestMapping("/dao-tao/giang-vien-huong-dan")
public class DtGiangVienController extends BaseController {

    @Autowired
    private DtGiangVienHDService giangVienHDService;

    @Autowired
    private DtDotDangKyService dangKyService;


    @GetMapping("")
    public String viewGiangVienHD(Model model, final DtFindGiangVienHDRequest rep) {
        String coSoid = session.getCoSoId();
        rep.setCoSoId(coSoid);
        PageableObject<DtGiangVienHDResponse> listGiangVien = giangVienHDService.search(rep);
        List<SimpleEntityProj> listDotDangKy = dangKyService.findAllSimpleEntity(coSoid);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("file", new MuitiPathFile());
        model.addAttribute("page", listGiangVien);
        return "daotao/dt-giang-vien-huong-dan";
    }

}