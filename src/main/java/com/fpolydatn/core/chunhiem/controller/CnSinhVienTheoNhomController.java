package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienNoGroupRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoNhomRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienSearchResponse;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoNhomResponse;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/chu-nhiem/sinh-vien")
public class CnSinhVienTheoNhomController extends BaseController {

    @Autowired
    private CnSinhVienTheoNhomService sinhVienTheoNhomService;

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping
    public String viewSinhVienTheoNhom(Model model, @RequestParam("id") String id, final CnFindSinhVienTheoNhomRequest req, final CnFindSinhVienNoGroupRequest request) {
        String coSoId = session.getCoSoId();
        req.setIdNhom(id);
        req.setCoSoId(coSoId);
        boolean checkHanChuNhiem = false;
        req.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        if(req.getDotDangKyId() != null){
            checkHanChuNhiem = nhomDatnService.checkHanChuNhiem();
        }
        request.setCoSoId(session.getCoSoId());
        request.setDotDangKyId(commonHelper.getDotDangKyHienTai());
        request.setIdNhom(req.getIdNhom());
        PageableObject<CnSinhVienTheoNhomResponse> pageSinhVien = sinhVienTheoNhomService.searchByIdNhom(req);
        List<CnSinhVienSearchResponse> listSinhVienNoGroup = sinhVienTheoNhomService.getListSinhVienNoGroup(request, session.getUserId());
        model.addAttribute("soThanhVien", sinhVienTheoNhomService.getSizeListSinhVien(id, coSoId));
        model.addAttribute("page", pageSinhVien);
        model.addAttribute("idNhom",req.getIdNhom());
        model.addAttribute("listSinhVienNoGroup", listSinhVienNoGroup);
        model.addAttribute("checkHanChuNhiem", checkHanChuNhiem);
        return "chunhiem/cn-sinh-vien-theo-nhom";
    }

}
