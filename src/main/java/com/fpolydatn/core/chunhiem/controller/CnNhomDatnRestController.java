package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.CnApiResponse;
import com.fpolydatn.core.chunhiem.model.response.CnNhomDatnResponse;
import com.fpolydatn.core.chunhiem.service.CnNhomDatnService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.entity.NhomDatn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/search")
    public ResponseObject viewHocKy(final CnFindNhomDatnRequest req) {
        String coSoId = session.getCoSoId();
        req.setCoSoId(coSoId);
        PageableObject<CnNhomDatnResponse> listNhomDatn = nhomDatnService.search(req);
        CnApiResponse<CnNhomDatnResponse> apiResponse = new CnApiResponse<>();
        apiResponse.setData(listNhomDatn);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listNhomDatn.getTotalPages(); i++) {
            list.add(i+1);
        }
        apiResponse.setList(list);
        return new ResponseObject(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseObject updateTrangThaiNhom(@PathVariable("id") String id, @RequestParam("page") int page) {
        String coSoId = session.getCoSoId();
        Boolean update = false;
        try {
            update = nhomDatnService.updateTrangThaiNhom(id, coSoId);
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
