package com.fpolydatn.api;

import com.fpolydatn.infrastructure.response.ChuyenNganhResponse;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thangncph26123
 */

@RestController
public class ChuyenNganhController {
    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @RequestMapping("/list")
    public List<ChuyenNganhResponse> getListCoSo() {
        List<ChuyenNganhResponse> list = chuyenNganhService.getListChuyenNganhCustom();
        return list;
    }
}
