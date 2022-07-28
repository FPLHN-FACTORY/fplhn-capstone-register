package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
