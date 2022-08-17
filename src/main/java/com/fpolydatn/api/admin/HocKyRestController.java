package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.BaseController;
import com.fpolydatn.api.base.ResponseObject;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.model.entity.HocKyEntity;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseObject detail(@PathVariable("id") String id) {
        HocKyEntity hocKy = null;
        try {
            hocKy = hocKyService.findById(id);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(hocKy);
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id,
                                 @RequestBody UpdateHocKyCommand cmd) {
        HocKy hocKyEntity = null;
        cmd.setId(id);
        hocKyEntity = hocKyService.update(cmd);
        return new ResponseObject(hocKyEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        Boolean remove = false;
        try {
            remove = hocKyService.delete(id);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(remove);
    }
}
