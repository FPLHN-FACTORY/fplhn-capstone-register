package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * @author hoangnt
 */
@Controller
@RequestMapping("/sinh-vien")
public class SvNhomDatnController extends BaseController {

    @Autowired
    private SvNhomDatnService svNhomDatnService;

    @Autowired
    private SvSinhVienService svSinhVienService;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @GetMapping
    public String viewNhomDatn(Model model, final SvFindNhomDatnRequest req ){
        String nhomId = svSinhVienService.getNhomIdById(session.getCoSoId(),
                "67f067fa-a0f4-4792-9cc4-3dbc92341561",
                "27b2dc99-65cb-4cbf-b02b-e2e3213517f5");
        if (nhomId == null || nhomId.trim().isEmpty()) {
            req.setCoSoId(session.getCoSoId());
            req.setDotDangKyId("67f067fa-a0f4-4792-9cc4-3dbc92341561");
            PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req);
            model.addAttribute("page", listNhomDatn);
            return "sinhvien/sv-join-nhom-datn";
        } else {
            Boolean checkTruongNhom = svNhomDatnService.checkTruongNhom(nhomId,
                    session.getCoSoId(), "27b2dc99-65cb-4cbf-b02b-e2e3213517f5");
            List<SvDetailNhomDatnResponse> svDetailNhomDatnResponse = svNhomDatnService.getNhomDatnById(nhomId);
            model.addAttribute("checkTruongNhom",checkTruongNhom);
            model.addAttribute("nhomDatnThamGia", svDetailNhomDatnResponse);
            return "sinhvien/sv-nhom-datn";
        }
    }

//    @GetMapping("/join")
//    public String findAllNhomDatn(Model model, final FindSvNhomDatnRequest req) {
//        String coSoId = session.getCoSoId();
//        req.setCoSoId(coSoId);
//        PageableObject<SvSinhVienNhomDatnResponse> listNhomDatn = svSinhVienNhomDatnService.findAllNhomDatn(req);
//        model.addAttribute("page", listNhomDatn);
//        return "sinhvien/sv-nhom-datn";
//    }
}
