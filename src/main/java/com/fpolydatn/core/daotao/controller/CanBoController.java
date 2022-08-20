package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.daotao.model.response.CanBoResponse;
import com.fpolydatn.core.daotao.service.CanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * @author caodinh
 */

@Controller
@RequestMapping("/dao-tao/can-bo")
public class CanBoController {

    @Autowired
    private CanBoService canBoService;

    @GetMapping
    public String viewHocKy(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "5") int size) {
        int offset = size * page;
        UUID coSoId = UUID.fromString("78d1e5c6-a9d8-43be-a6a0-deb1f45c116d");
        Long count = canBoService.countCanBoByCoSoId(coSoId);
        Long sizePaging = (count % size == 0 ? (count / size) : (count / size + 1));
        List<CanBoResponse> listCanBo = canBoService.findAllCanBoResponse(coSoId,offset, size);
        model.addAttribute("listCanBo", listCanBo);
        model.addAttribute("numberPaging", sizePaging);
        model.addAttribute("number", offset);
        return "daotao/can-bo";
    }

}