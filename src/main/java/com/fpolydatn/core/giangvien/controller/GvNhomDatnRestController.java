package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.giangvien.model.request.GvChotNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateTenDeTaiRequest;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.core.giangvien.service.GvSinhVienService;
import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.entity.NhomDatn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangdt
 */

@RestController
@RequestMapping(value = "/api/giang-vien/nhom-datn")
public class GvNhomDatnRestController extends BaseController {

    @Autowired
    private GvNhomDatnService nhomDatnService;

    @Autowired
    private GvSinhVienService sinhVienService;

    @GetMapping()
    public ResponseObject detailToTalSinhVien(final SvFindSinhVienRequest request) {
        request.setCoSoId(session.getCoSoId());
        return new ResponseObject(sinhVienService.findByNhomId(request));
    }

    @GetMapping("/search")
    public ResponseObject viewSeachNhomDatn(final GvFindNhomDatnRequest request) {
        String idGiangVien = session.getUserId();
        request.setCoSoId(session.getCoSoId());
        request.setGiangVienHDId(idGiangVien);
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        return new ResponseObject(listNhomDatn);
    }

    @GetMapping("/{maNhom}")
    public ResponseObject detailNhomDatn(@PathVariable("maNhom") String maNhom) {
        NhomDatn nhomDatnResponse = nhomDatnService.findById(maNhom);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/update-ten-de-tai/{maNhom}")
    public ResponseObject updateTenDeTaiNhomDatn(@PathVariable("maNhom") String maNhom, @RequestBody GvUpdateTenDeTaiRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.updateTenDeTai(request);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/phe-duyet-nhom")
    public ResponseObject pheDuyetNhomDatn(final GvChotNhomDatnRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.pheDuyetNhomDatn(request);
        return new ResponseObject(nhomDatnResponse);
    }

    @PutMapping("/phe-duyet-de-tai-nhom")
    public ResponseObject pheDuyetDeTaiNhomDatn(final GvChotNhomDatnRequest request) {
        NhomDatn nhomDatnResponse = nhomDatnService.chotDeTaiNhomDatn(request);
        return new ResponseObject(nhomDatnResponse);
    }

}
