package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.service.CnChuyenNganhService;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
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
public class CnNhomDatnController extends BaseController {

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CnChuyenNganhService chuyenNganhService;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping
    public String viewNhomDatn(Model model, final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req);
        List<SimpleEntityProj> listTenChuyenNganh = chuyenNganhService.listTenChuyenNganh(coSoId);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listTenChuyenNganh", listTenChuyenNganh);
        model.addAttribute("listDotDangKy", listDotDangKy);
        return "chunhiem/cn-nhom-datn";
    }
}