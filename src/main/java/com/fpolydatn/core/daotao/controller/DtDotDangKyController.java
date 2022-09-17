package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindDotDangKyRequest;
import com.fpolydatn.core.daotao.model.response.DtDotDangKyResponse;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtHocKyService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author SonPT
 */

@Controller
@RequestMapping("/dao-tao/dot-dang-ky")
public final class DtDotDangKyController extends BaseController {

    @Autowired
    private DtDotDangKyService dotDangKyService;

    @Autowired
    private DtHocKyService dtHocKyService;

    @GetMapping
    public String viewDotDangKy(Model model, final DtFindDotDangKyRequest request) {
        request.setCoSoId(session.getCoSoId());
        PageableObject<DtDotDangKyResponse> listDotDangKy = dotDangKyService.findByCoSo(request);
        List<SimpleEntityProj> listHocKy = dtHocKyService.findAllSimpleEntiry();
        model.addAttribute("page", listDotDangKy);
        model.addAttribute("listHocKy", listHocKy);
        return "daotao/dt-dot-dang-ky";
    }

}
