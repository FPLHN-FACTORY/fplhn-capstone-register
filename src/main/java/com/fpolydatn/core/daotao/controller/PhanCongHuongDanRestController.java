package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.core.daotao.service.PhanCongHuongDanService;
import com.fpolydatn.entity.PhanCongHuongDan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
public class PhanCongHuongDanRestController {
    @Autowired
    private PhanCongHuongDanService phanCongHuongDanService;

    @GetMapping("/{id}")
    public ResponseObject detailHocKy(Model model, @PathVariable("id") String uuid) {
        PhanCongHuongDan phanCongHuongDan = null;
        try {
            phanCongHuongDan = phanCongHuongDanService.findID(uuid);
            System.out.println(phanCongHuongDan);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(phanCongHuongDan);
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteHocKy(Model model, @PathVariable("id") String id) {
        Boolean remove = false;
        try {
            remove = phanCongHuongDanService.delete(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(remove);
    }

    @PostMapping
    public ResponseObject addHocKy(Model model, @RequestBody PhanCongHuongDan phanCongHuongDan) {
        PhanCongHuongDan phanCongHuongDan1 = null;
        try {
            phanCongHuongDan1 = phanCongHuongDanService.addMon(phanCongHuongDan);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(phanCongHuongDan1);
    }
}
