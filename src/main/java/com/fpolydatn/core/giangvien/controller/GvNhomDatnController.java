package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.admin.service.HocKyService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.service.DtSvChuyenNganhService;
import com.fpolydatn.core.daotao.service.DtSvDotDangKyService;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thangdt
 */

@Controller
@RequestMapping("/giang-vien/nhom-datn")
public class GvNhomDatnController extends BaseController {

    @Autowired
    private GvNhomDatnService nhomDatnService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private DtSvChuyenNganhService chuyenNganhService;

    @Autowired
    private DtSvDotDangKyService dotDangKyService;

    @GetMapping()
    public String viewNhomDatn(final GvFindNhomDatnRequest request, Model model) {
        String coSoId = session.getCoSoId();
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        List<SimpleEntityProj> listDotDangKy = dotDangKyService.getListDotDangKy(coSoId);
        List<SimpleEntityProj> listChuyenNganh = chuyenNganhService.getListChuyenNganh(coSoId);
        List<SimpleEntityProj> listHocKy =  hocKyService.getListHocKy();
        model.addAttribute("page", listNhomDatn);
        model.addAttribute("listDotDangKy", listDotDangKy);
        model.addAttribute("listChuyenNganh", listChuyenNganh);
        model.addAttribute("listHocKy", listHocKy);
        return "/giangvien/gv-nhom-datn";
    }

    @GetMapping("/phe-duyet-nhom")
    public String viewAllNhomDatnCanChot(final GvNhomDatnRequest request, Model model) {
        String maGvhd = "5e965ce8-9d44-4601-a00e-f793391a0c19";
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.findAllNhomDatnGroupCanPheDuyet(maGvhd, request);
        model.addAttribute("page", listNhomDatn);
        return "/giangvien/gv-phe-duyet-nhom-datn";
    }

    @GetMapping("/thong-tin-nhom-datn")
    public String viewAllNhomDatnHuongDan(final GvNhomDatnRequest request, Model model) {
        String maGvhd = "5e965ce8-9d44-4601-a00e-f793391a0c19";
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.findAllNhomDatnHuongDan(maGvhd, request);
        model.addAttribute("page", listNhomDatn);
        return "/giangvien/gv-thong-tin-nhom-datn";
    }
}
