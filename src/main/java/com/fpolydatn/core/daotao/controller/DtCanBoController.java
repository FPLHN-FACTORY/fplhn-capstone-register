package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */

@PropertySource("classpath:application.properties")
@Controller
@RequestMapping("/dao-tao/can-bo")
public final class DtCanBoController extends BaseController {

    @Autowired
    private DtCanBoService canBoService;

    @Value("${youtube.daotao.canbo}")
    private String youtubeLink;

    @GetMapping
    public String viewHocKy(Model model, final DtFindCanBoRequest canBoRequest) {
        canBoRequest.setCoSoId(session.getCoSoId());
        PageableObject<DtCanBoResponse> listCanBo = canBoService.search(canBoRequest);
        model.addAttribute("page", listCanBo);
        model.addAttribute("youtubeLink", youtubeLink);
        return "daotao/dt-can-bo";
    }

}
