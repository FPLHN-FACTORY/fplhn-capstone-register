package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.service.DotDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/api/dot-dang-ky")
public class DotDangKyController {

    @Autowired
    private DotDangKyService dotDangKyService;

    @GetMapping("/{id}")
    public ResponseObject get(@PathVariable("id") UUID id) {
        return new ResponseObject(dotDangKyService.findByID(id));
    }

}
