package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.model.CoSo;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/co-so")
public class CoSoController {

    @Autowired
    private CoSoService coSoService;
    @GetMapping("/{id}")
    public ResponseObject get(@PathVariable UUID id) {
        return new ResponseObject(coSoService.findById(id));
    }
    @GetMapping("")
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(coSoService.getList(offset, size));
    }

    @PostMapping("")
    public ResponseObject addNew(@RequestBody CreateCoSoRequest coSoRequest) {
        return new ResponseObject(coSoService.addCoSo(coSoRequest));
    }
}
