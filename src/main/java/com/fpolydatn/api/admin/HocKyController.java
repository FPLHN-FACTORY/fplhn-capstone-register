package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.BaseController;
import com.fpolydatn.api.base.PageableObject;
import com.fpolydatn.infrastructure.query.hocky.HocKyQuery;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author HangNT
 */
@Controller
@RequestMapping("/admin/hoc-ky")
public final class HocKyController extends BaseController {

    @Autowired
    private HocKyService hocKyService;

    @GetMapping
    public String viewHocKy(Model model, @Valid final FindHocKyRequest dto) {
        Long totalRecords = hocKyService.getSizeListHocKy(dto.getTenHocKy());
        long sizePaging = PaginationUtil.calculateTotalPages(totalRecords, dto.getSize());

        HocKyQuery req = mapper.map(HocKyQuery.class, dto);

        List<HocKy> listHocKy = hocKyService.search(req);
        model.addAttribute("page",
                new PageableObject<HocKy>(listHocKy, sizePaging, req.getOffset()));
        return "admin/hoc-ky";
    }

    @GetMapping("test")
    public String test() {
        throw new UnsupportedOperationException("HIHI");
    }

}
