package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.model.ChuyenNganh;
import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Vinhnv
 */
@RestController
@RequestMapping(value = "/api/chuyen-nganh")
public class ChuyenNganhController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @GetMapping("/{id}")
    public ResponseObject getList(@PathVariable UUID id,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        if(chuyenNganhService.getList(id, offset, size) ==null){
            return new ResponseObject("Không tìm thấy");
        }
        return new ResponseObject(chuyenNganhService.getList(id, offset, size));
    }
}
