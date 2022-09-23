package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.service.DtSvChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtSvDotDangKyService;
import com.fpolydatn.core.daotao.service.DtSinhVienTheoDotService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/dao-tao/sinh-vien")
public class DtSinhVienTheoDotController extends BaseController {
    @Autowired
    private DtSinhVienTheoDotService sinhVienService;

    @Autowired
    private DtSvChuyenNganhService chuyenNganhService;

    @Autowired
    private DtSvDotDangKyService dotDangKyService;
    @GetMapping
    public String findAllSinhVien(Model model,final FindDtSinhVienTheoDotRequest req) {
        req.setCoSoId(session.getCoSoId());
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.findAllSimpleEntityByCoSo(req.getCoSoId());
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(req.getCoSoId());
        PageableObject<DtSinhVienTheoDotResponse> listSinhVien = sinhVienService.findAllSinhVien(req);
        model.addAttribute("page", listSinhVien);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listDotDangKy", listDotDangKy);
        return "daotao/dt-sinh-vien-theo-dot";
    }
    @PostMapping("/import")
    public String exportEcel(@RequestParam("file") MultipartFile file,Model model,final FindDtSinhVienTheoDotRequest req) throws IOException {
        req.setCoSoId(session.getCoSoId());
        String coSoId =session.getCoSoId();
        sinhVienService.DtSinhVienImport(file,coSoId);
        return findAllSinhVien(model,req);
    }
}
