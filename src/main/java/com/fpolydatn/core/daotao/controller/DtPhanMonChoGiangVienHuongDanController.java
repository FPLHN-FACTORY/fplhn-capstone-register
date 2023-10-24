package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author huynq
 */

@PropertySource("classpath:linkyoutube.properties")
@Controller
@RequestMapping("/dao-tao/giang-vien-huong-dan")
public class DtPhanMonChoGiangVienHuongDanController extends BaseController {

    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @Autowired
    private CommonHelper commonHelper;

    @Value("${youtube.daotao.giangvien}")
    private String youtubeLink;

    @Value("${youtube.csv}")
    private String hdsdCSV;

    @GetMapping("/{id}")
    public String viewPhanCongHuongDan(Model model
            , final DtFindPhanMonGiangVienHuongDanRequest req
            , @PathVariable("id") String id) {
        String coSoId = session.getCoSoId();
        PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> listPhanCong = phanCongHuongDanService.getPhanMonDatn(coSoId, id, req);
        DtGiangVienHDResponse giangVienHuongDan = phanCongHuongDanService.getGiangVienHuongDan(id, coSoId);

        model.addAttribute("page", listPhanCong);
        model.addAttribute("giangVienHuongDan", giangVienHuongDan);
        model.addAttribute("youtubeLink", youtubeLink);
        model.addAttribute("hdsdCSV", hdsdCSV);
        return "daotao/dt-phan-mon-datn-giang-vien";
    }

}
