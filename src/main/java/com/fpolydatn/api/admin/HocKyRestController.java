package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.BaseController;
import com.fpolydatn.api.base.ResponseObject;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phongtt35
 */

@RestController
@RequestMapping("/api/admin/hoc-ky")
public final class HocKyRestController extends BaseController {

    @Autowired
    private HocKyService hocKyService;

    @PostMapping
    public ResponseObject add(@RequestBody CreateHocKyCommand cmd) {
        return new ResponseObject(hocKyService.create(cmd));
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(hocKyService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody UpdateHocKyCommand cmd) {
        cmd.setId(id);
        return new ResponseObject(hocKyService.update(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(hocKyService.delete(id));
    }
}
