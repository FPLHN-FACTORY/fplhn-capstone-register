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
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.fpolydatn.util.DateTimeUtil.convertDateToTimeStampSecond;

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
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private SvGiangVienHuongDanService svGiangVienHuongDanService;

    private String idUser = "f61b5269-9181-4be7-9b46-c5e4c81cb00f";

    @GetMapping
    public String viewNhomDatn(Model model, final SvFindNhomDatnRequest req) {
        String dotDangKyHienTai = dotDangKyRepository.findDotDangKyID(convertDateToTimeStampSecond());
        String nhomId = svSinhVienService.getNhomIdById(session.getCoSoId(),
                dotDangKyHienTai,
                idUser);
        if (nhomId == null || nhomId.trim().isEmpty()) {
            req.setCoSoId(session.getCoSoId());
            req.setDotDangKyId(dotDangKyHienTai);
            PageableObject<SvNhomDatnResponse> listNhomDatn = svNhomDatnService.findAllNhomDatn(req);
            model.addAttribute("listDataMonDatn", svMonDatnService.getMonDatn());
            model.addAttribute("page", listNhomDatn);
            return "sinhvien/sv-join-nhom-datn";
        } else {
            Boolean checkTruongNhom = svNhomDatnService.checkTruongNhom(nhomId,
                    session.getCoSoId(), idUser);
            List<SvDetailNhomDatnResponse> svDetailNhomDatnResponse = svNhomDatnService.getDetailNhomDatnById(nhomId);
            Integer soThanhVien = svSinhVienService.getSoThanhVienByNhomId(nhomId);
            String monDatnId = svSinhVienService.getMonDatnIdBySinhVienId(idUser);
            List<SvGiangVienHuongDanResponse> listGiangVien = svGiangVienHuongDanService.getGvhdByMonDatnId(monDatnId);
            model.addAttribute("soThanhVien", soThanhVien);
            model.addAttribute("listGiangVien", listGiangVien);
            model.addAttribute("checkTruongNhom", checkTruongNhom);
            model.addAttribute("nhomDatnThamGia", svDetailNhomDatnResponse);
            return "sinhvien/sv-nhom-datn";
        }
    }

}
