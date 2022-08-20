package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.NhomDatnResponse;
import com.fpolydatn.core.chunhiem.service.NhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/nhom-datn")
public class NhomDatnRestController {

    @Autowired
    private NhomDatnService nhomDatnService;

    @GetMapping("/search")
    public ResponseObject viewHocKy(final FindNhomDatnRequest req) {
        PageableObject<NhomDatnResponse> listNhomDatn = nhomDatnService.search(req);
        return new ResponseObject(listNhomDatn);
    }

}
