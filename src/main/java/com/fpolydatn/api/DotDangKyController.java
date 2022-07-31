package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateDotDangKyRequest;
import com.fpolydatn.service.DotDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author thepvph20110
 */

@RestController
@RequestMapping(value = "/api/dot-dang-ky")
public class DotDangKyController {

    @Autowired
    private DotDangKyService dotDangKyService;

    @PostMapping
    public ResponseObject createDotDangKy(@RequestBody CreateDotDangKyRequest dotDangKyRequest) {
        return new ResponseObject(dotDangKyService.createDotDangKy(dotDangKyRequest));
    }

    @PutMapping("/{id}")
    public ResponseObject updateDotDangKy(@RequestBody CreateDotDangKyRequest dotDangKyRequest, @PathVariable("id") UUID id) {
        return new ResponseObject(dotDangKyService.updateDotDangKy(dotDangKyRequest, id));
    }

}
