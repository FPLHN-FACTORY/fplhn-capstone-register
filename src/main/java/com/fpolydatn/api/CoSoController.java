package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.UpdateCoSoRequest;
import com.fpolydatn.service.CoSoService;
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
 * @author thepvph20110
 */


@RestController
@RequestMapping("/api/co-so")
public class CoSoController {

    @Autowired
    private CoSoService coSoService;

    @GetMapping
    public ResponseObject getListCoSo(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = size * page;
        return new ResponseObject(coSoService.getListCoSo(offset, size));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteCoSo(@PathVariable("id") UUID id) {
        return new ResponseObject(coSoService.deleteCoSo(id));
    }


    @PostMapping
    public ResponseObject create(@RequestBody CreateCoSoRequest canBo) {
        return new ResponseObject(coSoService.addNew(canBo));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable UUID id,
                                 @RequestBody UpdateCoSoRequest coSo) {
        return new ResponseObject(coSoService.update(id, coSo));
    }

    @GetMapping("/{id}")
    public ResponseObject get(@PathVariable UUID id) {
        return new ResponseObject(coSoService.findById(id));
    }

}
