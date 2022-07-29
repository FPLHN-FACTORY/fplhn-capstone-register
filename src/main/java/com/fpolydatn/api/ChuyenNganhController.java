package com.fpolydatn.api;

import com.fpolydatn.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vinhnv
 */
@RestController
@RequestMapping(value = "/api/chuyen-nganh")
public class ChuyenNganhController {

    @Autowired
    private ChuyenNganhService chuyenNganhService;
}
