package com.fpolydatn.api.admin;

import com.fpolydatn.infrastructure.interactor.admin.SearchHocKyInteractor;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import com.fpolydatn.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String viewHocKy(Model model, FindHocKyRequest request) {
        Long totalRecords = hocKyService.getSizeListHocKy(request.getTenHocKy());
        long sizePaging = PaginationUtil.calculateTotalPages(totalRecords, request.getSize());
        SearchHocKyInteractor interactor = FormUtils.convert(SearchHocKyInteractor.class, request);

        List<HocKy> listHocKy = hocKyService.search(interactor);
        model.addAttribute("listHocKy", listHocKy);
        model.addAttribute("numberPaging", sizePaging);
        model.addAttribute("number", interactor.getOffset());
        return "admin/hoc-ky";
    }

}
