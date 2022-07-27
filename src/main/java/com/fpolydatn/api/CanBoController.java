package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.UpdateCanBoRequest;
import com.fpolydatn.service.CanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

/**
 * @author nguyenvv4
 */
@RestController
@RequestMapping(value = "/api/can-bo")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(canBoService.getList(offset, size));
    }

//    @GetMapping("/{id}")
//    public ResponseObject get(@PathVariable UUID id) {
//        throw new NotImplementedException();
//    }
//
    @PostMapping
    public ResponseObject create(@RequestBody CreateCanBoRequest canBo) {
        return new ResponseObject(canBoService.addNew(canBo));
    }
//
//    @PostMapping("/import")
//    public ResponseObject importExcel(@RequestBody CreateCanBoRequest canBo) {
//        throw new NotImplementedException();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseObject update(@PathVariable UUID id,
//                                 @RequestBody UpdateCanBoRequest canBo) {
//        throw new NotImplementedException();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseObject delete(@PathVariable UUID id) {
//        throw new NotImplementedException();
//    }
}
