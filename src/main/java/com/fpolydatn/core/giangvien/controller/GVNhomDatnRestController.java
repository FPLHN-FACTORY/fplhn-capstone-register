package com.fpolydatn.core.giangvien.controller;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.giangvien.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.request.UpdateNhomDatnRequest;
import com.fpolydatn.core.giangvien.model.response.NhomDatnResponse;
import com.fpolydatn.core.giangvien.service.NhomDatnService;
import com.fpolydatn.core.sinhvien.model.request.FindSinhVienRequest;
import com.fpolydatn.core.sinhvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author thangdt
 */

@RestController
@RequestMapping(value = "/api/giang-vien/nhom-datn")
public class GVNhomDatnRestController {

    @Autowired
    private NhomDatnService nhomDatnService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping()
    public ResponseObject detailToTalSinhVien(@RequestBody()  FindSinhVienRequest request) {
        return new ResponseObject(sinhVienService.findByNhomId(request));
    }

    @PutMapping()
    public ResponseObject chotDuLieu(@RequestBody()  UpdateNhomDatnRequest request) {
        return new ResponseObject(nhomDatnService.updateTrangThaiNhom(request));
    }

    @GetMapping("/search")
    public ResponseObject viewSeachNhomDatn(@RequestBody()  FindNhomDatnRequest request) {
        PageableObject<NhomDatnResponse> listNhomDatn = nhomDatnService.fillAllNhomDatn(request);
        return new ResponseObject(listNhomDatn);
    }
}
