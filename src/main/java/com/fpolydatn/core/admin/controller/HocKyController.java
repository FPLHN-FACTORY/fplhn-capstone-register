package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.admin.service.TimDsHocKyUseCase;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HangNT
 */
@PropertySource("classpath:linkyoutube.properties")
@Controller
@RequestMapping("/dao-tao/hoc-ky")
public final class HocKyController extends BaseController {

    private final TimDsHocKyUseCase timDsHocKyUseCase;
    @Autowired
    public HocKyController(TimDsHocKyUseCase timDsHocKyUseCase) {
        this.timDsHocKyUseCase = timDsHocKyUseCase;
    }

    @Value("${youtube.daotao.hocky}")
    private String youtubeLink;

    @Value("${youtube.csv}")
    private String hdsdCSV;

    @GetMapping
    public String viewHocKy(Model model, final FindHocKyRequest req) {
        PageableObject<HocKyResponse> listHocKy = timDsHocKyUseCase.execute(req);
        model.addAttribute("page", listHocKy);
        model.addAttribute("youtubeLink", youtubeLink);
        model.addAttribute("hdsdCSV", hdsdCSV);
        return "admin/hoc-ky";
    }

}
