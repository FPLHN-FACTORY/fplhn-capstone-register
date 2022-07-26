package com.fpolydatn.api;

import com.fpolydatn.service.HocKyService;
import com.fpolydatn.service.impl.HocKyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vinhnv
 */

@RestController
@RequestMapping(value = "/api/hoc-ky")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService;

}
