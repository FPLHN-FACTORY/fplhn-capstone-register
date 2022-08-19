package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.repository.CNSinhVienTheoNhomRepository;
import com.fpolydatn.core.chunhiem.service.SinhVienTheoNhomService;
import com.fpolydatn.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chu-nhiem/sinh-vien")
public class SinhVienTheoNhomRestController {
    @Autowired
    private SinhVienTheoNhomService sinhVienTheoNhomService;

    @DeleteMapping("/{maSinhVien}")
    public ResponseObject updateSinhVienByNhom(@PathVariable("maSinhVien") String maSinhVien) {
        Boolean remove = false;
        try {
            remove = sinhVienTheoNhomService.deleteFromIdSinhVien(maSinhVien);
            return new ResponseObject(remove);
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }
    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("idNhom") String idNhom, @RequestParam("maSinhVien") String maSinhVien) {
        boolean add = false;
        try {
            add = sinhVienTheoNhomService.addSinhVienTheoNhom(idNhom, maSinhVien);
            return new ResponseObject(add);
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }
}
