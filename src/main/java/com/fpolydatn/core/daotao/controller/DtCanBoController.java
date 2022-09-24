package com.fpolydatn.core.daotao.controller;

import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.helper.ExcelHelper;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author caodinh
 */

@Controller
@RequestMapping("/dao-tao/can-bo")
public final class DtCanBoController extends BaseController {

    @Autowired
    private DtCanBoService canBoService;

    @Autowired
    private ExcelHelper excelHelper;

    @GetMapping
    public String viewHocKy(Model model, final DtFindCanBoRequest canBoRequest) {
        canBoRequest.setCoSoID(session.getCoSoId());
        PageableObject<DtCanBoResponse> listCanBo = canBoService.search(canBoRequest);
        model.addAttribute("page", listCanBo);
        return "daotao/dt-can-bo";
    }

    @PostMapping("/import")
    public String ImportFlie(Model model,@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        DtFindCanBoRequest dtFindCanBoRequest = new DtFindCanBoRequest();
        List<CanBo> listCanBo = excelHelper.importData(reapExcelDataFile,session.getCoSoId());
        for (CanBo canBo: listCanBo) {
            canBoService.createcb(canBo);
        }
        return viewHocKy(model,dtFindCanBoRequest);
    }

}