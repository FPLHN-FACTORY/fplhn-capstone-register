package com.fpolydatn.core.admin;

import com.fpolydatn.entity.NhomDatn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Controller
@RequestMapping("/admin/nhomdatns")
public class NhomDatnController {

    @Autowired
    private NhomDatnService nhomDatnService;

    @GetMapping
    public String listNhomDatns(@RequestParam(value = "coSoId", required = false) String coSoId,
                                @RequestParam(value = "dotDangKyId", required = false) String dotDangKyId,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NhomDatn> nhomDatns;
        if (coSoId != null && !coSoId.isEmpty() && dotDangKyId != null && !dotDangKyId.isEmpty()) {
            nhomDatns = nhomDatnService.getNhomDatnsByCoSoIdAndDotDangKyId(coSoId, dotDangKyId, pageable);
        } else {
            nhomDatns = nhomDatnService.getAllNhomDatns(pageable);
        }
        model.addAttribute("nhomDatns", nhomDatns);
        model.addAttribute("coSoId", coSoId); // Preserve filter values in the view
        model.addAttribute("dotDangKyId", dotDangKyId); // Preserve filter values in the view
        return "admin/nhomdatn-list";
    }

    @GetMapping("/new")
    public String createNhomDatnForm(Model model) {
        model.addAttribute("nhomDatn", new NhomDatn());
        return "admin/nhomdatn-form";
    }

    @PostMapping
    public String saveNhomDatn(@ModelAttribute("nhomDatn") NhomDatn nhomDatn) {
        nhomDatnService.saveNhomDatn(nhomDatn);
        return "redirect:/nhomdatns";
    }

    @GetMapping("/edit/{id}")
    public String editNhomDatnForm(@PathVariable String id, Model model) {
        NhomDatn nhomDatn = nhomDatnService.getNhomDatnById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid NhomDatn Id:" + id));
        model.addAttribute("nhomDatn", nhomDatn);
        return "admin/nhomdatn-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteNhomDatn(@PathVariable String id) {
        nhomDatnService.deleteNhomDatn(id);
        return "redirect:/admin/nhomdatns";
    }
}
