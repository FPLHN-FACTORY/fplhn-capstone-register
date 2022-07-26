package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vinhnv
 */

@RestController
@RequestMapping(value = "/api/hoc-ky")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService ;

    @GetMapping
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "20") int size) {
        int offset = page * size;
        return new ResponseObject(hocKyService.getList(offset, size));

    }

    @PostMapping("")
    public ResponseObject create(@RequestBody CreateHocKyRequest createHocKyRequest){
        return new ResponseObject(hocKyService.addNew(createHocKyRequest));
    }
}
