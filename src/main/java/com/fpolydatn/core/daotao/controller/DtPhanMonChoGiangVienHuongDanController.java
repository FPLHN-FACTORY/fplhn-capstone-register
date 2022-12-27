package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.core.common.base.BaseController;
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
@RequestMapping("/dao-tao/giang-vien-huong-dan")
public class DtPhanMonChoGiangVienHuongDanController extends BaseController {
    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping("/{id}")
    public String viewPhanCongHuongDan(Model model
            , final DtFindPhanMonGiangVienHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoid = session.getCoSoId();
        PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> listPhanCong = phanCongHuongDanService.getPhanMonDatn(id, req);
        DtGiangVienHDResponse giangVienHuongDan = phanCongHuongDanService.getGiangVienHuongDan(id, coSoid);
        List<DtPhanMonDatnChoGiangVienHuongDanResponse> phanMonDatns = phanCongHuongDanService.getPhanMonDatnList(coSoid, id);

        model.addAttribute("page", listPhanCong);
        model.addAttribute("giangVienHuongDan", giangVienHuongDan);
        model.addAttribute("phanMonDatns", phanMonDatns);
        return "daotao/dt-phan-mon-datn-giang-vien";
    }

}
