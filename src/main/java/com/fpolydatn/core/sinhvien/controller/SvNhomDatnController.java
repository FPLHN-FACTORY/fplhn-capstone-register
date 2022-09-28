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
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

//    @Autowired
//    private SvMonDatnService svMonDatnService;

    private String idUser = "a2529445-f53f-47ea-a2d2-9762bfe075bc";

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
            model.addAttribute("listDataMonDatn", svNhomDatnService.getMonDatn());
            model.addAttribute("page", listNhomDatn);
            return "sinhvien/sv-join-nhom-datn";
        } else {
            Boolean checkTruongNhom = svNhomDatnService.checkTruongNhom(nhomId,
                    session.getCoSoId(), idUser);
            List<SvDetailNhomDatnResponse> svDetailNhomDatnResponse = svNhomDatnService.getDetailNhomDatnById(nhomId);
            Integer soThanhVien = svSinhVienService.getSoThanhVienByNhomId(nhomId);
            model.addAttribute("soThanhVien", soThanhVien);
            model.addAttribute("checkTruongNhom", checkTruongNhom);
            model.addAttribute("nhomDatnThamGia", svDetailNhomDatnResponse);
            return "sinhvien/sv-nhom-datn";
        }
    }

}
