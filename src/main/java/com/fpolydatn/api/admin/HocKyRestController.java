package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.BaseController;
import com.fpolydatn.api.base.ResponseObject;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.model.entity.HocKyEntity;
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

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author phongtt35
 */

@RestController
@RequestMapping("/api/admin/hoc-ky")
public final class HocKyRestController extends BaseController {

    @Autowired
    private HocKyService hocKyService;

    @PostMapping
    public ResponseObject add(@RequestBody CreateHocKyCommand hocKyRequest) {
        HocKyEntity hocKyEntity = null;
        try {
            hocKyEntity = hocKyService.create(hocKyRequest);
        } catch (Exception ex) {
            // Ghi log
            System.out.println(ex.getMessage());
        }
        return new ResponseObject(hocKyEntity);
    }

    @GetMapping("/{id}")
    public ResponseObject detail(@PathVariable("id") UUID uuid) {
        HocKyEntity hocKyEntity = null;
        try {
            System.out.println(uuid);
            hocKyEntity = hocKyService.findById(uuid);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(hocKyEntity);
    }

    @PutMapping("/{id}")
    public ResponseObject update(@RequestBody UpdateHocKyCommand cmd) {
        HocKyEntity hocKyEntity = null;
        try {
            hocKyEntity = hocKyService.update(cmd);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(hocKyEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") UUID id) {
        Boolean remove = false;
        try {
            remove = hocKyService.delete(id);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(remove);
    }
}
