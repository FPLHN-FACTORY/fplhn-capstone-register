package com.fpolydatn.infrastructure.mapper;

import com.fpolydatn.api.base.PageableDto;
import com.fpolydatn.infrastructure.query.PageableQuery;
import com.fpolydatn.util.FormUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * @author phongtt35
 */

@Component
class ObjectMapper implements IObjectMapper {

    private final FormUtils formUtils = new FormUtils();

    public <T extends PageableQuery> T map(Class<T> clazz, final PageableDto data) {
        Gson gson = new Gson();
        try {
            T interactor = formUtils.convertToObject(clazz, data);
            interactor.setLimit(data.getSize());
            interactor.setOffset(data.getSize() * data.getPage());
            return interactor;
        } catch (Exception e) {
            return null;
        }
    }
}
