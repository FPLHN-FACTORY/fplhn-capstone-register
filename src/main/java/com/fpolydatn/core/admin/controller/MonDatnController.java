package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.service.MonDatnService;
import com.fpolydatn.entity.MonDatn;
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
@RequestMapping("/admin/mondatns")
public class MonDatnController {

    @Autowired
    private MonDatnService monDatnService;

    @GetMapping
    public String listMonDatns(@RequestParam(value = "coSoId", required = false) String coSoId, Model model) {
        List<MonDatn> monDatns;
        if (coSoId != null && !coSoId.isEmpty()) {
            monDatns = monDatnService.getMonDatnsByCoSoId(coSoId);
        } else {
            monDatns = monDatnService.getAllMonDatns();
        }
        model.addAttribute("monDatns", monDatns);
        model.addAttribute("coSoId", coSoId); // To preserve the filter value in the view
        return "admin/mondatn-list";
    }

    @GetMapping("/new")
    public String createMonDatnForm(Model model) {
        model.addAttribute("monDatn", new MonDatn());
        return "admin/mondatn-form";
    }

    @PostMapping
    public String saveMonDatn(@ModelAttribute("monDatn") MonDatn monDatn) {
        monDatnService.saveMonDatn(monDatn);
        return "redirect:/admin/mondatns";
    }

    @GetMapping("/edit/{id}")
    public String editMonDatnForm(@PathVariable String id, Model model) {
        MonDatn monDatn = monDatnService.getMonDatnById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid MonDatn Id:" + id));
        model.addAttribute("monDatn", monDatn);
        return "admin/mondatn-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMonDatn(@PathVariable String id) {
        monDatnService.deleteMonDatn(id);
        return "redirect:/admin/mondatns";
    }
}
