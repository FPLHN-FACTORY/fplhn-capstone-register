package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.model.request.DtCreateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtFindGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateGiangVienHDRequest;
import com.fpolydatn.core.daotao.model.response.DtApiResponse;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.service.DtGiangVienHDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinhnv
 */
@RestController
@RequestMapping("/api/dao-tao/giang-vien-huong-dan")
public class DtGiangVienRestController extends BaseController {

    @Autowired
    private DtGiangVienHDService giangVienHDService;

    @PostMapping
    public ResponseObject add(@RequestBody DtCreateGiangVienHDRequest cmd) {
        String coSoid = session.getCoSoId();
        cmd.setCoSoId(coSoid);
        return new ResponseObject(giangVienHDService.create(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody DtUpdateGiangVienHDRequest cmd) {
        cmd.setId(id);
        return new ResponseObject(giangVienHDService.update(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(giangVienHDService.delete(id));
    }

    @GetMapping("/search")
    public ResponseObject search(final DtFindGiangVienHDRequest rep) {
        DtApiResponse<DtGiangVienHDResponse> apiResponse = new DtApiResponse<>();
        PageableObject<DtGiangVienHDResponse> listGiangVienHD = giangVienHDService.search(rep);
        apiResponse.setData(listGiangVienHD);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listGiangVienHD.getTotalPages(); i++) {
            list.add(i + 1);
        }
        apiResponse.setList(list);
        return new ResponseObject(apiResponse);
    }
}
