package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.NhomDatnExcelExporter;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtDotDangKyService;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/dao-tao/nhom-datn")
public class DtNhomDatnTheoDotController extends BaseController {
    @Autowired
    private DtNhomDatnTheoDotService dtNhomDatnTheoDotService;

    @Autowired
    private DtChuyenNganhService dtChuyenNganhService;

    @Autowired
    private DtDotDangKyService dtDotDangKyService;

    @GetMapping
    public String findAllNhomDatn(Model model, final FindDtNhomDatnTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtNhomDatnTheoDotResponse> listNhomDatn = dtNhomDatnTheoDotService.findAllNhomDatn(req);
        List<SimpleEntityProj> listChuyenNganh = dtChuyenNganhService.findAllSimpleEntityByCoSo(coSoId);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        List<SimpleEntityProj> listDotDangKy = dtDotDangKyService.findAllSimpleEntityByCoSo(coSoId);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("page", listNhomDatn);
        return "daotao/dt-nhom-datn";
    }

    @GetMapping("/export")
    public void exportEcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=NhomDuAnTotNghiep.xlsx";
        response.setHeader(headerKey, headerValue);
        List<DtNhomDatnTheoDotResponse> getListNhomDatn = dtNhomDatnTheoDotService.listAllNhomDatnToExcel();
        NhomDatnExcelExporter nhomDatn = null;
        nhomDatn = new NhomDatnExcelExporter(getListNhomDatn);

        nhomDatn.exportData(response);
    }

}
