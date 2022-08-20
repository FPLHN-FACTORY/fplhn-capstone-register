package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.MonDatnResponse;
import com.fpolydatn.core.daotao.service.ChuyenNganhService;
import com.fpolydatn.core.daotao.service.MonDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hungpv
 */
@Controller
@RequestMapping("/dao-tao/mon-datn")
public class MonDatnController extends BaseController {
    @Autowired
    private MonDatnService monDatnService;

    @Autowired
    private ChuyenNganhService chuyenNganhService;
    @GetMapping
    public String viewMonDatn(Model model, final FindMonDatnRequest req){
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<MonDatnResponse>pageMonDatn = monDatnService.search(req);
        List<SimpleEntityProj>tenChuyenNganh = chuyenNganhService.findAllSimpleEntityByCoSo(coSoId);
        model.addAttribute("page", pageMonDatn);
        model.addAttribute("tenChuyenNganh", tenChuyenNganh);
        return "/daotao/mon-datn";
    }
}
