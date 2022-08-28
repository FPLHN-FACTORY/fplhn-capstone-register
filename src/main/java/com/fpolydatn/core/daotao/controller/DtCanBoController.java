package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */

@Controller
@RequestMapping("/dao-tao/can-bo")
//@PreAuthorize("hasAuthority('dao-tao')")
public final class DtCanBoController extends BaseController {

    @Autowired
    private DtCanBoService canBoService;

    @GetMapping
//    @Secured("dao-tao")
    public String viewHocKy(Model model, final DtFindCanBoRequest canBoRequest) {
        PageableObject<DtCanBoResponse> listCanBo = canBoService.search(canBoRequest);
        model.addAttribute("page", listCanBo);
        return "daotao/dt-can-bo";
    }

}