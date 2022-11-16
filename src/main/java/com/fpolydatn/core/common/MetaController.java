package com.fpolydatn.core.common;

import com.fpolydatn.infrastructure.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author phongtt35
 */

@Controller
public final class MetaController {

    @GetMapping("/version")
    public String getVersion() {
        return Constants.VERSION;
    }
}
