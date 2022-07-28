package com.fpolydatn.api.response;

import com.fpolydatn.infrastructure.request.CreateVaiTroRequest;
import com.fpolydatn.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vai-tro")
public class VaiTroController {

    @Autowired
    VaiTroService vaiTroService;

    @GetMapping
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(vaiTroService.getList(offset, size));

    }

    @PostMapping
    public ResponseObject create(@RequestBody CreateVaiTroRequest createVaiTroRequest){
        return new ResponseObject(vaiTroService.create(createVaiTroRequest));
    }

}
