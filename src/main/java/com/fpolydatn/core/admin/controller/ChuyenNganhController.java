package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.service.ChuyenNganhService;
import com.fpolydatn.entity.ChuyenNganh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Controller
@RequestMapping("/admin/chuyennganhs")
public class ChuyenNganhController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping
    public String listChuyenNganhs(@RequestParam(value = "coSoId", required = false) String coSoId,
                                   Model model) {
        List<ChuyenNganh> chuyenNganhs;
        if (coSoId != null && !coSoId.isEmpty()) {
            chuyenNganhs = chuyenNganhService.getChuyenNganhsByCoSoId(coSoId);
        } else {
            chuyenNganhs = chuyenNganhService.getAllChuyenNganhs();
        }
        model.addAttribute("chuyenNganhs", chuyenNganhs);
        model.addAttribute("coSoId", coSoId); // To preserve the filter value in the view
        return "admin/chuyennganh-list";
    }

    @GetMapping("/new")
    public String createChuyenNganhForm(Model model) {
        model.addAttribute("chuyenNganh", new ChuyenNganh());
        return "admin/chuyennganh-form";
    }

    @PostMapping
    public String saveChuyenNganh(@ModelAttribute("chuyenNganh") ChuyenNganh chuyenNganh) {
        chuyenNganhService.saveChuyenNganh(chuyenNganh);
        return "redirect:/admin/chuyennganhs";
    }

    @GetMapping("/edit/{id}")
    public String editChuyenNganhForm(@PathVariable String id, Model model) {
        ChuyenNganh chuyenNganh = chuyenNganhService.getChuyenNganhById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ChuyenNganh Id:" + id));
        model.addAttribute("chuyenNganh", chuyenNganh);
        return "admin/chuyennganh-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteChuyenNganh(@PathVariable String id) {
        chuyenNganhService.deleteChuyenNganh(id);
        return "redirect:/admin/chuyennganhs";
    }
}
