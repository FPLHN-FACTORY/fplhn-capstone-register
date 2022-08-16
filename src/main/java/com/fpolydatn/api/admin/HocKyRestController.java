package com.fpolydatn.api.admin;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.hocky.CreateHocKyRequest;
import com.fpolydatn.infrastructure.request.hocky.UpdateHocKyRequest;
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
public class HocKyRestController {

    @Autowired
    private HocKyService hocKyService;

    @PostMapping
    public ResponseObject addHocKy(Model model, @Valid @RequestBody CreateHocKyRequest hocKyRequest) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.addNew(hocKyRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(hocKy);
    }

    @GetMapping("/{id}")
    public ResponseObject detailHocKy(Model model, @PathVariable("id") UUID uuid) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.findById(uuid);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(hocKy);
    }

    @PutMapping("/{id}")
    public ResponseObject updateHocKy(Model model, @PathVariable("id") UUID id,
                                      @Valid @RequestBody UpdateHocKyRequest hocKyRequest) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.update(id, hocKyRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(hocKy);
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteHocKy(Model model, @PathVariable("id") UUID id) {
        Boolean remove = false;
        try {
            remove = hocKyService.delete(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(remove);
    }
}
