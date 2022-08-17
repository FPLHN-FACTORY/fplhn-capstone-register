package com.fpolydatn.infrastructure.mapper;

import com.fpolydatn.api.base.PageableRequest;
import com.fpolydatn.infrastructure.query.PageableQuery;
import com.fpolydatn.util.FormUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * @author phongtt35
 */

@Component
class FployDatnMapperImpl implements FpolyDatnMapper {

    private final FormUtils formUtils = new FormUtils();

    public <T extends PageableQuery> T map(Class<T> clazz, final PageableRequest data) {
        Gson gson = new Gson();
        try {
            T cqrs = formUtils.convertToObject(clazz, data);
            cqrs.setLimit(data.getSize());
            cqrs.setOffset(data.getSize() * data.getPage());
            return cqrs;
        } catch (Exception e) {
            return null;
        }
    }
}
