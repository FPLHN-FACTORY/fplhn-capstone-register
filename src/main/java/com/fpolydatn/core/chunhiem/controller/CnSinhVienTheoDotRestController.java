package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.response.CnSinhVienTheoDotResponse;
import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoDotService;
import com.fpolydatn.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chu-nhiem/sinh-vien-dot-dang-ky")
public class CnSinhVienTheoDotRestController {
    @Autowired
    private CnSinhVienTheoDotService cnSinhVienTheoDotService;

    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("idNhom") String maNhom, @RequestParam("maSinhVien") String maSinhVien) {
        boolean add = false;
        try {
            add = cnSinhVienTheoDotService.addSinhVienTheoNhom(maNhom, maSinhVien);
            return new ResponseObject(add);
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }
    @GetMapping
    public ResponseObject getSinhVienByTenSinhVien( @RequestParam("tenSinhVien") String tenSinhVien) {
        try {
            List<CnSinhVienTheoDotResponse> listSinhVien = cnSinhVienTheoDotService.getSinhVienByTenSinhVien(tenSinhVien);
            return new ResponseObject(listSinhVien);
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }
}
