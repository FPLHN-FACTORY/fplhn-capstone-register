package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnMonDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.service.CnChuyenNganhService;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnMonDatnService;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
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

@PropertySource("classpath:linkyoutube.properties")
@Controller
@RequestMapping("/chu-nhiem/dot-dang-ky/sinh-vien")
public class CnSinhVienTheoDotController extends BaseController {
    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CnMonDatnService cnMonDatnService;

    @Autowired
    private CnChuyenNganhService chuyenNganhService;

    @Value("${youtube.cnbm.sinhvien}")
    private String youtubeLink;

    @Value("${youtube.csv}")
    private String hdsdCSV;

    @GetMapping
    public String viewSinhVienTheoDot(Model model, final CnFindSinhVienTheoDotRequest req){
        req.setIdDotDangKy(commonHelper.getDotDangKyHienTai());
        req.setIdChuNhiem(session.getUserId());
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
        Boolean checkDotDangKyHienTai = false;
        if(dotDangKyHienTai != null) {
            checkDotDangKyHienTai = true;
        }
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> pageSinhVien = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        List<CnMonDatnResponse> listMonDatn = cnMonDatnService.getAllMonDatnByChuyenNganh(coSoId, session.getUserId());
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.getAllChuyenNganhByIdChuNhiem(coSoId, session.getUserId());
        model.addAttribute("page",pageSinhVien);
        model.addAttribute("listMonDatn", listMonDatn);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("checkDotDangKyHienTai", checkDotDangKyHienTai);
        model.addAttribute("youtubeLink", youtubeLink);
        model.addAttribute("hdsdCSV", hdsdCSV);
        return "chunhiem/cn-sinh-vien-theo-dot";
    }
}
