package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.giangvien.model.request.GvFindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.GvUpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.GvApiResponse;
import com.fpolydatn.core.giangvien.model.response.GvNhomDatnResponse;
import com.fpolydatn.core.giangvien.service.GvNhomDatnService;
import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.service.SvSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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
    public GvApiResponse viewSeachNhomDatn(final GvFindNhomDatnRequest request) {
        PageableObject<GvNhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        List<Integer> listPage = new ArrayList<>();
        for(int index = 0; index < listNhomDatn.getTotalPages(); index++){
            listPage.add(index);
        }
        GvApiResponse apiRespone = new GvApiResponse(listNhomDatn,listPage);
        return apiRespone;
    }
}
