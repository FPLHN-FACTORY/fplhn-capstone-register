package com.fpolydatn.api.admin;

import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author HangNT
 */
@Controller
@RequestMapping("/admin/hoc-ky")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService;

    @GetMapping
    public String viewHocKy(Model model,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "5") int size,
                            @RequestParam(value = "keyword", defaultValue = "") String tenHocKy) {
        int offset = size * page;
        Long count = hocKyService.getSizeListHocKy(tenHocKy);
        Long sizePaging = (count % size == 0 ? (count / size) : (count / size + 1));
        List<HocKy> listHocKy = hocKyService.searchHocKy(tenHocKy, offset, size);
        model.addAttribute("listHocKy", listHocKy);
        model.addAttribute("numberPaging", sizePaging);
        model.addAttribute("number", offset);
        return "admin/hoc_ky";
    }

}
