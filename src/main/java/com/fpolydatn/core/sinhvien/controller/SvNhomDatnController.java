package com.fpolydatn.core.sinhvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.sinhvien.model.request.SvFindNhomDatnRequest;
import com.fpolydatn.core.sinhvien.model.response.SvDetailNhomDatnResponse;
import com.fpolydatn.core.sinhvien.model.response.SvGiangVienHuongDanResponse;
import com.fpolydatn.core.sinhvien.model.response.SvNhomDatnResponse;
import com.fpolydatn.core.sinhvien.service.SvGiangVienHuongDanService;
import com.fpolydatn.core.sinhvien.service.SvMonDatnService;
import com.fpolydatn.core.sinhvien.service.SvNhomDatnService;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.util.CommonHelper;
import com.fpolydatn.util.MocThoiGianCommon;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SvMonDatnService svMonDatnService;

    @Autowired
    private SvGiangVienHuongDanService svGiangVienHuongDanService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    MocThoiGianCommon mocThoiGianCommon;



    @GetMapping
    public String viewNhomDatn(Model model, final SvFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        String idUSer = session.getUserId();
        String dotDangKyHienTai = commonHelper.getDotDangKyHienTai();
        String nhomId = svSinhVienService.getNhomIdById(coSoId,
                dotDangKyHienTai,
                idUSer);
        model.addAttribute("checkMocThoiGian", mocThoiGianCommon.checkMocThoiGianSinhVien());
        if (nhomId == null || nhomId.trim().isEmpty()) {
            req.setCoSoId(coSoId );
            req.setDotDangKyId(dotDangKyHienTai);
            PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req, idUSer);
            Boolean checkThoiGianRoiNhom = svNhomDatnService.checkThoiGianRoiNhom(idUSer);
            model.addAttribute("listDataMonDatn", svMonDatnService.getAllMonDatnByIdUser(idUSer, coSoId, dotDangKyHienTai));
            model.addAttribute("page", listNhomDatn);
            model.addAttribute("checkThoiGianRoiNhom", checkThoiGianRoiNhom);
            return "sinhvien/sv-join-nhom-datn";
        } else {
            Boolean checkTruongNhom = svNhomDatnService.checkTruongNhom(
                    nhomId, coSoId, idUSer, dotDangKyHienTai);
            List<SvDetailNhomDatnResponse> svDetailNhomDatnResponse = svNhomDatnService.getDetailNhomDatnById(nhomId, coSoId, dotDangKyHienTai);
            Integer soThanhVien = svSinhVienService.getSoThanhVienByNhomId(nhomId, coSoId, dotDangKyHienTai);
            String monDatnId = svSinhVienService.getMonDatnIdBySinhVienId(idUSer);
            List<SvGiangVienHuongDanResponse> listGiangVien = svGiangVienHuongDanService.getGvhdByMonDatnId(monDatnId, coSoId);
            model.addAttribute("soThanhVien", soThanhVien);
            model.addAttribute("listGiangVien", listGiangVien);
            model.addAttribute("checkTruongNhom", checkTruongNhom);
            model.addAttribute("nhomDatnThamGia", svDetailNhomDatnResponse);
            return "sinhvien/sv-nhom-datn";
        }
    }

}
