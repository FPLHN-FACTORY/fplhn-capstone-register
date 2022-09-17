package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnCreateNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnApiResponse;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/chu-nhiem/nhom-datn")
public class CnNhomDatnRestController extends BaseController {

    @Autowired
    private CnNhomDatnService nhomDatnService;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;//hiện tại chưa lấy đợt nên bọn em fix cứng cái dot_dang_ky_id thầy ạ

    @GetMapping("/search")
    public ResponseObject viewHocKy(final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req);
        return new ResponseObject(listNhomDatn);
    }

    @PostMapping
    public ResponseObject create(@RequestBody CnCreateNhomDatnRequest cnCreateNhomDatnRequest) {
        cnCreateNhomDatnRequest.setCoSoId(session.getCoSoId());
        cnCreateNhomDatnRequest.setDotDangKyId(dotDangKyRepository.findAll().get(0).getId());
        Boolean update = false;
        try {
            update = nhomDatnService.create(cnCreateNhomDatnRequest);
            return new ResponseObject(update);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseObject chotDeTai(@PathVariable("id") String id) {
        Boolean update = false;
        try {
            update = nhomDatnService.chotDeTai(id);
            return new ResponseObject(update);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @PutMapping
    public ResponseObject chotDeTai(@RequestParam("id") String id, @RequestParam("nhanXet") String nhanXet) {
        Boolean update = false;
        try {
            update = nhomDatnService.tuChoiChotDeTai(id, nhanXet);
            return new ResponseObject(update);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseObject detailNhomDatn(@PathVariable("id") String id) {
        NhomDatn nhomDatn = null;
        try {
            nhomDatn = nhomDatnService.findById(id);
            return new ResponseObject(nhomDatn);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteNhomDatn(@PathVariable("id") String id) {
        Boolean remove = false;
        try {
            remove = nhomDatnService.delete(id);
            return new ResponseObject(remove);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }


}
