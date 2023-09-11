package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindDashboardRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardResponse;
import com.fpolydatn.core.chunhiem.service.CnDashboardService;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.common.base.BaseController;
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
@RequestMapping("/chu-nhiem/thong-ke-chi-so")
public class CnDashboardController extends BaseController {

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnDashboardService cnDashboardService;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @GetMapping
    public String viewDashboard(Model model, final CnFindDashboardRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
        req.setDotDangKyId(dotDangKyHienTai);
        String idUser = session.getUserId();
        req.setIdChuNhiem(idUser);
        CnDashboardResponse cnDashboardResponse = cnDashboardService.dashboard(req);
        List<SimpleEntityProj> listChuyenNganh = cnDashboardService.getAllChuyenNganhByIdChuNhiem(req);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        model.addAttribute("dashboard", cnDashboardResponse);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listDotDangKy", listDotDangKy);
        return "chunhiem/cn-thong-ke";
    }
}
