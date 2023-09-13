package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindDashboardRequest;
import com.fpolydatn.core.chunhiem.service.CnDashboardService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/thong-ke-chi-so")
public class CnDashboardRestController extends BaseController {

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private CnDashboardService cnDashboardService;

    @GetMapping("/search-all")
    public ResponseObject searchAll(final CnFindDashboardRequest req) {
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
        return new ResponseObject(cnDashboardService.dashboard(req));
    }

    @GetMapping("/search-gvhd")
    public ResponseObject searchGvhd(final CnFindDashboardRequest req) {
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
        return new ResponseObject(cnDashboardService.dashboardGvhd(req));
    }
}
