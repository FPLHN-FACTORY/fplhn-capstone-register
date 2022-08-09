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

    @GetMapping("/list")
    public String viewHocKy(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "keyword", defaultValue = "") String tenHocKy) {
        int offset = size * page;
        Long count = hocKyService.getSizeListHocKy(tenHocKy);
        Long sizePaging = (count % size == 0 ? (count / size) : (count / size + 1));
        List<HocKy> listHocKy = hocKyService.searchHocKy(tenHocKy, offset, size);
        model.addAttribute("listHocKy", listHocKy);
        model.addAttribute("numberPaging", sizePaging);
        model.addAttribute("number", offset);
        return "hoc_ky_view";
    }

    @PostMapping("/update/{id}")
    public String updateHocKy(Model model, @PathVariable("id") UUID id, @RequestParam("tenHocKyUpdate") String tenHocKy) {
        try {
            HocKy updateHocKyRequest = new HocKy();
            updateHocKyRequest.setTenHocKy(tenHocKy);
            hocKyService.update(id, updateHocKyRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/hoc-ky/list";
    }

    @PostMapping("/remove/{id}")
    public String deleteHocKy(Model model, @PathVariable("id") UUID id) {
        try {
            hocKyService.delete(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/hoc-ky/list";
    }

    @PostMapping(value = "/add")
    public String addHocKy(Model model, @RequestParam("tenHocKyAdd") String tenHocKy) {
        try {
            CreateHocKyRequest request = new CreateHocKyRequest();
            request.setTenHocKy(tenHocKy);
            hocKyService.addNew(request);
            return "redirect:/hoc-ky/list";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "add_hoc_ky";
    }
}
