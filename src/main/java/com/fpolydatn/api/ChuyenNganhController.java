package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vinhnv
 */
@RestController
@RequestMapping(value = "/api/chuyen-nganh")
public class ChuyenNganhController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping("")
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(chuyenNganhService.getList(offset,size));
    }
}
