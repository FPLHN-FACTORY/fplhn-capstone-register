package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.FindDtNhomDatnTheoDotRequest;
import com.fpolydatn.core.daotao.model.request.FindSinhVienRequest;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomDatnTheoDotResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienResponse;
import com.fpolydatn.core.daotao.service.DtNhomDatnTheoDotService;
import com.fpolydatn.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dao-tao/nhom-datn")
public class DtNhomDatnTheoDotRestController extends BaseController {
    @Autowired
    private DtNhomDatnTheoDotService dtNhomDatnTheoDotService;

    @Autowired
    private CommonHelper commonHelper;

    @GetMapping("")
    public ResponseObject showDanhSachSinhVien(@RequestParam("keyword") String id, final FindSinhVienRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        req.setId(id);
        List<DtSinhVienResponse> listDanhSachSinhVien = dtNhomDatnTheoDotService.showDanhSachSinhVien(req);
        return new ResponseObject(listDanhSachSinhVien);
    }

    @GetMapping("/search")
    public ResponseObject viewHocKy(final FindDtNhomDatnTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        String dotDangKyId = commonHelper.getDotDangKyHienTai();
        req.setCoSoId(coSoId);
        if (req.getDotDangKyId() == null) {
            req.setDotDangKyId(dotDangKyId);
        }
        PageableObject<DtNhomDatnTheoDotResponse> listNhomDatn = dtNhomDatnTheoDotService.findAllNhomDatn(req);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam("typeExcel") String typeExcel, FindDtNhomDatnTheoDotRequest request) throws IOException {
        request.setCoSoId(session.getCoSoId());
        dtNhomDatnTheoDotService.DTNhomDatnExport(response, typeExcel, request);
    }
}
