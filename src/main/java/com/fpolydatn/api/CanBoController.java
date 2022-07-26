package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.request.CreateCanBoRequest;
import com.fpolydatn.service.CanBoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenvv4
 */
@RestController
@RequestMapping(value = "/api")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping(Constants.UrlPath.URL_API_CAN_BO_LIST)
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        int offset = page * size;
        return new ResponseObject(canBoService.getList(offset, size));
    }

    @PostMapping(Constants.UrlPath.URL_API_CAN_BO_ADD)
    public ResponseObject addNew(@RequestBody CreateCanBoRequest canBo) {
        return new ResponseObject(canBoService.addNew(canBo));
    }

}
