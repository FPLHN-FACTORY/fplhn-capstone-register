package com.fpolydatn.core.chunhiem.controller;

/**
 * @author thangncph26123
 */

import com.fpolydatn.core.chunhiem.model.request.CnFindListGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSimpleListGvhdResponse;
import com.fpolydatn.core.chunhiem.service.CnChuyenNganhService;
import com.fpolydatn.core.chunhiem.service.CnDashboardService;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
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

@PropertySource("classpath:application.properties")
@Controller
@RequestMapping("/chu-nhiem/giang-vien-huong-dan")
public class CnGiangVienHuongDanController extends BaseController {

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnGiangVienHuongDanService cnGiangVienHuongDanService;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @Autowired
    private CnChuyenNganhService chuyenNganhService;

    @Value("${youtube.cnbm.gvhdtk}")
    private String youtubeLink;

    @GetMapping
    public String view(Model model, final CnFindListGvhdRequest req) {
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
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.getAllChuyenNganhByIdChuNhiem(coSoId, idUser);
        List<SimpleEntityProj> listMonDatn = chuyenNganhService.getAllMonDatnByIdChuNhiem(coSoId, idUser);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        PageableObject<CnSimpleListGvhdResponse> listGvhd = cnGiangVienHuongDanService.getPageGvhd(req);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listMonDatn", listMonDatn);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("listGvhd", listGvhd);
        model.addAttribute("youtubeLink", youtubeLink);
        return "chunhiem/cn-giang-vien-huong-dan";
    }

}
