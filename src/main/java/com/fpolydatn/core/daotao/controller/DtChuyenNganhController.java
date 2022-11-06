package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindChuyenNganhRequest;
import com.fpolydatn.core.daotao.model.response.DtChuyenNganhResponse;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.CommonHelper;
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
    private CommonHelper commonHelper;

    @Autowired
    private DtCanBoService canBoService;

    @GetMapping
    public String viewChuyenNganh(Model model, final DtFindChuyenNganhRequest request) {
        String coSoId = session.getCoSoId();
        request.setCoSoId(coSoId);
        int vaiTro = VaiTro.CHU_NHIEM_BO_MON.ordinal();
        PageableObject<DtChuyenNganhResponse> listChuyenNganh = chuyenNganhService.search(request);
        List<SimpleEntityProj> listTenCanBo = canBoService.findAllSimpleEntityByTenCanBo(coSoId, vaiTro);
        model.addAttribute("page", listChuyenNganh);
        model.addAttribute("listCanBo", listTenCanBo);
        return "daotao/dt-chuyen-nganh";
    }
}
