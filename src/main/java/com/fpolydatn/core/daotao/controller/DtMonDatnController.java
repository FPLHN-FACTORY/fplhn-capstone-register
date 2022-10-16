package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.model.request.MuitiPathFile;
import com.fpolydatn.core.daotao.service.DtChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("page", pageMonDatn);
        model.addAttribute("file", new MuitiPathFile());
        model.addAttribute("tenChuyenNganh", listChuyenNganh);
        return "daotao/dt-mon-datn";
    }

    @PostMapping("/import")
    public String importExcel(RedirectAttributes redirectAttributes, @RequestBody MultipartFile file) {
        List<String> check = this.monDatnService.importExcel(file);
        System.out.println(check);
        if(check.size() == 0) {
            redirectAttributes.addFlashAttribute("successMess", "Import file excel thành công.");
        } else {
            redirectAttributes.addFlashAttribute("errorMess", check);
        }
        return "redirect:/dao-tao/mon-datn";
    }

}
