package com.fpolydatn.core.admin;

import com.fpolydatn.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Controller
@RequestMapping("/admin/sinhviens")
public class SinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String listSinhViens(@RequestParam(value = "coSoId", required = false) String coSoId,
                                @RequestParam(value = "dotDangKyId", required = false) String dotDangKyId,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SinhVien> sinhViens;
        if (coSoId != null && !coSoId.isEmpty() && dotDangKyId != null && !dotDangKyId.isEmpty()) {
            sinhViens = sinhVienService.getSinhViensByCoSoIdAndDotDangKyId(coSoId, dotDangKyId, pageable);
        } else {
            sinhViens = sinhVienService.getAllSinhViens(pageable);
        }
        model.addAttribute("sinhViens", sinhViens);
        model.addAttribute("coSoId", coSoId); // Preserve filter values in the view
        model.addAttribute("dotDangKyId", dotDangKyId); // Preserve filter values in the view
        return "admin/sinhvien-list";
    }

    @GetMapping("/new")
    public String createSinhVienForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "admin/sinhvien-form";
    }

    @PostMapping
    public String saveSinhVien(@ModelAttribute("sinhVien") SinhVien sinhVien) {
        sinhVienService.saveSinhVien(sinhVien);
        return "redirect:/admin/sinhviens";
    }

    @GetMapping("/edit/{id}")
    public String editSinhVienForm(@PathVariable String id, Model model) {
        SinhVien sinhVien = sinhVienService.getSinhVienById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid SinhVien Id:" + id));
        model.addAttribute("sinhVien", sinhVien);
        return "admin/sinhvien-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSinhVien(@PathVariable String id) {
        sinhVienService.deleteSinhVien(id);
        return "redirect:/admin/sinhviens";
    }
}
