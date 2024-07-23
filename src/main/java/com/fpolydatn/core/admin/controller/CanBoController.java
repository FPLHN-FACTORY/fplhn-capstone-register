package com.fpolydatn.core.admin.controller;

import com.fpolydatn.core.admin.service.CanBoService;
import com.fpolydatn.entity.CanBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Controller
@RequestMapping("/admin/canbos")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping
    public String listCanBos(@RequestParam(value = "cosoId", required = false) String cosoId, Model model) {
        List<CanBo> canBos;
        if (cosoId != null && !cosoId.isEmpty()) {
            canBos = canBoService.getCanBosByCosoId(cosoId);
        } else {
            canBos = canBoService.getAllCanBo();
        }
        model.addAttribute("canbos", canBos);
        model.addAttribute("cosoId", cosoId);  // To preserve the filter value in the view
        return "admin/canbo-list";
    }

    @GetMapping("/new")
    public String createCanBoForm(Model model) {
        model.addAttribute("canbo", new CanBo());
        return "admin/canbo-form";
    }

    @PostMapping
    public String saveCanBo(@ModelAttribute CanBo canBo) {
        canBoService.saveCanBo(canBo);
        return "redirect:/admin/canbos";
    }

    @GetMapping("/edit/{id}")
    public String editCanBoForm(@PathVariable String id, Model model) {
        Optional<CanBo> canBo = canBoService.getCanBoById(id);
        canBo.ifPresent(value -> model.addAttribute("canbo", value));
        return "admin/canbo-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCanBo(@PathVariable String id) {
        canBoService.deleteCanBo(id);
        return "redirect:/admin/canbos";
    }
}
