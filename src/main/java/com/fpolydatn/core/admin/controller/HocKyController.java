package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.admin.service.HocKyService;
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

@PropertySource("classpath:application.properties")

@Controller
@RequestMapping("/dao-tao/hoc-ky")
public final class HocKyController extends BaseController {

    @Autowired
    private HocKyService hocKyService;

    @Value("${youtube.daotao.hocky}")
    private String youtubeLink;

    @GetMapping
    public String viewHocKy(Model model, final FindHocKyRequest req) {
        PageableObject<HocKyResponse> listHocKy = hocKyService.search(req);
        model.addAttribute("page", listHocKy);
        model.addAttribute("youtubeLink", youtubeLink);
        return "admin/hoc-ky";
    }

}
