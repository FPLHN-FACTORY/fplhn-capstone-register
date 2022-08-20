package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.admin.model.request.FindHocKyRequest;
import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.BasePhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.FindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.service.PhanCongHuongDanService;
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
public final class PhanCongHuongDanController extends BaseController {

    @Autowired
    private PhanCongHuongDanService phanCongHuongDanService;

    @GetMapping("/{id}")
    public String viewPhanCongHuongDan(Model model
                                        , final FindPhanCongHuongDanRequest req
                                        , @PathVariable("id") String id) {
        System.out.println(id);
        PageableObject<PhanCongHuongDanResponse> listPhanCong = phanCongHuongDanService.search(id, req);
        for (PhanCongHuongDanResponse a : listPhanCong.getData()
        ) {
            System.out.println(a.getTenGV());
        }
        PhanCongHuongDanMonDatnResponse monDatnResponse = phanCongHuongDanService.getMon(id);
        List<PhanCongHuongDanGiaoVienResponse> phanCongHuongDanGiaoViens = phanCongHuongDanService.getPhanCongGiaoVien(monDatnResponse.getCoSoId(), id);

        model.addAttribute("page", listPhanCong);
        model.addAttribute("monDatn", monDatnResponse);
        model.addAttribute("phanCongHuongDanGiaoViens", phanCongHuongDanGiaoViens);
        return "daotao/phan-cong-huong-dan";
    }

}
