package com.fpolydatn.core.admin;

import com.fpolydatn.entity.DotDangKy;
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
@RequestMapping("/admin/dotdangkys")
public class DotDangKyController {

    @Autowired
    private DotDangKyService dotDangKyService;

    @GetMapping
    public String listDotDangKys(@RequestParam(value = "coSoId", required = false) String coSoId, Model model) {
        List<DotDangKy> dotDangKys;
        if (coSoId != null && !coSoId.isEmpty()) {
            dotDangKys = dotDangKyService.getDotDangKysByCoSoId(coSoId);
        } else {
            dotDangKys = dotDangKyService.getAllDotDangKys();
        }
        model.addAttribute("dotDangKys", dotDangKys);
        model.addAttribute("coSoId", coSoId); // To preserve the filter value in the view
        return "admin/dotdangky-list";
    }

    @GetMapping("/new")
    public String createDotDangKyForm(Model model) {
        model.addAttribute("dotDangKy", new DotDangKy());
        return "admin/dotdangky-form";
    }

    @PostMapping
    public String saveDotDangKy(@ModelAttribute("dotDangKy") DotDangKy dotDangKy) {
        dotDangKyService.saveDotDangKy(dotDangKy);
        return "redirect:/admin/dotdangkys";
    }

    @GetMapping("/edit/{id}")
    public String editDotDangKyForm(@PathVariable String id, Model model) {
        DotDangKy dotDangKy = dotDangKyService.getDotDangKyById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid DotDangKy Id:" + id));
        model.addAttribute("dotDangKy", dotDangKy);
        return "admin/dotdangky-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteDotDangKy(@PathVariable String id) {
        dotDangKyService.deleteDotDangKy(id);
        return "redirect:/admin/dotdangkys";
    }
}
