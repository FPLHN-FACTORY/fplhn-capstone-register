package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.service.CoSoService;
import com.fpolydatn.entity.CoSo;
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
@RequestMapping("/admin/cosos")
public class CoSoController {

    @Autowired
    private CoSoService coSoService;

    @GetMapping
    public String listCoSos(Model model) {
        List<CoSo> coSos = coSoService.getAllCoSos();
        model.addAttribute("coSos", coSos);
        return "admin/coso-list";
    }

    @GetMapping("/new")
    public String createCoSoForm(Model model) {
        model.addAttribute("coSo", new CoSo());
        return "admin/coso-form";
    }

    @PostMapping
    public String saveCoSo(@ModelAttribute("coSo") CoSo coSo) {
        coSoService.saveCoSo(coSo);
        return "redirect:/admin/cosos";
    }

    @GetMapping("/edit/{id}")
    public String editCoSoForm(@PathVariable String id, Model model) {
        CoSo coSo = coSoService.getCoSoById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CoSo Id:" + id));
        model.addAttribute("coSo", coSo);
        return "admin/coso-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCoSo(@PathVariable String id) {
        coSoService.deleteCoSo(id);
        return "redirect:/admin/cosos";
    }
}
