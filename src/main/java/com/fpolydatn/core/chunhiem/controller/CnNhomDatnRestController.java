package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.model.request.CnFindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.ApiResponse;
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
import java.util.UUID;

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
        ApiResponse<CnNhomDatnResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(listNhomDatn);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listNhomDatn.getTotalPages(); i++) {
            list.add(i+1);
        }
        apiResponse.setList(list);
        return new ResponseObject(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseObject updateTrangThaiNhom(Model model, @PathVariable("id") String id, @RequestParam("page") int page) {
        String coSoId = session.getCoSoId();
        Boolean update = false;
        try {
            update = nhomDatnService.updateTrangThaiNhom(id, coSoId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(update);
    }

    @GetMapping("/{id}")
    public ResponseObject detailNhomDatn(Model model, @PathVariable("id") String id) {
        NhomDatn nhomDatn = null;
        try {
            nhomDatn = nhomDatnService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(nhomDatn);
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteNhomDatn(Model model, @PathVariable("id") String id) {
        Boolean remove = false;
        try {
            remove = nhomDatnService.delete(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(remove);
    }


}
