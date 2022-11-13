package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.service.CnDotDangKyService;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chu-nhiem/dot-dang-ky/sinh-vien")
public class CnSinhVienTheoDotController extends BaseController {
    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnDotDangKyService dotDangKyService;

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @GetMapping
    public String viewSinhVienTheoDot(Model model, final CnFindSinhVienTheoDotRequest req){
        req.setIdDotDangKy(commonHelper.getDotDangKyHienTai());
        req.setIdChuNhiem(session.getUserId());
        boolean checkHanChuNhiem = false;
        if(req.getIdDotDangKy() != null){
            checkHanChuNhiem = nhomDatnService.checkHanChuNhiem();
        }
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.listDotDangKy(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> pageSinhVien = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        model.addAttribute("page",pageSinhVien);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("checkHanChuNhiem", checkHanChuNhiem);
        return "chunhiem/cn-sinh-vien-theo-dot";
    }
}
