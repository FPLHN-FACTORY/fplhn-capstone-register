package com.fpolydatn.core.common.base;

import com.fpolydatn.infrastructure.mapper.FpolyDatnMapper;
import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author phongtt35
 */
public abstract class BaseController {

    @Autowired
    protected FpolyDatnSession session;

    @Autowired
    protected FpolyDatnMapper mapper;
}
