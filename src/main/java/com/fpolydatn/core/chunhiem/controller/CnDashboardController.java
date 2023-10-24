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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thangncph26123
 */

@PropertySource("classpath:linkyoutube.properties")
@Controller
@RequestMapping("/chu-nhiem/thong-ke-chi-so")
public class CnDashboardController extends BaseController {

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnDashboardService cnDashboardService;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @Value("${youtube.cnbm.gvhdtk}")
    private String youtubeLink;

    @GetMapping
    public String viewDashboard(Model model, final CnFindDashboardRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        String idUser = session.getUserId();
        req.setIdChuNhiem(idUser);
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (req.getDotDangKyId() == null) {
            if (dotDangKyId != null) {
                req.setDotDangKyId(dotDangKyId);
            } else {
                req.setDotDangKyId("");
            }
        } else if (req.getDotDangKyId().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                req.setDotDangKyId(dotDangKyId);
            } else {
                req.setDotDangKyId("");
            }
        }
        CnDashboardResponse cnDashboardResponse = cnDashboardService.dashboard(req);
        List<SimpleEntityProj> listChuyenNganh = cnDashboardService.getAllChuyenNganhByIdChuNhiem(req);
        List<SimpleEntityProj> listMonDatn = cnDashboardService.getAllMonDatnByIdChuNhiem(req);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        model.addAttribute("dashboard", cnDashboardResponse);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listMonDatn", listMonDatn);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("youtubeLink", youtubeLink);
        return "chunhiem/cn-thong-ke";
    }
}
