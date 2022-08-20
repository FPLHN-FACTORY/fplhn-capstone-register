package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.core.daotao.service.CanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * @author caodinh
 */

@Controller
@RequestMapping("/dao-tao/can-bo")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping
    public String viewHocKy(Model model, final FindCanBoRequest canBoRequest) {
        PageableObject<CanBoResponse> listCanBo = canBoService.search(canBoRequest);
        model.addAttribute("page", listCanBo);
        return "daotao/can-bo";
    }

}