package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.admin.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author HangNT
 */
@Controller
@RequestMapping("/admin/hoc-ky")
//@PreAuthorize("hasAuthority('admin')")
public final class HocKyController extends BaseController {

    @Autowired
    private HocKyService hocKyService;

    @GetMapping
    public String viewHocKy(Model model, final FindHocKyRequest req) {
        PageableObject<HocKyResponse> listHocKy = hocKyService.search(req);
        model.addAttribute("page", listHocKy);
        return "admin/hoc-ky";
    }

}
