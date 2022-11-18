package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtMonDatnMauImport;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.model.request.MuitiPathFile;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @author hungpv
 */
@Controller
@RequestMapping("/dao-tao/mon-datn")
public class DtMonDatnController extends BaseController {
    @Autowired
    private DtMonDatnService monDatnService;

    @Autowired
    private DtChuyenNganhService chuyenNganhService;

    @GetMapping
    public String viewMonDatn(Model model, final DtFindMonDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<DtMonDatnResponse> pageMonDatn = monDatnService.search(req);
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.findAllSimpleEntityByCoSo(coSoId);
//        List<MonDatn> tenNhomMon = monDatnService.findByTenNhomMon(coSoId);
        model.addAttribute("page", pageMonDatn);
        model.addAttribute("file", new MuitiPathFile());
        model.addAttribute("tenChuyenNganh", listChuyenNganh);
//        model.addAttribute("tenNhomMon", tenNhomMon);
        return "daotao/dt-mon-datn";
    }

    @GetMapping("/mau-import")
    public void exportEcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MonDatn.xlsx";
        response.setHeader(headerKey, headerValue);
        DtMonDatnMauImport mau = new DtMonDatnMauImport();
        mau.exportData(response);
    }

}
