package com.fpolydatn.api;

import com.fpolydatn.api.response.ResponseObject;
import com.fpolydatn.infrastructure.request.HocKyRequest;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/**
 * @author HangNT
 */

@Controller
@RequestMapping("/hoc-ky")
public class HocKyController {

    @Autowired
    private HocKyService hocKyService;

    @GetMapping("/list")
    public String viewHocKy(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "5") int size, @RequestParam(value = "keyword", defaultValue = "") String tenHocKy) {
        int offset = size * page;
        Long count = hocKyService.getSizeListHocKy(tenHocKy);
        Long sizePaging = (count % size == 0 ? (count / size) : (count / size + 1));
        List<HocKy> listHocKy = hocKyService.searchHocKy(tenHocKy, offset, size);
        model.addAttribute("listHocKy", listHocKy);
        model.addAttribute("numberPaging", sizePaging);
        model.addAttribute("number", offset);
        return "hoc_ky";
    }

    @PutMapping(value = "/add")
    @ResponseBody
    public ResponseObject addHocKy(Model model, @RequestBody HocKyRequest hocKyRequest) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.addNew(hocKyRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(hocKy);
    }

    @GetMapping("/detail")
    @ResponseBody
    public ResponseObject detailHocKy(Model model, @RequestParam("id") UUID uuid) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.findById(uuid);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(hocKy);
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseObject updateHocKy(Model model, @PathVariable("id") UUID id, @RequestBody HocKyRequest hocKyRequest) {
        HocKy hocKy = null;
        try {
            hocKy = hocKyService.update(id, hocKyRequest);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return new ResponseObject(hocKy);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseBody
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
