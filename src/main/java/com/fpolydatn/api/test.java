package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenvv4
 */

@RestController
@RequestMapping(value = "/demo")
public class test {

    @GetMapping("")
    public ResponseObject getLog() {
        if (true) {
            throw new RestApiException(StatusCode.ABSENT_DETAIL_NOT_EXIST);
        } else {
            return new ResponseObject("xin chao");
        }
    }
}
