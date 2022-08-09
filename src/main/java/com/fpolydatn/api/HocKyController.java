package com.fpolydatn.api;

import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.model.HocKy;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * @author HangNT
 */

@Controller
@RequestMapping("/hoc-ky")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService;

    @GetMapping("/lists")
    public String viewHocKy(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        int offset = size * page;
        Long count = hocKyService.getSizeListHocKy();
        Long sizePaging = (count % size == 0 ? (count / size) : (count / size + 1));
        List<HocKy> listHocKy = hocKyService.getList(offset, size);
        model.addAttribute("listHocKy", listHocKy);
        model.addAttribute("numberPaging", sizePaging);
        model.addAttribute("number", offset);
        return "view_hoc_ky";
    }

    @GetMapping("/update/{id}")
    public String showEditHocKy(Model model, @PathVariable("id") UUID id) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("hocKy", hocKy);
        return "update_hoc_ky";
    }

    @PostMapping("/update/{id}")
    public String updateHocKy(Model model, @PathVariable("id") UUID id, @ModelAttribute("hocKy") HocKy updateHocKyRequest) {
        try {
            hocKyService.update(id, updateHocKyRequest);
            return "redirect:/hoc-ky/lists";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "update_hoc_ky";
        }
    }

    @GetMapping("/remove/{id}")
    public String showRemoveHocKy(Model model, @PathVariable("id") UUID id) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("hocKy", hocKy);
        return "remove_hoc_ky";
    }

    @PostMapping(value = {"/remove/{id}"})
    public String deleteHocKy(
            Model model, @PathVariable("id") UUID id, @ModelAttribute("hocKy") HocKy hocKy) {
        try {
            hocKyService.delete(id);
            return "redirect:/hoc-ky/lists";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("hocKy", hocKy);
        return "view_hoc_ky";
    }

    @GetMapping(value = {"/add"})
    public String showAddHocKy(Model model) {
        CreateHocKyRequest hocKy = new CreateHocKyRequest();
        model.addAttribute("hocKy", hocKy);
        return "add_hoc_ky";
    }

    @PostMapping(value = "/add")
    public String addHocKy(Model model,
                           @ModelAttribute("hocKy") CreateHocKyRequest hocKy) {
        try {
            hocKyService.addNew(hocKy);
            return "redirect:/hoc-ky/lists";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "add_hoc_ky";
    }
}
