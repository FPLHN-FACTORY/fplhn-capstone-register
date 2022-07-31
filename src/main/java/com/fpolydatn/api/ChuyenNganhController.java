package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateChuyenNganhRequest;
import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

    @GetMapping("/search/{id}")
    public ResponseObject search(@PathVariable UUID id) {
        return new ResponseObject(chuyenNganhService.search(id));
    }

}
