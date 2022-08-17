package com.fpolydatn.api.daotao;

import com.fpolydatn.api.base.ResponseObject;
import com.fpolydatn.model.domain.ChuyenNganh;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thangncph26123
 */

@RestController
@RequestMapping("/api/dao-tao/chuyen-nganh")
public class ChuyenNganhRestController {
    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping
    public ResponseObject getListCoSo() {
        List<ChuyenNganh> list = chuyenNganhService.getListChuyenNganhCustom();
        return new ResponseObject(list);
    }
}
