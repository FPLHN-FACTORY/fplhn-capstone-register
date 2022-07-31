package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.service.DotDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping("/api/dot-dang-ky")
public class DotDangKyController {
    @Autowired
    private DotDangKyService dotDangKyService;

    @GetMapping("")
    public ResponseObject getListDotDangKy(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "coSoId", defaultValue = "abc") UUID coSoId
    ) {
        int offset = page * size;
        return new ResponseObject(dotDangKyService.getListDotDangKy(coSoId, offset, size));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteDotDangKy(@PathVariable("id") UUID id) {
        return new ResponseObject(dotDangKyService.deleteDotDangKy(id));
    }

}
