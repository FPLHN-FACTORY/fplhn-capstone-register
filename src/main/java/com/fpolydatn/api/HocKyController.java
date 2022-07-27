package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author vinhnv
 */

@RestController
@RequestMapping(value = "/api/hoc-ky")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService ;


    @GetMapping("")
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(hocKyService.getList(offset, size));
    }

    @PostMapping("")
    public ResponseObject create(@RequestBody CreateHocKyRequest createHocKyRequest){
        return new ResponseObject(hocKyService.addNew(createHocKyRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable UUID id){
        return new ResponseObject(hocKyService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseObject searchById(@PathVariable UUID id){
        return new ResponseObject(hocKyService.searchById(id));
    }
}
