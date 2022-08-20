package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;
import com.fpolydatn.core.daotao.service.MonDatnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hungpv
 */
@Controller
@RequestMapping("/dao-tao/mon-datn")
public class MonDatnController {
    @Autowired
    private MonDatnService monDatnService;

    @GetMapping
    public String viewMonDatn(Model model, final FindMonDatnRequest req){
        PageableObject<MonDatnResponse>listMonDatn = monDatnService.search(req);
        model.addAttribute("page", listMonDatn);
        return "/daotao/mon-datn";
    }
}
