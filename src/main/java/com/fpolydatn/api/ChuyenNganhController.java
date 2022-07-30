package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateChuyenNganhRequest;
import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vinhnv
 */
@RestController
@RequestMapping(value = "/api/chuyen-nganh")
public class ChuyenNganhController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @PostMapping("")
    public ResponseObject create(@RequestBody CreateChuyenNganhRequest createChuyenNganhRequest) {
        return new ResponseObject(chuyenNganhService.insert(createChuyenNganhRequest));
    }
}
