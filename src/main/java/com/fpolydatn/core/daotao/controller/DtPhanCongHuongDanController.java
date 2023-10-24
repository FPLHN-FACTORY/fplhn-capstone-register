package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.core.daotao.service.DtSvDotDangKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author huynq
 */

@PropertySource("classpath:application.properties")
@Controller
@RequestMapping("/dao-tao/mon-datn")
public final class DtPhanCongHuongDanController extends BaseController {

    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private DtSvDotDangKyService dotDangKyService;

    @Value("${youtube.daotao.mondatn}")
    private String youtubeLink;

    @GetMapping("/{id}")
    public String viewPhanCongHuongDan(Model model
            , final DtFindPhanCongHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        req.setDotDangKy(commonHelper.getDotDangKyHienTai());
        req.setCoSoId(coSoId);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(req.getCoSoId());
        PageableObject<DtPhanCongHuongDanResponse> listPhanCong = phanCongHuongDanService.search(id, req, req.getPage());
        DtPhanCongHuongDanMonDatnResponse monDatnResponse = phanCongHuongDanService.getMon(id);

        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("page", listPhanCong);
        model.addAttribute("monDatn", monDatnResponse);
        model.addAttribute("youtubeLink", youtubeLink);
        return "daotao/dt-phan-cong-huong-dan";
    }

}
