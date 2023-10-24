package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import com.fpolydatn.core.daotao.service.DtSvChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtSvDotDangKyService;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thepvph20110
 */

@PropertySource("classpath:application.properties")
@Controller
@RequestMapping("/dao-tao/sinh-vien")
public class DtSinhVienTheoDotController extends BaseController {
    @Autowired
    private DtSinhVienTheoDotService sinhVienService;

    @Autowired
    private DtSvChuyenNganhService chuyenNganhService;

    @Autowired
    private DtSvDotDangKyService dotDangKyService;

    @Value("${youtube.daotao.sinhvien}")
    private String youtubeLink;

    @GetMapping
    public String findAllSinhVien(Model model, final FindDtSinhVienTheoDotRequest req) {
        req.setCoSoId(session.getCoSoId());
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.findAllSimpleEntityByCoSo(req.getCoSoId());
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(req.getCoSoId());
        List<SimpleEntityProj> listMonChuongTrinh = sinhVienService.getMaMonChuongTrinh(req.getCoSoId());
        List<NhomDatn> listNhomDatn = sinhVienService.getListNhomMon(req.getCoSoId());
        PageableObject<DtSinhVienTheoDotResponse> listSinhVien = sinhVienService.searchStudent(req);
        model.addAttribute("page", listSinhVien);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("listMonChuongTrinh", listMonChuongTrinh);
        model.addAttribute("listNhomDatn", listNhomDatn);
        model.addAttribute("youtubeLink", youtubeLink);
        return "daotao/dt-sinh-vien-theo-dot";
    }

}
