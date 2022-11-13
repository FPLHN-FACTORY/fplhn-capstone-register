package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindSinhVienTheoDotRequest;
import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chu-nhiem/dot-dang-ky/sinh-vien")
public class CnSinhVienTheoDotRestController extends BaseController {
    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @GetMapping("/search")
    public ResponseObject searchSinhVienTheoDot(final CnFindSinhVienTheoDotRequest req) {
        String coSoId = session.getCoSoId();
        req.setIdChuNhiem(session.getUserId());
        req.setCoSoId(coSoId);
        PageableObject<CnSinhVienTheoDotResponse> listNhomDatnTheoDot = cnSinhVienTheoDotService.searchByIdDotDangKy(req);
        return new ResponseObject(listNhomDatnTheoDot);
    }

    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("id") String maNhom, @RequestParam("maSinhVien") String maSinhVien) {
        return new ResponseObject(cnSinhVienTheoDotService.addSinhVienTheoNhom(maNhom, maSinhVien));
    }

}
