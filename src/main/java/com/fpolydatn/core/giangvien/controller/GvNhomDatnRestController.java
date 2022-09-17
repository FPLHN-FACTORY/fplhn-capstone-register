package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import com.fpolydatn.entity.NhomDatn;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author thangdt
 */

@RestController
@RequestMapping(value = "/api/giang-vien/nhom-datn")
public class GvNhomDatnRestController {

    @Autowired
    private GvNhomDatnService nhomDatnService;

    @Autowired
    private SvSinhVienService sinhVienService;

    @GetMapping()
    public ResponseObject detailToTalSinhVien(final SvFindSinhVienRequest request) {
        return new ResponseObject(sinhVienService.findByNhomId(request));
    }

    @PutMapping()
    public ResponseObject chotDuLieu(final GvUpdateNhomDatnRequest request) {
        return new ResponseObject(nhomDatnService.updateTrangThaiNhom(request));
    }

    @GetMapping("/search")
    public ResponseObject viewSeachNhomDatn(final GvFindNhomDatnRequest request) {
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("/{maNhom}")
    public ResponseObject detailNhomDatn(@PathVariable("maNhom") String maNhom) {
        NhomDatn nhomDatnResponse = nhomDatnService.findById(maNhom);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/update-ten-de-tai/{maNhom}")
    public ResponseObject updateTenDeTaiNhomDatn(@PathVariable("maNhom") String maNhom,
                                                  @RequestBody GvUpdateTenDeTaiRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.updateTenDeTai(request);
        return new ResponseObject(nhomDatnResponse);
    }
}
