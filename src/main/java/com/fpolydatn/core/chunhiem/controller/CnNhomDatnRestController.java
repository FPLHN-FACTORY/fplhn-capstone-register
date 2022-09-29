package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.repository.CnDotDangKyRepository;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/nhom-datn")
public class CnNhomDatnRestController extends BaseController {

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    private CnDotDangKyRepository cnDotDangKyRepository;

    @GetMapping("/search")
    public ResponseObject viewNhomDatn(final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req);
        return new ResponseObject(listNhomDatn);
    }

    @PostMapping
    public ResponseObject create(@RequestBody CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        cnCreateNhomDatnRequest.setCoSoId(session.getCoSoId());
        cnCreateNhomDatnRequest.setDotDangKyId(cnDotDangKyRepository.findAll().get(0).getId());
        return new ResponseObject(nhomDatnService.create(cnCreateNhomDatnRequest));
    }

    @PutMapping("/{id}")
    public ResponseObject chotDeTai(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.chotDeTai(id));
    }

    @PutMapping
    public ResponseObject chotDeTai(@RequestParam("id") String id, @RequestParam("nhanXet") String nhanXet) {
        return new ResponseObject(nhomDatnService.tuChoiChotDeTai(id, nhanXet));
    }

    @GetMapping("/{id}")
    public ResponseObject detailNhomDatn(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteNhomDatn(@PathVariable("id") String id) {
        return new ResponseObject(nhomDatnService.delete(id));
    }

}
