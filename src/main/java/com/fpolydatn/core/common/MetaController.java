package com.fpolydatn.core.common;

import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.repository.CoSoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author phongtt35
 */

@Controller
public final class MetaController {

    @Autowired
    @Qualifier(CoSoRepository.NAME)
    private CoSoRepository coSoRepository;

    @GetMapping("/version")
    public String getVersion(Model model) {
        model.addAttribute("version",Constants.VERSION);
        return "version";
    }

    @GetMapping
    public String index(Model model) {
        List<SimpleEntityProj> listCoSo = coSoRepository.findAllSimpleEntity();
        model.addAttribute("listCoSo", listCoSo);
        return "index";
    }

    @GetMapping("/403")
    public String prohibitedAccess() {
        return "403";
    }
}
