package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.infrastructure.request.UpdateCoSoRequest;
import com.fpolydatn.service.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author caodinh
 */
@RestController
@RequestMapping(value = "/api/co-so")
public class CoSoController {
    @Autowired
    private CoSoService coSoService;

    @PostMapping
    public ResponseObject create(@RequestBody CreateCoSoRequest canBo) {
        return new ResponseObject(coSoService.addNew(canBo));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable UUID id,
                                 @RequestBody UpdateCoSoRequest coSo) {
        return new ResponseObject(coSoService.update(id,coSo));
    }
}
