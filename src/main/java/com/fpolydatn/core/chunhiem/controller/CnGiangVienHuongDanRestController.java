package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindListGvhdRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSimpleListGvhdResponse;
import com.fpolydatn.core.chunhiem.service.CnGiangVienHuongDanService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.common.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/giang-vien-huong-dan")
public class CnGiangVienHuongDanRestController extends BaseController {

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnGiangVienHuongDanService cnGiangVienHuongDanService;

    @GetMapping
    public ResponseObject search(final CnFindListGvhdRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        String idUser = session.getUserId();
        req.setIdChuNhiem(idUser);
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        if (req.getDotDangKyId() == null) {
            if (dotDangKyId != null) {
                req.setDotDangKyId(dotDangKyId);
            } else {
                req.setDotDangKyId("");
            }
        } else if (req.getDotDangKyId().equalsIgnoreCase("")) {
            if (dotDangKyId != null) {
                req.setDotDangKyId(dotDangKyId);
            } else {
                req.setDotDangKyId("");
            }
        }
        PageableObject<CnSimpleListGvhdResponse> listGvhd = cnGiangVienHuongDanService.getPageGvhd(req);
        return new ResponseObject(listGvhd);
    }
}
