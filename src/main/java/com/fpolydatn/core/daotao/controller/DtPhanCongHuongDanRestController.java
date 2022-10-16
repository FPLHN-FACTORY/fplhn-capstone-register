package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.service.DtPhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huynq
 */
@RestController
@RequestMapping("/api/dao-tao/phan-cong-hong-dan")
public class DtPhanCongHuongDanRestController {
    @Autowired
    private DtPhanCongHuongDanService phanCongHuongDanService;

    @GetMapping()
    public ResponseObject detail(@RequestParam("idGiangVien") String idGiangVien, @RequestParam("idMon") String idMon) {
        return new ResponseObject(phanCongHuongDanService.findId(idGiangVien, idMon));
    }

    @DeleteMapping()
    public ResponseObject delete(@RequestParam("idGiangVien") String idGiangVien, @RequestParam("idMon") String idMon) {
        return new ResponseObject(phanCongHuongDanService.delete(idGiangVien,idMon));
    }

    @PostMapping()
    public ResponseObject addAll(@RequestBody List<PhanCongHuongDan> list) {
        return new ResponseObject(phanCongHuongDanService.addAll(list));
    }

}
