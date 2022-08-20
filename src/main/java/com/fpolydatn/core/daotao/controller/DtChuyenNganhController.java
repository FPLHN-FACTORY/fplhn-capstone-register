package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.ChuyenNganhResponse;
import com.fpolydatn.core.daotao.model.response.TenCanBoResponse;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author hoangnt
 */
@Controller
@RequestMapping("/dao-tao/chuyen-nganh")
public class DtChuyenNganhController extends BaseController {

    @Autowired
    private DtChuyenNganhService chuyenNganhService;

    @Autowired
    private DtCanBoService canBoService;

    @GetMapping
    public String viewChuyenNganh(Model model, final FindChuyenNganhRequest request){
        String coSoId = session.getCoSoId();
        request.setCoSoId(coSoId);
        PageableObject<ChuyenNganhResponse> listChuyenNganh = chuyenNganhService.search(request);
        List<TenCanBoResponse> listTenCanBo = canBoService.getTenCanBo();
        model.addAttribute("page",listChuyenNganh);
        model.addAttribute("listCanBo",listTenCanBo);
        return "daotao/chuyen-nganh";
    }
}
