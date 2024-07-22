package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.daotao.model.request.FindHocKyRequest;
import com.fpolydatn.core.daotao.model.response.HocKyResponse;
import com.fpolydatn.core.daotao.service.HocKyService;
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

    @Autowired
    private HocKyService hocKyService;

    @Value("${youtube.daotao.hocky}")
    private String youtubeLink;

    @Value("${youtube.csv}")
    private String hdsdCSV;

    @GetMapping
    public String viewHocKy(Model model, final FindHocKyRequest req) {
        PageableObject<HocKyResponse> listHocKy = hocKyService.search(req);
        model.addAttribute("page", listHocKy);
        model.addAttribute("youtubeLink", youtubeLink);
        model.addAttribute("hdsdCSV", hdsdCSV);
        return "admin/hoc-ky";
    }

}
