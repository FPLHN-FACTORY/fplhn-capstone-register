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

import java.util.UUID;

/**
 * @author nguyenvv4
 */
@RestController
@RequestMapping(value = "/api/can-bo")
public class CanBoController {
    @Autowired
    private CanBoService canBoService;
    @GetMapping("")
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(canBoService.getList(offset, size));
    }

    @PostMapping("")
    public ResponseObject addNew(@RequestBody CreateCanBoRequest canBo) {
        return new ResponseObject(canBoService.addNew(canBo));
    }
}
