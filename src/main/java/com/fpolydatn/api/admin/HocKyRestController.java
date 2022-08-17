package com.fpolydatn.api.admin;

import com.fpolydatn.api.base.ResponseObject;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
public final class HocKyRestController {

    @Autowired
    private HocKyService hocKyService;

    @PostMapping
    public ResponseObject addHocKy(@Valid @RequestBody CreateHocKyCommand hocKyRequest) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.create(hocKyRequest);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(hocKy);
    }

    @GetMapping("/{id}")
    public ResponseObject detailHocKy(@PathVariable("id") UUID uuid) {
        HocKy hocKy = null;
        try {
            System.out.println(uuid);
            hocKy = hocKyService.findById(uuid);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(hocKy);
    }

    @PutMapping("/{id}")
    public ResponseObject updateHocKy(@Valid @RequestBody UpdateHocKyCommand cmd) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.update(cmd.getId(), cmd);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(hocKy);
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteHocKy(@PathVariable("id") UUID id) {
        Boolean remove = false;
        try {
            remove = hocKyService.delete(id);
        } catch (Exception ex) {
            // Ghi log
        }
        return new ResponseObject(remove);
    }
}
