package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huynq
 */
@RestController
@RequestMapping("/api/dao-tao/phan-cong-hong-dan")
public class DtPhanCongHuongDanRestController {
    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String uuid) {
        return new ResponseObject(phanCongHuongDanService.findId(uuid));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(phanCongHuongDanService.delete(id));
    }

    @PostMapping
    public ResponseObject add(@RequestBody PhanCongHuongDan phanCongHuongDan) {
        return new ResponseObject(phanCongHuongDanService.add(phanCongHuongDan));
    }
}
