package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnMonDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.service.CnChuyenNganhService;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnMonDatnService;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.core.common.CommonHelper;
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
@RequestMapping("/chu-nhiem/nhom-datn")
public class CnNhomDatnController extends BaseController {

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CnChuyenNganhService chuyenNganhService;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @Autowired
    private CnMonDatnService cnMonDatnService;

    @Autowired
    private CommonHelper commonHelper;

    @Value("${youtube.cnbm.nhomdatn}")
    private String youtubeLink;

    @Value("${youtube.csv}")
    private String hdsdCSV;

    @GetMapping
    public String viewNhomDatn(Model model, final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        String dotDangKyHienTai = commonHelper.getIdDotDangKyHienTai();
        req.setDotDangKyId(dotDangKyHienTai);
        Boolean checkDotDangKyHienTai = false;
        if(dotDangKyHienTai != null) {
            checkDotDangKyHienTai = true;
        }
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req, session.getUserId());
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        List<CnMonDatnResponse> listMonDatn = cnMonDatnService.getAllMonDatnByChuyenNganh(coSoId, session.getUserId());
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.getAllChuyenNganhByIdChuNhiem(coSoId, session.getUserId());
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listMonDatn", listMonDatn);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("checkDotDangKyHienTai", checkDotDangKyHienTai);
        model.addAttribute("youtubeLink", youtubeLink);
        model.addAttribute("hdsdCSV", hdsdCSV);
        return "chunhiem/cn-nhom-datn";
    }
}