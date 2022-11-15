package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author huynq
 */
@Controller
@RequestMapping("/dao-tao/mon-datn")
public final class DtPhanCongHuongDanController extends BaseController {

    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping("/{id}")
    public String viewPhanCongHuongDan(Model model
            , final DtFindPhanCongHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtPhanCongHuongDanResponse> listPhanCong = phanCongHuongDanService.search(id, req);
        DtPhanCongHuongDanMonDatnResponse monDatnResponse = phanCongHuongDanService.getMon(id);
        List<DtPhanCongHuongDanResponse> phanCongHuongDanGiaoViens = phanCongHuongDanService.getPhanCongGiaoVien(coSoId, id);

        model.addAttribute("page", listPhanCong);
        model.addAttribute("monDatn", monDatnResponse);
        model.addAttribute("phanCongHuongDanGiaoViens", phanCongHuongDanGiaoViens);
        return "daotao/dt-phan-cong-huong-dan";
    }

}
