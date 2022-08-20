package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.ChuyenNganhResponse;
import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.core.daotao.service.CanBoService;
import com.fpolydatn.core.daotao.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dao-tao/chuyen-nganh")
public class ChuyenNganhController extends BaseController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private CanBoService canBoService;

    @GetMapping
    public String viewChuyenNganh(Model model, final FindChuyenNganhRequest request){
        String coSoId = session.getCoSoId();
        request.setCoSoId(coSoId);
        PageableObject<ChuyenNganhResponse> listChuyenNganh = chuyenNganhService.search(request);
        List<TenCanBoResponse> listCanBo = canBoService.getTenCanBo();
        model.addAttribute("page",listChuyenNganh);
        model.addAttribute("listCanBo",listCanBo);
        return "daotao/chuyen-nganh";
    }
}
